package C1S.childgoodsstore.together.service;

import C1S.childgoodsstore.entity.Together;
import C1S.childgoodsstore.entity.User;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import C1S.childgoodsstore.together.dto.LikeTogetherRequest;
import C1S.childgoodsstore.together.repository.TogetherRepository;
import C1S.childgoodsstore.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class TogetherService {
    private final TogetherRepository togetherRepository;
    private final RedisUtil redisUtil;
    public Void likeTogether(User user, LikeTogetherRequest likeTogetherRequest) {
        Together together = togetherRepository.findById(likeTogetherRequest.getTogetherId())
                .orElseThrow(() -> new CustomException(ErrorCode.TOGETHER_NOT_FOUND));

        redisUtil.addTogetherLike(user.getUserId().toString(), likeTogetherRequest.getTogetherId().toString());

        together.incrementLikeNum();
        togetherRepository.save(together);

        return null;
    }

    public Void unlikeTogether(User user, LikeTogetherRequest likeTogetherRequest) {
        Together together = togetherRepository.findById(likeTogetherRequest.getTogetherId())
                .orElseThrow(() -> new CustomException(ErrorCode.TOGETHER_NOT_FOUND));
        redisUtil.removeTogetherLike(user.getUserId().toString(), likeTogetherRequest.getTogetherId().toString());

        together.decrementLikeNum();
        togetherRepository.save(together);

        return null;
    }
}
