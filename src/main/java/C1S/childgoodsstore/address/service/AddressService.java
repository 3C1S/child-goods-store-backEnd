package C1S.childgoodsstore.address.service;

import C1S.childgoodsstore.entity.Address;
import C1S.childgoodsstore.address.dto.AddressInterfaceDto;
import C1S.childgoodsstore.address.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<AddressInterfaceDto> getAddress(Integer userId){
        return addressRepository.findByUserId(userId);
    }

    public Address save(Address address){
        return addressRepository.save(address);
    }

    public void update(String address, String detailAddress, String category, Integer addressId){
        addressRepository.updateByAddressId(address, detailAddress, category, addressId);
    }

    public AddressInterfaceDto findByAddressId(Integer addressId) { return  addressRepository.findByAddressId(addressId);}



}
