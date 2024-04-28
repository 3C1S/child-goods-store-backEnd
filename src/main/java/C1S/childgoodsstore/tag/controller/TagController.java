package C1S.childgoodsstore.tag.controller;

import C1S.childgoodsstore.global.response.ApiResponse;
import C1S.childgoodsstore.tag.dto.KeywordDto;
import C1S.childgoodsstore.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping("/tag")
    public ResponseEntity<ApiResponse<List<String>>> getTag(@RequestBody KeywordDto keyword){
        return ResponseEntity.ok(ApiResponse.success(tagService.getTag(keyword.getKeyword())));
    }
}
