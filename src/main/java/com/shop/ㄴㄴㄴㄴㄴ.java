package com.shop;


import java.util.Arrays;

class MLL {
    Object data;
    MLL next;
    public MLL(Object data, MLL next) {
        this.data = data;
        this.next = next;
    }
}


/*
* 정수형 숫자들이 들어있는 배열에서,
* 합계로 '0'을 만들 수 있는 3개의 요소를 출력하세요.
*/
public class ㄴㄴㄴㄴㄴ {
    static int pro(int[] n) {
        int count = 0;
        for (int i = 0; i < n.length; i++) {
            for (int j = i + 1; j < n.length; j++) {
                for (int k = j + 1; k < n.length; k++) {
                    if (n[i] + n[j] + n[k] == 0){
                        count++;
                    }
                }
            }
        }

        return count;
    }

    static boolean check(int a, int b, int c) {
        return (a + b + c) == 0;
    }


    // LinkedList 구조
    public static void main(String[] args) {
        MLL mll1 = new MLL("a", null);
        MLL mll2 = new MLL("b", null);
        MLL mll3 = new MLL("c", null);
        mll1.next = mll2;
        mll2.next = mll3;
//        System.out.println(mll1.data);
//        System.out.println(mll1.next.data);
//        System.out.println(mll2.next.data);

        System.out.println(pro(new int[]{-2, 3, 0, 2, -5}));

    }

}
