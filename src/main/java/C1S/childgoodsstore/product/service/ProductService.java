package C1S.childgoodsstore.product.service;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.ProductHeart;
import C1S.childgoodsstore.entity.User;
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
    private final UserRepository userRepository;

    public void setHeart(Long userId, Long productId) {

        Optional<ProductHeart> isHeart = productRepository.findByUserAndProduct(userId, productId);
        ProductHeart productHeart = new ProductHeart();
        User user;
        Product product;

        if(!isHeart.isEmpty()) {
            throw new CustomException(ErrorCode.PRODUCT_HEART_ALREADY);
        }

        try {
            user = userRepository.findByUserId(userId).get();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        try {
            product = productRepository.findByProductId(productId);
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        productRepository.save(productHeart);
    }
}
