package com.shop;

import org.springframework.security.core.parameters.P;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ㅇㅇㅇㅇㅇ {
//    static String a = "";
    // 팰린드롬
    public static String sol(String s) {

        String result = "";

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length() + 1; j++) {
//                System.out.println(s.substring(i, j));
                String val = s.substring(i, j);
                System.out.println("val:" + val);
                System.out.println(i + " " + j);
                if (val.length() <= 1) {
                    System.out.println("val가 이거라 continue: " + val);
                    continue;
                }
                for (int k = 0; k < val.length()/2; k++) {
                    // val
                    char first = val.charAt(k);
                    char last = val.charAt((val.length()-1) -k);
                    System.out.println("first: " + first);
                    System.out.println("last: " + last);
                    if (first != last) {
                        System.out.println("같지 않아서 break");
                        continue;
                    }
                    if (val.length() > result.length()) {
                        result = val;
                    }
                }
                System.out.println("result: " + result);
            }
        }

        System.out.println("result: " + result);

        return result;

    }

    static boolean check(String val) {
        for (int i = 0; i < val.length()/2; i++) {
            char first = val.charAt(i);
            char last = val.charAt(val.length() -1 - i);
            if (first != last) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(sol("abcbabc"));
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
