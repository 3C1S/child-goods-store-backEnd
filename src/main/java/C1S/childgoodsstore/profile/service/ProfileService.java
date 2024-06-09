package C1S.childgoodsstore.profile.service;

import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.product.repository.ProductHeartRepository;
import C1S.childgoodsstore.product.repository.ProductImageRepository;
import C1S.childgoodsstore.product.repository.ProductRepository;
import C1S.childgoodsstore.profile.dto.MypageProductListDto;
import C1S.childgoodsstore.profile.dto.PurchaseProductListDto;
import C1S.childgoodsstore.order.repository.OrderRepository;
import C1S.childgoodsstore.profile.dto.TogetherDto;
import C1S.childgoodsstore.profile.dto.TogetherOrderDto;
import C1S.childgoodsstore.review.repository.ProductReviewRepository;
import C1S.childgoodsstore.review.repository.TogetherReviewRepository;
import C1S.childgoodsstore.together.repository.TogetherImageRepository;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    private final RedisUtil redisUtil;
    private final TogetherRepository togetherRepository;
    private final TogetherImageRepository togetherImageRepository;
    private final TogetherReviewRepository togetherReviewRepository;

    //마이페이지 판매 게시글 목록 조회
    public List<MypageProductListDto> getMypageSaleProduct(Long userId, int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<Product> products = productRepository.findAllByUserUserId(userId, pageable);
        return createMypageProductList(products, userId);
    }

    //상품 관심 목록 조회
    public List<MypageProductListDto> getMyProductHeart(Long userId, int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<ProductHeart> productHearts = productHeartRepository.findAllByUserUserId(userId, pageable);
        List<Product> products = productHearts.stream()
                .map(ProductHeart::getProduct)
                .collect(Collectors.toList());
        Page<Product> productPage = new PageImpl<>(products, pageable, productHearts.getTotalElements());
        return createMypageProductList(productPage, userId);
    }

    // 상품 구매 내역 조회
    public List<PurchaseProductListDto> getPurchaseProduct(Long userId, int page) {

        Pageable pageable = PageRequest.of(page, 10);
        Page<OrderRecord> orders = orderRepository.findAllByUserUserId(userId, pageable);
        List<PurchaseProductListDto> purchaseProductList = new ArrayList<>();

        if(orders != null && !orders.isEmpty()) {

            for (OrderRecord order : orders) {

                boolean isReview = false;
                Optional<ProductReview> productReview = productReviewRepository.findByUserAndProduct(userId, order.getProduct().getProductId());
                if (productReview.isPresent()) {
                    isReview = true;
                }

                String profileImg = "";
                Optional<ProductImage> productImage = productImageRepository.findByProductIdAndOrder(order.getProduct().getProductId(), 1);
                if (productImage.isPresent()) {
                    profileImg = productImage.get().getImageUrl();
                }
                purchaseProductList.add(new PurchaseProductListDto(order.getProduct(), profileImg, order.getCreatedAt(), isReview));
            }
        }
        return purchaseProductList;
    }

    private List<MypageProductListDto> createMypageProductList(Page<Product> products, Long userId) {

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
            if (productHeart.isEmpty())
                isHeart = false;
            mypageProductList.add(new MypageProductListDto(product, profileImg, isHeart));
        }
        return mypageProductList;
    }

    public List<TogetherDto> getLikesForTogether(Long userId, Pageable pageable) {
        // Redis에서 사용자가 좋아요 누른 Together ID 목록 가져오기
        Set<String> togetherIds = redisUtil.getTogetherLikes(userId.toString());
        List<Long> ids = togetherIds.stream().map(Long::parseLong).collect(Collectors.toList());

        // 데이터베이스에서 Together 객체들 조회
        Page<Together> togethers = togetherRepository.findAllByTogetherIdIn(ids, pageable);

        // Together 객체들을 TogetherDto로 변환
        return togethers.getContent().stream()
                .map(together -> {
                    String imageUrl = togetherImageRepository.findById(together.getTogetherId())
                            .stream()
                            .map(TogetherImage::getImageUrl)
                            .findFirst() // Optional<String>을 반환
                            .orElse(null); // Optional이 비어 있으면 null 반환
                    return TogetherDto.fromEntity(together, imageUrl);
                })
                .collect(Collectors.toList());
    }

    public List<TogetherDto> getProfileTogether(Long userId, Pageable pageable) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Page<Together> togethers = togetherRepository.findByUser(user, pageable);

        // 변환: Entity를 DTO로
        return togethers.getContent().stream()
                .map(together -> {
                    String imageUrl = togetherImageRepository.findById(together.getTogetherId())
                            .stream()
                            .map(TogetherImage::getImageUrl)
                            .findFirst() // Optional<String>을 반환
                            .orElse(null); // Optional이 비어 있으면 null 반환
                    return TogetherDto.fromEntity(together, imageUrl);
                })
                .collect(Collectors.toList());
    }

    public List<TogetherOrderDto> getTogetherOrder(User user, Pageable pageable) {
        Page<OrderRecord> orderRecords = orderRepository.findByUserAndTogetherIsNotNull(user, pageable);

        return orderRecords.getContent().stream()
                .map(orderRecord -> {
                    String imageUrl = orderRecord.getTogether() != null ?
                            togetherImageRepository.findByTogether(orderRecord.getTogether())
                                    .stream()
                                    .map(TogetherImage::getImageUrl)
                                    .findFirst().orElse(null) : null;  // 이미지 URL을 찾거나 null 반환
                    boolean isReview = togetherReviewRepository.findByUserAndTogether(orderRecord.getUser(), orderRecord.getTogether()).isPresent();  // 리뷰 여부를 확인하는 로직 구현 필요
                    return new TogetherOrderDto(orderRecord, imageUrl, isReview);
                })
                .collect(Collectors.toList());
    }

}

