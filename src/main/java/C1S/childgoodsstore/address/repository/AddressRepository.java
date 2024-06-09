package C1S.childgoodsstore.address.repository;

import C1S.childgoodsstore.entity.Address;
import C1S.childgoodsstore.address.dto.AddressInterfaceDto;
import C1S.childgoodsstore.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
@Transactional

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query(value = "select a from Address a where a.user.userId = ?1")
    List<AddressInterfaceDto> findByUserId(Long userId);

    @Modifying
    @Query("update Address a set a.address = :address, a.detailAddress = :detailAddress, a.category = :category where a.addressId = :addressId")
    void updateByAddressId(@Param("address") String address, @Param("detailAddress") String detailAddress, @Param("category") String category, @Param("addressId") Long addressId);

    @Query(value = "select a from Address a where a.addressId = ?1")
    AddressInterfaceDto findByAddressId(Long addressId);

    void deleteByUser(User user);
}
