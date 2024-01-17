package com.shop.service;

import com.shop.dto.user.UserDTO;
import com.shop.mappers.UserMapper;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserMailService {

    private final String SEND_EMAIL_FROM = "jaeho9859@naver.com";
    private final String RESET_PASSWORD_URL = "http://localhost:8080/user/password?token=";

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine; //config에 설정한 emailTempleateEngine
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public String find_user_id(String phoneNum) {
        String userID = userMapper.fine_user_id(phoneNum);
        if (Objects.isNull(userID)) {
            return "해당 유저가 없음";
        }

        try{
            return "유저 id: " + userID;
        } catch (Exception e) {
            return "일시적인 문제입니다. 다시 시도하세요.";
        }
    }

//    public String find_user_pw() {
//
//    }

//    private void send_sms_message(String phoneNum) throws Exception {
//        String timestamp = String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime());		// current timestamp (epoch)
//
//        String signature = makeSignature("POST", "sms/v2/services/" + NAVER_SMS_SERVICE_KEY + "/messages" + timestamp);
//        // 받을 사람
//        JSONObject sendMessageTemplate = new JSONObject();
//        sendMessageTemplate.put("to", phoneNum);
//        JSONArray jsonArray = new JSONArray();
//        jsonArray.add(sendMessageTemplate);
//
//
//        // 메세지 정보
//        JSONObject messageObject = new JSONObject();
//        messageObject.put("type", "SMS");
//        messageObject.put("contentType", "COMM");
//        messageObject.put("from", "123456789"); // 내 전화번호
//        messageObject.put("content", "[koreaIt]\n 조회된 아이디: 어쩌구"); // 메세지 내용
//        messageObject.put("messages", List.of(sendMessageTemplate)); // 받는 사람
//
//        // 메시지 전송 요청
//        RequestEntity<String> requestEntity = RequestEntity
//                .post(NAVER_SMS_SEND_URL, NAVER_SMS_SERVICE_KEY)
//                .header("x-ncp-apigw-timestamp", timestamp)
//                .header("x-ncp-iam-access-key", NAVER_SMS_ACCESS_KEY)
//                .header("x-ncp-apigw-signature-v2", signature)
//                .body(messageObject.toString());
//
//        ResponseEntity<String> response = restOperations.exchange(requestEntity, String.class);
//
//    }



//    private String makeSignature(String method, String url) throws Exception {
//        String space = " ";					// one space
//        String newLine = "\n";					// new line
////        String method = "GET";					// method
////        String url = "/photos/puppy.jpg?query1=&query2";	// url (include query string)
//        String timestamp = String.valueOf(Timestamp.valueOf(LocalDateTime.now()).getTime());		// current timestamp (epoch)
////        String accessKey = NAVER_SMS_ACCESS_KEY;			// access key id (from portal or Sub Account)
////        String secretKey = NAVER_SMS_SECRET_KEY;
//
//        String message = new StringBuilder()
//                .append(method)
//                .append(space)
//                .append(url)
//                .append(newLine)
//                .append(timestamp)
//                .append(newLine)
//                .append(NAVER_SMS_ACCESS_KEY)
//                .toString();
//
//        SecretKeySpec signingKey = new SecretKeySpec(NAVER_SMS_SECRET_KEY.getBytes("UTF-8"), "HmacSHA256");
//        Mac mac = Mac.getInstance("HmacSHA256");
//        mac.init(signingKey);
//
//        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
//        String encodeBase64String = Base64.encodeBase64String(rawHmac);
//
//        return encodeBase64String;
//    }



    /* ********************************비밀번호 찾기************************************************/

    public String find_user_pw(UserDTO userDTO) {
        UserDTO findUserDTO = userMapper.find_user_by_email(userDTO);
        System.out.println(findUserDTO);
        if (Objects.isNull(findUserDTO)) {
            // 유저가 없으면
            return "해당 유저가 존재하지 않음";
        }

        try {
            // 토큰과 만료 날짜를 생성한다.
            String token = UUID.randomUUID().toString();
            LocalDateTime tokenExpireDate = LocalDateTime.now().plusMinutes(3);
            //만들어진 토큰과 만료 날짜를 유저에게 설정함.
            findUserDTO.setPwReToken(token);
            findUserDTO.setPwReTokenExpire(tokenExpireDate);
            // db에 update
            userMapper.update_user_repw_token(findUserDTO);
            // 메일 전송함
            send_mail_of_user_password(findUserDTO.getEmail(), token);
        } catch (Exception e) {
            System.out.println("메일 전송 실패: " + e);
            return "메일전송이 실패했음, 관리자한테 문의";
        }

        return "메일을 발송했습니다.";
    }

    public boolean find_user_by_token(String token) {
        UserDTO userDTO = userMapper.find_user_by_token(token);
        // 조회된 유저가 없거나, token 만료일이 지났으면 false 반환
        return !Objects.isNull(userDTO) && !userDTO.getPwReTokenExpire().isBefore(LocalDateTime.now());
    }

    public void update_user_password(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userMapper.update_user_password(userDTO);
    }

    private void send_mail_of_user_password(String to, String token) throws Exception {
        System.out.println("메일 보내기 시도");
        Context ctx = new Context();
        ctx.setVariable("link", RESET_PASSWORD_URL+token);
        String htmlContent = templateEngine.process("/user/re-password-template.html", ctx);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(SEND_EMAIL_FROM); //누가 보내는지
        mimeMessageHelper.setTo(to); // 누가 받는지
        mimeMessageHelper.setSubject("[korea shop] 비밀번호 재설정"); // 제목은?
        mimeMessageHelper.setText(htmlContent, true); // 내용은? 2번째 파라미터는 html 여부

        javaMailSender.send(mimeMessage);

    }
}
