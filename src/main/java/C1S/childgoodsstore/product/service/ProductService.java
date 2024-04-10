package C1S.childgoodsstore.product.service;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.ProductHeart;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.product.converter.ProductConverter;
import C1S.childgoodsstore.product.dto.ProductDto;
import C1S.childgoodsstore.product.repository.ProductHeartRepository;
import C1S.childgoodsstore.product.repository.ProductRepository;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.util.exception.CustomException;
import C1S.childgoodsstore.util.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductHeartRepository productHeartRepository;
    private final UserRepository userRepository;
    private final ProductConverter productConverter;

    public Long postProduct(User user, ProductDto productDto) {
        Product product = productConverter.convertToEntity(user, productDto);
        Product savedProduct = productRepository.saveAndFlush(product);
        return savedProduct.getProductId();
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
