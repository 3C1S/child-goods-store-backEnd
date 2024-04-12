package C1S.childgoodsstore.profile.service;

import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.product.repository.ProductHeartRepository;
import C1S.childgoodsstore.product.repository.ProductImageRepository;
import C1S.childgoodsstore.product.repository.ProductRepository;
import C1S.childgoodsstore.profile.dto.MypageProductListDto;
import C1S.childgoodsstore.profile.dto.PurchaseProductListDto;
import C1S.childgoodsstore.review.repository.OrderRepository;
import C1S.childgoodsstore.review.repository.ProductReviewRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductHeartRepository productHeartRepository;
    private final ProductImageRepository productImageRepository;
    private final OrderRepository orderRepository;
    private final ProductReviewRepository productReviewRepository;

    public List<MypageProductListDto> getMypageSaleProduct(Long userId) {

        User user = getUserById(userId);

        List<Product> products = productRepository.findAllByUserUserId(userId);
        return createMypageProductList(products, userId);
    }

    public List<MypageProductListDto> getMyProductHeart(Long userId) {

        User user = getUserById(userId);

        List<ProductHeart> productHearts = productHeartRepository.findAllByUserUserId(userId);
        List<Product> products = productHearts.stream().map(ProductHeart::getProduct).collect(Collectors.toList());
        return createMypageProductList(products, userId);
    }

    public List<PurchaseProductListDto> getPurchaseProduct(Long userId) {

        User user = getUserById(userId);

        List<Order> orders = orderRepository.findAllByUser(user); // 오류 발생
        List<PurchaseProductListDto> purchaseProductList = new ArrayList<>();

        for(Order order : orders) {

            boolean isReview = false;
            Optional<ProductReview> productReview = productReviewRepository.findByUserAndProduct(userId, order.getProduct().getProductId());
            if(!productReview.isEmpty()) {
                isReview = true;
            }
            purchaseProductList.add(new PurchaseProductListDto(order.getProduct(), order.getCreatedAt(), isReview));
        }
        return purchaseProductList;
    }

    private User getUserById(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    private List<MypageProductListDto> createMypageProductList(List<Product> products, Long userId) {

        List<MypageProductListDto> mypageProductList = new ArrayList<>();

        for (Product product : products) {

            String profileImg = "";
            int imageOrder = 1;

            Optional<ProductImage> productImage = productImageRepository.findByProductIdAndOrder(product.getProductId(), imageOrder);
            if (productImage.isPresent()) {
                profileImg = productImage.get().getImageUrl();
            }

            boolean isHeart = true;
            Optional<ProductHeart> productHeart = productHeartRepository.findByUserAndProduct(userId, product.getProductId());
            if(productHeart.isEmpty())
                isHeart = false;
            mypageProductList.add(new MypageProductListDto(product, profileImg, isHeart));
        }
        return mypageProductList;
    }
}
