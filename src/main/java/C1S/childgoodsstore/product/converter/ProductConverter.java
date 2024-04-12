package C1S.childgoodsstore.product.converter;

import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import C1S.childgoodsstore.product.dto.input.CreateProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {

    public Product convertToEntity(User user, CreateProductDto productDto) {
        Product product = new Product();
        product.setUser(user);
        product.setProductName(productDto.getProductName());
        product.setPrice(productDto.getPrice());
        product.setContent(productDto.getContent());
        product.setState(PRODUCT_SALE_STATUS.SALE); // 기본값 설정
        product.setProductState(productDto.getProductState());
        product.setMainCategory(productDto.getMainCategory());
        product.setSubCategory(productDto.getSubCategory());
        return product;
    }

}
