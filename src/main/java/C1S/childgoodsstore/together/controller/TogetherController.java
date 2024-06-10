package C1S.childgoodsstore.together.controller;

import C1S.childgoodsstore.enums.AGE;
import C1S.childgoodsstore.enums.MAIN_CATEGORY;
import C1S.childgoodsstore.enums.SUB_CATEGORY;
import C1S.childgoodsstore.global.response.ApiResponse;
import C1S.childgoodsstore.product.dto.input.ProductSearchCriteriaDto;
import C1S.childgoodsstore.security.auth.PrincipalDetails;
import C1S.childgoodsstore.together.dto.input.CreateTogetherDto;
import C1S.childgoodsstore.together.dto.LikeTogetherRequest;
import C1S.childgoodsstore.together.dto.input.TogetherSearchCriteriaDto;
import C1S.childgoodsstore.together.dto.output.TogetherDetailsDto;
import C1S.childgoodsstore.together.dto.output.TogetherListDto;
import C1S.childgoodsstore.together.service.TogetherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/together")
public class TogetherController {

    private final TogetherService togetherService;

    //공동구매 상품 목록 조회
    @GetMapping("")
    public ResponseEntity<ApiResponse<List<TogetherListDto>>> getTogetherList(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestParam(name = "mainCategory", required = false) MAIN_CATEGORY mainCategory,
            @RequestParam(name = "subCategory", required = false) SUB_CATEGORY subCategory,
            @RequestParam(name = "region", defaultValue = "ALL") String region,
            @RequestParam(name = "age", required = false) AGE age,
            @RequestParam(name = "page", defaultValue = "0") Integer page) {

        TogetherSearchCriteriaDto criteria = new TogetherSearchCriteriaDto();
        criteria.setMainCategory(mainCategory);
        criteria.setSubCategory(subCategory);
        criteria.setRegion(region);
        criteria.setAge(age);
        criteria.setPage(page);

        return ResponseEntity.ok().body(ApiResponse.success(togetherService.getTogetherList(principalDetails.getUser(), criteria)));
    }

    //공동구매글 등록
    @PostMapping("")
    public ResponseEntity<ApiResponse<Long>> postTogether(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                          @Valid @RequestBody CreateTogetherDto togetherDto) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.postTogether(principalDetails.getUser(), togetherDto)));
    }

    //공동구매글 수정
    @PatchMapping("{togetherId}")
    public ResponseEntity<ApiResponse<Long>> updateTogether(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                            @PathVariable("togetherId") Long togetherId,
                                                            @Valid @RequestBody CreateTogetherDto togetherDto) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.updateTogether(principalDetails.getUser(), togetherId, togetherDto)));
    }

    //공동구매 상품 상세조회
    @GetMapping("{togetherId}")
    public ResponseEntity<ApiResponse<TogetherDetailsDto>> getTogetherDetails(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable("togetherId") Long togetherId) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.getTogetherDetails(principalDetails.getUser(), togetherId)));
    }

    //공동구매 관심 등록
    @PostMapping("/heart")
    public ResponseEntity<ApiResponse<Void>> likeTogether(@AuthenticationPrincipal PrincipalDetails principalDetails, @Valid @RequestBody LikeTogetherRequest likeTogetherRequest) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.likeTogether(principalDetails.getUser(), likeTogetherRequest)));
    }

    //공동구매 관심 삭제
    @DeleteMapping("/heart/{togetherId}")
    public ResponseEntity<ApiResponse<Void>> unlikeTogether(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable Long togetherId) {
        return ResponseEntity.ok().body(ApiResponse.success(togetherService.unlikeTogether(principalDetails.getUser(), togetherId)));
    }
}
