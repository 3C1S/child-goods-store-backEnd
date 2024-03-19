package C1S.childgoodsstore.address.controller;

import C1S.childgoodsstore.entity.*;
import C1S.childgoodsstore.address.dto.AddressInterfaceDto;
import C1S.childgoodsstore.address.dto.UpdateAddressDto;
import C1S.childgoodsstore.address.service.AddressService;
import C1S.childgoodsstore.following.service.FollowingService;
import C1S.childgoodsstore.user.service.UserService;
import C1S.childgoodsstore.util.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    //@AuthenticationPrincipal PrincipalDetails principalDetails 이거 사용해서 유저 받아오면 됨.
    @GetMapping("/address")
    public ResponseEntity<ApiResponse<List<AddressInterfaceDto>>> getAddress()
    {
        //유저 받아와야 함.

        //principalDetails.getUser()

        return ResponseEntity.ok(ApiResponse.success(addressService.getAddress(1)));
    }
    @PostMapping("/address")
    public ResponseEntity<ApiResponse<AddressInterfaceDto>> uploadAddress(//@RequestHeader("Authorization") String token,
                                  @RequestBody UpdateAddressDto updateAddressDto)
    {

        Optional<User> user = userService.findByUserId(1); //유저 아이디로

        Address newAddress = new Address();
        newAddress.setUser(user.get());
        newAddress.setAddress(updateAddressDto.getAddress());
        newAddress.setDetailAddress(updateAddressDto.getDetailAddress());
        newAddress.setCategory(updateAddressDto.getCategory());

        Address saveAddress = addressService.save(newAddress);

        return ResponseEntity.ok(ApiResponse.success(addressService.findByAddressId(saveAddress.getAddressId())));
    }
    @PatchMapping(value = "/address/{addressId}")
    public ResponseEntity<ApiResponse<AddressInterfaceDto>> modifyAddress(@PathVariable("addressId") String addressId, //@RequestHeader("Authorization") String token,
                                  @RequestBody UpdateAddressDto updateAddressDto)
    {

        addressService.update(updateAddressDto.getAddress(), updateAddressDto.getDetailAddress()
        ,updateAddressDto.getCategory(), Integer.valueOf(addressId));

        return ResponseEntity.ok(ApiResponse.success(addressService.findByAddressId(Integer.valueOf(addressId))));
    }
}
