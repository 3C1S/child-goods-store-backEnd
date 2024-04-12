package C1S.childgoodsstore.product.service;

import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.product.converter.ProductConverter;
import C1S.childgoodsstore.product.dto.input.CreateProductDto;
import C1S.childgoodsstore.product.dto.output.ProductDetailsDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public Long postProduct(User user, CreateProductDto productDto) {
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

    // controller - 상품 수정
    public Long updateProduct(User user, Long productId, CreateProductDto productDto) {
        // 기존 상품 찾기
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        // 기본 정보 업데이트
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setContent(productDto.getContent());
        product.setProductState(productDto.getProductState());
        product.setMainCategory(productDto.getMainCategory());
        product.setSubCategory(productDto.getSubCategory());
        // 이미지 리스트 처리
        updateProductImages(product, productDto.getImageList());
        // 태그 리스트 처리
        updateProductTags(product, productDto.getTag());
        // 변경된 정보를 데이터베이스에 저장
        productRepository.save(product);

        // 업데이트된 상품의 ID 반환
        return product.getProductId();
    }

    // 상품 이미지 수정
    public void updateProductImages(Product product, List<String> imageUrls) {
        if (imageUrls == null || imageUrls.isEmpty()) {
            // 입력된 이미지 링크가 없는 경우 기존 모든 이미지 제거
            product.getProductImages().clear();
        } else {
            // 업데이트할 이미지 목록을 저장할 임시 리스트
            List<ProductImage> updatedImages = new ArrayList<>();

            // imageUrls 리스트를 순회하면서 각 이미지 URL에 대한 처리
            for (int i = 0; i < imageUrls.size(); i++) {
                // 기존 이미지 리스트의 크기보다 인덱스가 작은 경우, 기존 이미지를 업데이트
                if (i < product.getProductImages().size()) {
                    ProductImage existingImage = product.getProductImages().get(i);
                    existingImage.setImageUrl(imageUrls.get(i));  // 이미지 URL 업데이트
                    existingImage.setImageOrder(i);  // 이미지 순서 업데이트
                    updatedImages.add(existingImage);  // 업데이트된 이미지를 임시 리스트에 추가
                } else {
                    // 기존의 이미지 리스트보다 더 많은 이미지가 제공된 경우, 새 이미지를 추가
                    updatedImages.add(new ProductImage(imageUrls.get(i), i, product));
                }
            }
            // 기존의 이미지 목록을 클리어하고, 업데이트된 이미지 목록으로 대체
            product.getProductImages().clear();
            product.getProductImages().addAll(updatedImages);
        }
    }

    // 상품 태그 수정
    @Transactional
    public void updateProductTags(Product product, List<String> tagNames) {
        if (tagNames == null) {
            // 입력된 태그가 없으면 기존 태그 제거
            product.getProductTags().clear();
        } else {
            // 기존 태그 목록에서 이름을 기준으로 맵 생성
            Map<String, ProductTag> existingTags = product.getProductTags().stream()
                    .collect(Collectors.toMap(tag -> tag.getTag().getName(), tag -> tag));

            List<ProductTag> updatedTags = new ArrayList<>();

            for (String tagName : tagNames) {
                // 이미 존재하는 태그인지 확인
                if (!existingTags.containsKey(tagName)) {
                    // 새로운 태그 생성
                    Tag newTag = new Tag(tagName);
                    tagRepository.save(newTag); // 새 태그 저장
                    ProductTag newProductTag = new ProductTag(product, newTag);
                    updatedTags.add(newProductTag);
                } else {
                    // 이미 존재하는 태그면 기존 태그 사용
                    updatedTags.add(existingTags.get(tagName));
                }
            }

            // 모든 기존 태그를 제거하고 새로운 태그 목록을 할당
            product.getProductTags().clear();
            product.getProductTags().addAll(updatedTags);
        }
    }

    // controller - 상품 조회
    public ProductDetailsDto getProduct(User user, Long productId) {
        // Product 엔티티 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // ProductHeart 존재 여부 확인
        boolean hasHeart = productHeartRepository.existsByUserAndProduct(user, product);

        // ProductDto 생성 및 반환
        return ProductDetailsDto.fromProduct(product, hasHeart);
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
