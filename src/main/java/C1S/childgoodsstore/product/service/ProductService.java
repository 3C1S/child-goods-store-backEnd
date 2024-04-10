package C1S.childgoodsstore.product.service;

import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.global.FileStore;
import C1S.childgoodsstore.product.converter.ProductConverter;
import C1S.childgoodsstore.product.dto.ProductDto;
import C1S.childgoodsstore.product.repository.ProductHeartRepository;
import C1S.childgoodsstore.product.repository.ProductImageRepository;
import C1S.childgoodsstore.product.repository.ProductRepository;
import C1S.childgoodsstore.product.repository.ProductTagRepository;
import C1S.childgoodsstore.tag.repository.TagRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductHeartRepository productHeartRepository;
    private final UserRepository userRepository;
    private final ProductConverter productConverter;
    private final ProductImageRepository productImageRepository;
    private final TagRepository tagRepository;
    private final ProductTagRepository productTagRepository;

    // controller - 상품 등록
    public Long postProduct(User user, ProductDto productDto) {
        // DTO로부터 Product 엔티티 변환
        Product product = productConverter.convertToEntity(user, productDto);
        // Product 저장
        Product savedProduct = productRepository.saveAndFlush(product);
        // ProductDto로부터 받은 이미지 리스트를 처리하여 저장
        saveProductImages(productDto.getImageList(), savedProduct);
        // ProductDto로부터 받은 태그 리스트를 처리하여 저장
        saveProductTags(productDto.getTag(), savedProduct);

        // 저장된 Product의 ID 반환
        return savedProduct.getProductId();
    }

    // 상품 이미지 리스트 ProductImage 테이블에 순서대로 저장
    private void saveProductImages(List<String> imageUrls, Product savedProduct) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (int i = 0; i < imageUrls.size(); i++) {
                // 각 이미지 URL과 순서, Product를 이용하여 ProductImage 인스턴스 생성
                ProductImage productImage = new ProductImage(imageUrls.get(i), i + 1, savedProduct);
                // 생성된 ProductImage 인스턴스를 데이터베이스에 저장
                productImageRepository.save(productImage);
            }
        }
    }

    // 상품 태그 리스트 ProductTag 테이블 및 Tag 테이블에 저장
    private void saveProductTags(List<String> tagNames, Product savedProduct) {
        if (tagNames != null && !tagNames.isEmpty()) {
            for (String tagName : tagNames) {
                // 태그 이름으로 Tag 테이블 검색, 없으면 새로 생성하여 저장
                Tag tag = tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                // Product와 Tag를 연결하는 ProductTag 인스턴스 생성
                ProductTag productTag = new ProductTag(savedProduct, tag);
                // 생성된 ProductTag 인스턴스를 데이터베이스에 저장
                productTagRepository.save(productTag);
            }
        }
    }

    public void setHeart(Long userId, Long productId) {

        Optional<ProductHeart> isHeart = productHeartRepository.findByUserAndProduct(userId, productId);
        User user;
        Product product;
        boolean isExist = true;

        if (isHeart.isEmpty()) {

            try {
                user = userRepository.findByUserId(userId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
            } catch (RuntimeException f) {
                throw new CustomException(ErrorCode.USER_NOT_FOUND);
            }

            try {
                product = productRepository.findByProductId(productId).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));
            } catch (RuntimeException g) {
                throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
            }

            ProductHeart productHeart = new ProductHeart();
            productHeart.setUser(user);
            productHeart.setProduct(product);
            productHeartRepository.save(productHeart);
        }
        else
            throw new CustomException(ErrorCode.PRODUCT_HEART_ALREADY);
    }

    public void deleteHeart(Long userId, Long productId) {

        ProductHeart productHeart;

        try{
            productHeart = productHeartRepository.findByUserAndProduct(userId, productId).get();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.PRODUCT_UNHEART_ALREADY);
        }

        productHeartRepository.deleteByHeartId(productHeart.getHeartId());
    }
}
