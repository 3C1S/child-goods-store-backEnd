package C1S.childgoodsstore.address.repository;

import C1S.childgoodsstore.entity.Address;
import C1S.childgoodsstore.address.dto.AddressInterfaceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "select a from Address a where a.user.userId = ?1")
    List<AddressInterfaceDto> findByUserId(Integer userId);

    @Query(value = "update Address a set a.address = ?1, a.detail_address = ?2, a.category = ?3 where a.address_id = ?4", nativeQuery = true)
    void updateByAddressId(String address, String detailAddress, String category, Integer addressId);

    @Query(value = "select a from Address a where a.addressId = ?1")
    AddressInterfaceDto findByAddressId(Integer addressId);
}
