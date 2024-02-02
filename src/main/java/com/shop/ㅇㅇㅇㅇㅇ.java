package com.shop;

import org.springframework.security.core.parameters.P;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ㅇㅇㅇㅇㅇ {
//    static String a = "";
    // 팰린드롬
    public static int sol(String s) {

        String result = "";

//        if (s.length() == 2){
//            char a = s.charAt(0);
//            char b = s.charAt(1);
//            return a == b ? 2 : 1;
//        }
        int sLength = s.length();

        for (int i = 0; i < sLength; i++) {
            for (int j = sLength; j > i; j--) {
//                System.out.println("val:" + val);
//                System.out.println(i + " " + j);

                if ((j-i) <= 1 || (j-i) <= result.length()) {
                    System.out.println("val가 이거라 continue: " );
                    continue;
                }

//                String val = s.substring(i, j);
//                char[] cha = Arrays.copyOfRange(s.toCharArray(), i, j);
//                char[] cha = Arrays.copyOfRange(val, i, j);
//                System.out.println("val: " + Array);
                // i 시작인덱스 j 끝 인덱스 차이를
                if (check(s, i, j)) {
                    int chars = Arrays.copyOfRange(s.toCharArray(), i, j).length;

                }
                System.out.println("result: " + result);
            }
        }

        System.out.println("result: " + result);

        return result.length();

    }

    static boolean check(String s, int i, int j) {
        char[] chars = Arrays.copyOfRange(s.toCharArray(), i, j);


        for (int k = 0; k < chars.length/2; k++) {
            char first = chars[k];
            char last = chars[chars.length -1 - k];
            if (first != last) {
                return false;
            }
        }
        return true;
    }


    /////////////////////////////////////////////////////////////////////////////////////////


    int expand(String s, int left, int right) {
        int len = s.length();
        while (left >= 0 && right <= len && s.charAt(left) == s.charAt(right-1)) {
            left -= 1; // 투 포인터를 왼쪽으로 한칸 이동
            right += 1; // 투 포인터를 오른쪽으로 한칸 이동
        }

        return (right-1) - (left +1) +1; // s.substring((left+1), (right-1)).length()
    }

    int longestPanlindrome(String s) {
        int result =1;
        for (int i = 0; i < s.length(); i++) {
            int ex1 = expand(s, i, i+1);
            int ex2 = expand(s, i, i+2);

            result = Math.max(Math.max(ex1, ex2), result);
        }

        return result;
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //최대 공약수 재귀로 구하기

    // a * b == 최약 * 최배 같다.
    static int abcd(int a, int b) {
        return a % b == 0 ? b : abcd(b, a%b);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 피보나치 수열
    static int abc(int i1, int i2, int n) {
        return n == 0 ? i1 + i2 : abc(i2, i1 + i2, n-1);
    }

    // 다이나믹
    static int dynamic(int n) {
        int[] pibonacci = new int[100000];
        pibonacci[0] = 1;
        pibonacci[1] = 1;
        for (int i = 2; i < n; i++) {
            pibonacci[i] = pibonacci[i - 1] + pibonacci[i - 2] % 1234567;
        }
        System.out.println(pibonacci[n-1]);
        return pibonacci[n-1] % 1234567;
    }

    // 하노이 자식의 탑
    static int count = 1;

    static int hanoi(int n) {
        // 규칙만 알긴한데...
        if (n != 1) {
            count = (count * 2) + 1;
            return hanoi(n-1);
        } else {
            return count;
        }
    }
    
    static void move_tower(int n, char A, char B, char C) {
        if (n == 1) {
            System.out.printf("원판 %d을 %c에서 %c로 이동시킴\n", n, A, C);
        } else {
            // 1. 맨 아래 원판을 제외한 n-1개의 원판을 from에서 by로 이동시킴
            move_tower(n-1, A, B, C);
            // 2. 남은 하나의 원판을 from에서 to로 이동시켰다! a -> c
            System.out.printf("원판 %d을 %c에서 %c로 이동시킴\n", n, A, C);
            // 3. 1번에서 잠깐 옮겨놨던 n-1개의 원판을 큰 원판 위로 이동 b -> c
            move_tower(n-1, B, A, C);
        }
    }


    public static void main(String[] args) {
//        System.out.println(sol("abcabcdcba"));
//        System.out.println(hanoi(10));

        move_tower(3, 'A', 'B', 'C');

//        System.out.println(abc(1, 1, 27));

//        System.out.println(abcd(3, 12));
//        if (val.length() % 2 == 0) { //짝수면
//
//        } else { //홀수면
//
//        }

//        String a = "abcdedcbsaa";
//
//        for (int i = 0; i < a.length() / 2; i++) {
//            char q = a.charAt(i);
//            char w = a.charAt(a.length()-1 -i);
//            if (q != w) {
//                System.out.println(q +"와"+ w + "같지 않음");
//                break;
//            }
//            System.out.println(q +"와"+ w + "같음");
//        }

//        System.out.println(a.substring(4, 4));
//        a.substring()
//        System.out.println(a.length()/2);
    }
}
