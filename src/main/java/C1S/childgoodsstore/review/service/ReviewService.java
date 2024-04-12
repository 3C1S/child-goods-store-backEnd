package C1S.childgoodsstore.review.service;

import C1S.childgoodsstore.review.dto.ReviewDto;
import C1S.childgoodsstore.review.dto.ReviewSumDto;
import C1S.childgoodsstore.review.repository.OrderRepository;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.product.repository.ProductRepository;
import C1S.childgoodsstore.review.dto.SaveReviewDto;
import C1S.childgoodsstore.review.repository.ProductReviewRepository;
import C1S.childgoodsstore.review.repository.TogetherReviewRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductReviewRepository productReviewRepository;
    private final TogetherReviewRepository togetherReviewRepository;
    private final ProductRepository productRepository;
    private final TogetherRepository togetherRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public List<ReviewDto> getReview(Long userId){

        Optional<User> user = userRepository.findByUserId(userId);

        if(user.isEmpty()){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        //해당 사용자가 판매한 상품들을 조회
        List<Product> productList = productRepository.findByUser(user.get());
        List<Together> togetherList = togetherRepository.findByUser(user.get());

        List<ProductReview> productReviewList = new ArrayList<>();
        List<TogetherReview> togetherReviewList = new ArrayList<>();

        for(Product product: productList){
            Optional<ProductReview> productReview = productReviewRepository.findByProduct(product);
            if(!productReview.isEmpty()) productReviewList.add(productReview.get());
        }

        for(Together together: togetherList){
            Optional<TogetherReview> togetherReview = togetherReviewRepository.findByTogether(together);
            if(!togetherReview.isEmpty()) togetherReviewList.add(togetherReview.get());
        }

        List<ReviewDto> reviewList = new ArrayList<>();

        HashMap<Long, Double> userAvgList = new HashMap<Long, Double>();
        HashMap<Long, Long> userTotalList = new HashMap<Long, Long>();

        for(ProductReview productReview: productReviewList){
            ReviewDto review = new ReviewDto(productReview.getProductReviewId(), "PRODUCT", productReview.getProduct().getProductId(),
                    productReview.getUser().getUserId(), productReview.getUser().getNickName(),
                    productReview.getScore(), productReview.getContent(), productReview.getCreatedAt(),
                    productReview.getProduct().getProductName());
            userAvgList.put(productReview.getUser().getUserId(), 0.0);
            userTotalList.put(productReview.getUser().getUserId(), 0L);
            reviewList.add(review);
        }

        for(TogetherReview togetherReview: togetherReviewList){
            ReviewDto review = new ReviewDto(togetherReview.getTogetherReviewId(), "TOGETHER", togetherReview.getTogether().getTogetherId(),
                    togetherReview.getUser().getUserId(), togetherReview.getUser().getNickName(),
                    togetherReview.getScore(), togetherReview.getContent(), togetherReview.getCreatedAt(),
                    togetherReview.getTogether().getTogetherName());
            userAvgList.put(togetherReview.getUser().getUserId(), 0.0);
            userTotalList.put(togetherReview.getUser().getUserId(), 0L);

            reviewList.add(review);
        }

        for ( HashMap.Entry<Long, Double> entry : userAvgList.entrySet() ){
            //리뷰 수랑 별점 총합을 가져오기
            ReviewSumDto result1 = productReviewRepository.findSumScoreAndReviewCountByUserId(entry.getKey());

            ReviewSumDto result2 = togetherReviewRepository.findSumScoreAndReviewCountByUserId(entry.getKey());

            Long result1Sum = result1.getSum();
            Long result1Count = result1.getCount();
            Long result2Sum = result2.getSum();
            Long result2Count = result2.getCount();

            if(result1Sum==null) result1Sum = 0L;
            if(result1Count==null) result1Count = 0L;
            if(result2Sum==null) result2Sum = 0L;
            if(result2Count==null) result2Count = 0L;

            userAvgList.replace(entry.getKey(), (double) (result1Sum+result2Sum)/(result1Count+result2Count));
            userTotalList.replace(entry.getKey(), (result1Count+result2Count));
        }

        for(ReviewDto review: reviewList){
            Long reviewUserId = review.getUserId();
            review.setAverageStars(userAvgList.get(reviewUserId));
            review.setTotalReview(userTotalList.get(reviewUserId));
        }

        return reviewList;
    }

    public void saveReview(User user, SaveReviewDto reviewDto){

        Optional<Product> product = productRepository.findByProductId(reviewDto.getProductId());
        Optional<Together> together = togetherRepository.findByTogetherId(reviewDto.getProductId());

        if(!product.isEmpty()){ //상품이 있고

            Optional<Order> order = orderRepository.findByUserAndProduct(user, product.get());

            if(!order.isEmpty()){ //주문 내역이 존재하면
                ProductReview productReview = new ProductReview(user, product.get(), reviewDto);
                productReviewRepository.saveAndFlush(productReview);

                Long sellerId = product.get().getUser().getUserId(); //상품을 판 사람
                Optional<User> seller = userRepository.findByUserId(sellerId);

                if(!seller.isEmpty()){
                    userRepository.updateByUserId(seller.get().getTotalScore()+reviewDto.getScore(),
                            seller.get().getScoreNum()+1, sellerId);
                }
                else throw new CustomException(ErrorCode.USER_NOT_FOUND);
                return;
            }
        }
        if(!together.isEmpty()){ //상품이 있고
            Optional<Order> order = orderRepository.findByUserAndTogether(user, together.get());

            if(!order.isEmpty()) { //주문 내역이 존재하면
                TogetherReview togetherReview = new TogetherReview(user, together.get(), reviewDto);
                togetherReviewRepository.saveAndFlush(togetherReview);

                Long sellerId = together.get().getUser().getUserId(); //공동구매를 연 사람
                Optional<User> seller = userRepository.findByUserId(sellerId);

                if (!seller.isEmpty()) {
                    userRepository.updateByUserId(seller.get().getTotalScore()+reviewDto.getScore(),
                            seller.get().getScoreNum()+1, sellerId);
                }
                else throw new CustomException(ErrorCode.USER_NOT_FOUND);
                return;
            }
        }
    }

    public void modifyReview(User user, Long reviewId, SaveReviewDto reviewDto){

        Optional<ProductReview> productReview = productReviewRepository.findByProductReviewIdAndUserIdAndProductId(reviewId,
                user.getUserId(), reviewDto.getProductId());
        Optional<TogetherReview> togetherReview = togetherReviewRepository.findByTogetherReviewIdAndUserIdAndTogetherId(reviewId,
                user.getUserId(), reviewDto.getProductId());

        if(!productReview.isEmpty()){ //상품 후기가 있고

            Long sellerId = productReview.get().getProduct().getUser().getUserId(); //상품을 연 사람
            Optional<User> seller = userRepository.findByUserId(sellerId);

            if(!seller.isEmpty()){
                Integer x = productReview.get().getScore(); //원래의 받은 리뷰 별점
                userRepository.updateByUserId(seller.get().getTotalScore()-x+reviewDto.getScore(),
                        seller.get().getScoreNum(), sellerId);
            }
            else throw new CustomException(ErrorCode.USER_NOT_FOUND);
            productReviewRepository.updateByProductReviewId(reviewDto.getScore(), reviewDto.getContent(), reviewId);
        }
        else if(!togetherReview.isEmpty()){ //공구 후기가 있고
            Long sellerId = togetherReview.get().getTogether().getUser().getUserId(); //공동구매를 연 사람
            Optional<User> seller = userRepository.findByUserId(sellerId);

            if(!seller.isEmpty()){
                Integer x = togetherReview.get().getScore(); //원래의 받은 리뷰 별점
                userRepository.updateByUserId(seller.get().getTotalScore()-x+reviewDto.getScore(),
                        seller.get().getScoreNum(), sellerId);
            }
            else throw new CustomException(ErrorCode.USER_NOT_FOUND);
            togetherReviewRepository.updateByTogetherReviewId(reviewDto.getScore(), reviewDto.getContent(), reviewId);
        }
        else throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
    }

    public void deleteReview(User user, Long reviewId){
        Optional<ProductReview> productReview = productReviewRepository.findByProductReviewIdAndUser(reviewId, user);
        Optional<TogetherReview> togetherReview = togetherReviewRepository.findByTogetherReviewIdAndUser(reviewId, user);

        if(!productReview.isEmpty()){ //상품 후기가 있고
            Long sellerId = productReview.get().getProduct().getUser().getUserId(); //상품을 판 사람
            Optional<User> seller = userRepository.findByUserId(sellerId);
            if(!seller.isEmpty()){
                Integer x = productReview.get().getScore();
                userRepository.updateByUserId(seller.get().getTotalScore()-x,
                        seller.get().getScoreNum()-1, sellerId);
            }
            else throw new CustomException(ErrorCode.USER_NOT_FOUND);
            productReviewRepository.delete(productReview.get());
        }
        else if(!togetherReview.isEmpty()){ //공구 후기가 있고
            Long sellerId = togetherReview.get().getTogether().getUser().getUserId(); //공동구매를 연 사람
            Optional<User> seller = userRepository.findByUserId(sellerId);

            if(!seller.isEmpty()){
                Integer x = togetherReview.get().getScore(); //원래의 받은 리뷰 별점
                userRepository.updateByUserId(seller.get().getTotalScore()-x,
                        seller.get().getScoreNum()-1, sellerId);
            }
            else throw new CustomException(ErrorCode.USER_NOT_FOUND);
            togetherReviewRepository.delete(togetherReview.get());
        }
        else throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
    }
}