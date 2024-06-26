package C1S.childgoodsstore.mail.service;

import C1S.childgoodsstore.util.RedisUtil;
import C1S.childgoodsstore.global.exception.CustomException;
import C1S.childgoodsstore.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisUtil redisUtil;
    private int authNumber;

    public void makeRandomNumber() {

        Random r = new Random();
        String randomNumber = "";
        for(int i = 0; i < 6; i++) {
            randomNumber += Integer.toString(r.nextInt(10));
        }
        authNumber = Integer.parseInt(randomNumber);
    }

    public String joinEmail(String email) {

        makeRandomNumber();
        String setFrom = "ariel020918@naver.com"; //email-config에 설정한 이메일 주소
        String toMail = email;
        String title = "회원 가입 인증 이메일 입니다.";
        String content =
                "나의 APP을 방문해주셔서 감사합니다." +
                        "<br><br>" +
                        "인증 번호는 " + authNumber + "입니다." +
                        "<br>" +
                        "인증번호를 제대로 입력해주세요"; //html 형식으로 작성
        mailSend(setFrom, toMail, title, content);
        return Integer.toString(authNumber);
    }

    public void mailSend(String setFrom, String toMail, String title, String content) {

        MimeMessage message = mailSender.createMimeMessage(); //JavaMailSender 객체를 사용하여 MimeMessage 객체를 생성
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8"); //이메일 메시지와 관련된 설정을 수행
            //true를 전달하여 multipart 형식의 메시지를 지원하고, "utf-8"을 전달하여 문자 인코딩을 설정
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content,true); //두 번째 매개 변수에 true를 설정하여 html 설정으로 함.
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        redisUtil.setDataExpire(Integer.toString(authNumber),toMail,60*5L);
    }

    public boolean CheckAuthNum(String email,String authNum){

        if(redisUtil.getData(authNum)==null) {
            throw new CustomException(ErrorCode.EMAIL_AUTHENTICATION_FAILED);
        }
        else if(redisUtil.getData(authNum).equals(email)) {
            return true;
        }
        else {
            throw new CustomException(ErrorCode.EMAIL_AUTHENTICATION_FAILED);
        }
    }
}