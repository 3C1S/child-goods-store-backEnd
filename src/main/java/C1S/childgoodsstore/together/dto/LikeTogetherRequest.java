package C1S.childgoodsstore.together.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeTogetherRequest {

    @NotNull(message = "Together Id cannot be blank")
    private Long togetherId;


}