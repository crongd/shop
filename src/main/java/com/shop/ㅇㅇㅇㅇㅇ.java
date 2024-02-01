package com.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ㅇㅇㅇㅇㅇ {
    public static int[] sol(String s) {
//        if (arr.length == 1) {
//            return new int[]{-1};
//        }
//        int[] result = new int[arr.length-1];
//        Integer[] arr2 = new Integer[arr.length];
//        for (int i = 0; i < arr.length; i++) {
//            arr2[i] = arr[i];
//        }
//
//        Arrays.sort(arr2, Collections.reverseOrder());
//
//        for (int i = 0; i < arr.length-1; i++) {
//            result[i] = arr[i];
//        }
//
//        return result;

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j < s.length() + 1; j++) {
                System.out.println(s.substring(i, j));
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println("abcdedcba");
    }
}
