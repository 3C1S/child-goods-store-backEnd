package C1S.childgoodsstore.mail.controller;

import C1S.childgoodsstore.mail.dto.EmailCheckDto;
import C1S.childgoodsstore.mail.dto.EmailRequestDto;
import C1S.childgoodsstore.mail.service.MailService;
import C1S.childgoodsstore.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>> mailSend(@RequestBody @Valid EmailRequestDto emailRequestDto){
        return ResponseEntity.ok().body(ApiResponse.success(mailService.joinEmail(emailRequestDto.getEmail())));
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Boolean>> AuthCheck(@RequestBody @Valid EmailCheckDto emailCheckDto) {
        return ResponseEntity.ok().body(ApiResponse.success(mailService.CheckAuthNum(emailCheckDto.getEmail(), emailCheckDto.getAuthNum())));
    }
}