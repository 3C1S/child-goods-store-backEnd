package C1S.childgoodsstore.user.service;

import C1S.childgoodsstore.address.repository.AddressRepository;
import C1S.childgoodsstore.chatting.repository.ChattingRepository;
import C1S.childgoodsstore.chatting.repository.ChattingRoomUserRepository;
import C1S.childgoodsstore.child.repository.ChildRepository;
import C1S.childgoodsstore.child.repository.ChildTagRepository;
import C1S.childgoodsstore.entity.Child;
import C1S.childgoodsstore.entity.Following;
import C1S.childgoodsstore.entity.Product;
import C1S.childgoodsstore.enums.PRODUCT_SALE_STATUS;
import C1S.childgoodsstore.enums.ROLE;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.following.repository.FollowingRepository;
import C1S.childgoodsstore.order.repository.OrderRepository;
import C1S.childgoodsstore.product.repository.ProductHeartRepository;
import C1S.childgoodsstore.product.repository.ProductRepository;
import C1S.childgoodsstore.review.repository.ProductReviewRepository;
import C1S.childgoodsstore.review.repository.TogetherReviewRepository;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import C1S.childgoodsstore.user.dto.*;
import C1S.childgoodsstore.user.repository.UserRepository;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ProductRepository productRepository;
    private final TogetherRepository togetherRepository;
    private final ChildRepository childRepository;
    private final ChildTagRepository childTagRepository;
    private final AddressRepository addressRepository;
    private final FollowingRepository followingRepository;
    private final ProductHeartRepository productHeartRepository;
    private final OrderRepository orderRepository;
    private final ChattingRepository chattingRepository;
    private final ChattingRoomUserRepository chattingRoomUserRepository;
    private final ProductReviewRepository productReviewRepository;
    private final TogetherReviewRepository togetherReviewRepository;


    public Long save(SignUpDto signUpDto) {

        Optional<User> findUser = userRepository.findByEmail(signUpDto.getEmail());

        if(!findUser.isEmpty())
            throw new CustomException(ErrorCode.USER_EMAIL_DUPLICATED);

        User savedUser = userRepository.saveAndFlush(new User(signUpDto.getEmail(), bCryptPasswordEncoder.encode(signUpDto.getPassword()), ROLE.USER));

        return savedUser.getUserId();
    }

    //내 정보 조회(자동로그인)
    public AutoLoginResultDto autoLogin(Long userId) {

        User user = findUserByUserId(userId);
        return new AutoLoginResultDto(user);
    }

    //내 프로필 조회
    public MyProfileDto getMyProfile(Long userId) {

        User user = findUserByUserId(userId);

        int followerNum = followingRepository.countFollowersByUserId(userId);
        int followingNum = followingRepository.countFollowingsByUserId(userId);

        return new MyProfileDto(user, followingNum, followerNum);
    }

    //타 유저의 프로필 조회
    public UserProfileDto getUserProfile(Long userId, Long followId) {

        User user = findUserByUserId(followId);

        int followerNum = followingRepository.countFollowersByUserId(followId);
        int followingNum = followingRepository.countFollowingsByUserId(followId);

        boolean isFollowing = followingRepository.isFollow(userId, followId).isPresent();

        return new UserProfileDto(user, isFollowing, followingNum, followerNum);
    }

    //내 정보(프로필) 작성
    //내 정보(프로필) 수정
    public InfoResultDto saveInfo(Long userId, InfoSaveDto infoSaveDto) {

        User user = findUserByUserId(userId);
        user.setUser(infoSaveDto);
        InfoResultDto infoResultDto = new InfoResultDto(user);
        return infoResultDto;
    }

    public void withdrawalUser(User user){

        //106-상품 상세 조회, 112-거래자 조회 - 완
        //205-공동구매 상세 - 아직 안됨
        //14-내가 받은 후기 조회 - 완
        //302-채팅방 상세 조회 - 완

        //product와 together는 지우진 않고, 상태는 바꾸고, user를 null
        List<Product> productList = productRepository.findAllByUserUserId(user.getUserId());
        for(Product product: productList){
            product.setUser(null);
            product.setState(PRODUCT_SALE_STATUS.DELETE);
            productRepository.save(product);
        }
        togetherRepository.deleteTogetherByUser(user);
        orderRepository.deleteByUser(user); //OrderRecord는 지우면 안됨 - 판매 내역 조회가 있어서
        //따라서 해당 사용자가 올린 상품에 대한 ProductReview, TogetherReview는 user만 null
        productReviewRepository.deleteByUser(user);
        togetherReviewRepository.deleteByUser(user);

        //Child, ChildTag, Address, Following, ProductHeart(해당 사용자가 다른 상품에 누른 찜)
        List<Child> childList =  childRepository.findByParent(user);
        for(Child child: childList){
            childTagRepository.deleteByChild(child);
        }
        childRepository.deleteByParent(user);
        addressRepository.deleteByUser(user);
        followingRepository.deleteByUser(user);
        followingRepository.deleteByFollowId(user.getUserId());
        productHeartRepository.deleteByUser(user);

        //Chatting도 user를 null
        chattingRepository.deleteByUser(user);
        //ChattingRoom은 아무것도 안 삭제
        //ChattingRoomUser도 user를 null
        chattingRoomUserRepository.deleteByUser(user);

        userRepository.delete(user);
    }

    private User findUserByUserId(Long userId) {

        User user;
        try{
            user = userRepository.findByUserId(userId).get();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        return user;
    }
}
