package com.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ㅇㅇㅇㅇㅇ {
    public static int[] sol(int[] arr) {
        if (arr.length == 1) {
            return new int[]{-1};
        }
        int[] result = new int[arr.length-1];
        Integer[] arr2 = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            arr2[i] = arr[i];
        }

        Arrays.sort(arr2, Collections.reverseOrder());

        for (int i = 0; i < arr.length-1; i++) {
            result[i] = arr[i];
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(sol(new int[]{4})));
    }
}
