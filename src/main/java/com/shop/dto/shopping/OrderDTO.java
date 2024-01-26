package com.shop.dto.shopping;

import com.shop.dto.user.UserDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String merchantUid; // 결제건의 가맹점 주문 번호
    private UserDTO user; // 결제건 주문자 정보
    private List<ShoppingCartDTO> shoppingCarts;
    private LocalDateTime createdDate;
    
    private int amount;         // 결제건의 결제금액?
    private int currency;        // 결제통화 구분 코드
    private String postCode;    // 주문자 우편번호
    private int startedAt;      // 결제 요청 시각
    private String addr;        // 주문자 주소
    private int cardType;       // 카드 구분코드 (0: 신용카드, 1: 체크카드, null: 카드 정보 제공하지 않음)
    private String cardNumber;  // 카드번호
    private int cardQuota;      // 결제 할부 개월 수 (일시불은 0) - 신용카드인 경우
    private String cardName;    // 결제건의 카드사명
    private String impUid;      // 포트원 거래 고유 번호
    private String payMethod;   // 결제건의 결제 수단을 구분하는 코드
    private String pgProvider;  // kakaopay와 같은 결제 수단
    private String pgId;        // TC0ONETIME와 같은 결제건의 PG사 상점 아이디
    private String pgTid;       // 결제건의 PG사 거래번호
    
}
