package C1S.childgoodsstore.address.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UpdateAddressDto {
    private String address;
    private String detailAddress;
    private String category;
}
