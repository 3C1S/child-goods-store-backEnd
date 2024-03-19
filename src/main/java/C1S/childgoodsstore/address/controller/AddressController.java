package C1S.childgoodsstore.address.controller;

import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.address.dto.AddressInterfaceDto;
import C1S.childgoodsstore.address.dto.UpdateAddressDto;
import C1S.childgoodsstore.address.service.AddressService;
import C1S.childgoodsstore.following.service.FollowingService;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.user.service.UserService;
import C1S.childgoodsstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/address")
    public ResponseEntity<ApiResponse<List<AddressInterfaceDto>>> getAddress(@AuthenticationPrincipal PrincipalDetails principalDetails)
    {
        return ResponseEntity.ok(ApiResponse.success(addressService.getAddress(principalDetails.getUser().getUserId())));
    }
    @PostMapping("/address")
    public ResponseEntity<ApiResponse<AddressInterfaceDto>> uploadAddress(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                  @RequestBody UpdateAddressDto updateAddressDto)
    {

        Address newAddress = new Address();
        newAddress.setUser(principalDetails.getUser());
        newAddress.setAddress(updateAddressDto.getAddress());
        newAddress.setDetailAddress(updateAddressDto.getDetailAddress());
        newAddress.setCategory(updateAddressDto.getCategory());

        Address saveAddress = addressService.save(newAddress);

        return ResponseEntity.ok(ApiResponse.success(addressService.findByAddressId(saveAddress.getAddressId())));
    }
    @PatchMapping(value = "/address/{addressId}")
    public ResponseEntity<ApiResponse<AddressInterfaceDto>> modifyAddress(@PathVariable("addressId") Long addressId, @AuthenticationPrincipal PrincipalDetails principalDetails,
                                  @RequestBody UpdateAddressDto updateAddressDto)
    {

        addressService.update(updateAddressDto.getAddress(), updateAddressDto.getDetailAddress()
        ,updateAddressDto.getCategory(), addressId);

        return ResponseEntity.ok(ApiResponse.success(addressService.findByAddressId(addressId)));
    }
}
