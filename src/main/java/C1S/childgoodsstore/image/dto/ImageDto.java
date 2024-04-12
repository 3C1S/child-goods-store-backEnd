package C1S.childgoodsstore.image.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ImageDto {

    @NotNull(message = "이미지 리스트는 null일 수 없습니다.")
    @NotEmpty(message = "이미지 리스트는 비어 있을 수 없습니다.")
    private List<MultipartFile> imageList;

    @NotEmpty(message = "폴더 이름은 비어 있을 수 없습니다.")
    private String folderName;
}
