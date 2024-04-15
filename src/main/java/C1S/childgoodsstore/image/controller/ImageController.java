package C1S.childgoodsstore.image.controller;

import C1S.childgoodsstore.global.response.ApiResponse;
import C1S.childgoodsstore.image.dto.ImageDto;
import C1S.childgoodsstore.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    // MultipartFile 객체 리스트를 S3에 저장
    @PostMapping()
    public ResponseEntity<ApiResponse<List<String>>> saveProductImage(@ModelAttribute ImageDto imageDto) {
        return ResponseEntity.ok().body(ApiResponse.success(imageService.saveImageList(imageDto)));
    }
}
