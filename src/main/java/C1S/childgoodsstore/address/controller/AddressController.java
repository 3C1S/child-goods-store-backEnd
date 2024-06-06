package C1S.childgoodsstore.address.controller;

import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.address.dto.AddressInterfaceDto;
import C1S.childgoodsstore.address.dto.UpdateAddressDto;
import C1S.childgoodsstore.address.service.AddressService;
import C1S.childgoodsstore.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import C1S.childgoodsstore.auth.presentation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address")
    public ResponseEntity<ApiResponse<List<AddressInterfaceDto>>> getAddress(@AuthenticationPrincipal User user)
    {
        return ResponseEntity.ok(ApiResponse.success(addressService.getAddress(user.getUserId())));
    }
    @PostMapping("/address")
    public ResponseEntity<ApiResponse<AddressInterfaceDto>> uploadAddress(@AuthenticationPrincipal User user,
                                  @RequestBody UpdateAddressDto updateAddressDto)
    {

        Address newAddress = new Address();
        newAddress.setUser(user);
        newAddress.setAddress(updateAddressDto.getAddress());
        newAddress.setDetailAddress(updateAddressDto.getDetailAddress());
        newAddress.setCategory(updateAddressDto.getCategory());

        Address saveAddress = addressService.save(newAddress);

        return ResponseEntity.ok(ApiResponse.success(addressService.findByAddressId(saveAddress.getAddressId())));
    }
    @PatchMapping(value = "/address/{addressId}")
    public ResponseEntity<ApiResponse<AddressInterfaceDto>> modifyAddress(@PathVariable("addressId") Long addressId, @AuthenticationPrincipal User user,
                                  @RequestBody UpdateAddressDto updateAddressDto)
    {

        addressService.update(updateAddressDto.getAddress(), updateAddressDto.getDetailAddress()
        ,updateAddressDto.getCategory(), addressId);

        return ResponseEntity.ok(ApiResponse.success(addressService.findByAddressId(addressId)));
    }
}
