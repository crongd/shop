package com.shop.service;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PortOneService {

    private final RestOperations restOperations;

    // URL

    // token 발급 url
    private final String PORTONE_ACCESS_TOKEN_URL = "https://api.iamport.kr/users/getToken";
    // 결제정보 사전 등록 url (사전 검증)
    private final String PORTONE_PAYMENTS_PREPARE_URL = "https://api.iamport.kr/payments/prepare";
    // 결제 내역 단건조회 url
    private final String PORTONE_PAYMENTS_INQUERY_URL = "https://api.iamport.kr/payments/{imp_uid}";


    private final String PORTONE_CERTIFICATION_CI_URI = "https://api.iamport.kr/certifications/{impUID}";

    // KEY
    private final String PORTONE_API_KEY = "8843414326450430";
    private final String PORTONE_API_SECRET = "6PJKx8uc4mNqPaOtjeIZ19DKx8gxU9O3VFu3E0dIQIHijPCRDHVvFPa1YXu1mVkDgNhSnPyexo7A7xfF";



    // PORT_ONE 기능을 사용하기 위해 ACCESS_TOKEN 발급받기
    public String get_access_token(){
        Map<String, String> bodyData = Map.of(
                "imp_key", PORTONE_API_KEY,
                "imp_secret", PORTONE_API_SECRET
        );

        RequestEntity<String> requestEntity = RequestEntity
                .post(PORTONE_ACCESS_TOKEN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONObject.toJSONString(bodyData));

        ResponseEntity<JSONObject> response = restOperations.exchange(requestEntity, JSONObject.class);
        System.out.println(response);
        JSONObject responseBody = response.getBody();
        Map data = (Map) responseBody.get("response");
        return (String) data.get("access_token");
    }

    ///////////////////////////////////////////////////////////////////////////////


    // 유저 인증 후, 유저의 ci 값 받기
    public String get_user_certification(String impUID, String token) throws Exception{
        RequestEntity requestEntity = RequestEntity
                .get(PORTONE_CERTIFICATION_CI_URI, impUID)
                .header("Authorization", token)
                .build();

        ResponseEntity<JSONObject> responseEntity = restOperations.exchange(requestEntity, JSONObject.class);
        return ((Map) responseEntity.getBody().get("response")).get("unique_key").toString();
    }

    ///////////////////////////////////////////////////////////////////////////////



    // 결제 요청 전, 사전 검증 요청하기 (내가 결제할 상품의 정보를 PORT_ONE에 전달해놓기)
    // merchant_uid => 한 주문의 고유 주문번호 / amount => 총 결제금액
    public String pre_verification_order(String merchant_uid, String amount) {
        String accessToken = get_access_token();
        Map<String, String> bodyData = Map.of(
                "merchant_uid", merchant_uid,
                "amount", amount
        );

        RequestEntity<String> requestEntity = RequestEntity
                .post(PORTONE_PAYMENTS_PREPARE_URL)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONObject.toJSONString(bodyData));

        ResponseEntity<JSONObject> response = restOperations.exchange(requestEntity, JSONObject.class);
        System.out.println(response);
        JSONObject responseBody = response.getBody();
        int code = (int) responseBody.get("code");
        String message = (String) responseBody.get("message");

        // code가 0이면 성공임
        System.out.println(message);
        // 메세지 없음
        System.out.println(((Map)responseBody.get("response")).get("merchant_uid"));
        System.out.println(((Map)responseBody.get("response")).get("amount"));


        return code == 0 ? null : message;

    }




    // 결제 내역 단건 조회 (+결제 요청 후, 사후 검증)
    // imp_uid => PORT_ONE에 등록된 고유한 결제 번호
    public void get_inquery_order(String imp_uid) {
        Map<String, String> bodyData = Map.of(
                "imp_uid", imp_uid
        );

        // get 요청으로 변경해야함.
        RequestEntity<String> requestEntity = RequestEntity
                .post(PORTONE_PAYMENTS_PREPARE_URL, imp_uid)
                .header("Authorization", "Bearer " + get_access_token())
                .contentType(MediaType.APPLICATION_JSON)
                .body(JSONObject.toJSONString(bodyData));

        ResponseEntity<JSONObject> response = restOperations.exchange(requestEntity, JSONObject.class);
        System.out.println(response);
        JSONObject responseBody = response.getBody();
        int code = (int) responseBody.get("code");
        String message = (String) responseBody.get("message");
        System.out.println(code);
        // code가 0이면 성공임
        if (code == 0) {

        }

        System.out.println(message);
        // 메세지 없음
        
        Map responseData = (Map) responseBody.get("response");
        // responseData를 검증한 후, db에 저장하는 과정을 거쳐야 함.
        System.out.println(responseData);




    }


    // 유저 인증 후, 유저의 ci 값 받기


}
