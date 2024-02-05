package com.shop;

import org.springframework.security.core.parameters.P;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
        int[] pibonacci = new int[n+1];
        pibonacci[0] = 1;
        pibonacci[1] = 1;
        for (int i = 2; i < n; i++) {
            pibonacci[i] = (pibonacci[i - 1] + pibonacci[i - 2]) % 1234567;
        }
        System.out.println(pibonacci[n-1]);
        return pibonacci[n-1];
    }

    // 하노이 자식의 탑
    static int count = 1;

    // 최소 횟수만 구할 때
    static int hanoi(int n) {
        // 규칙만 알긴한데...
        if (n != 1) {
            count = (count * 2) + 1;
            return hanoi(n-1);
        } else {
            return count;
        }
    }


    static List<int[]> list = new ArrayList<>();
    // 쌤의 하노이의 탑
    static int[][] hanoi_result(int n) {

        move_tower(n, 1, 2, 3);

        int[][] result = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            int[] value = list.get(i);
            result[i][0] = value[0];
            result[i][1] = value[1];
        }

        return result;
    }

    static void move_tower(int n, int from, int by, int to) {
        int[] move = {from, to};

        if (n == 1) {
//            System.out.printf("if 원판 %d을 %c에서 %c로 이동시킴\n", n, A, C);
            list.add(move);
        } else {
            // 1. 맨 아래 원판을 제외한 n-1개의 원판을 from에서 by로 이동시킴
            move_tower(n-1, from, to, by);
            list.add(move);
            // 2. 남은 하나의 원판을 from에서 to로 이동시켰다! a -> c
//            System.out.printf("else 원판 %d을 %c에서 %c로 이동시킴\n", n, A, C);
            // 3. 1번에서 잠깐 옮겨놨던 n-1개의 원판을 큰 원판 위로 이동 b -> c
            move_tower(n-1, by, from, to);
        }
    }



    //////////////////////////////

    //내적
    static int sowjr(int[] a, int[] b) {
        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] * b[i];
        }
        return (int) Arrays.stream(result).asLongStream().sum();
    }

    // 행렬의 덧셈
    static int[][] sum(int[][] arr1, int[][] arr2) {
        int[][] result = new int[arr1.length][arr1[0].length];
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[i].length; j++) {
                result[i][j] = arr1[i][j] + arr2[i][j];
            }
        }
        return result;
    }

    // 같은 숫자는 싫어 (스택/큐)
    static int[] stack(int[] arr) {
//        return Arrays.stream(arr).distinct().toArray();

//        Set<Integer> integerSet = new HashSet<>();
//
//        for (int i = 0; i < arr.length; i++) {
//            integerSet.add(arr[i]);
//        }
//
//        int[] result = new int[integerSet.size()];
//        int index = 0;
//        for (int i: integerSet) {
//            result[index++] = i;
//        }
//        return result;


        return IntStream.range(0, arr.length).filter(i -> i == 0 || arr[i+1] != arr[i]).map(i -> arr[i]).toArray();
    }

    // 3진법 뒤집기
    static int jin3(int n) {
        String str = "";
        while (n != 0) {
            str += n % 3;
            System.out.println(str);
            n /= 3;
        }
        return Integer.parseInt(str, 3);
    }

    // 크기가 작은 부분 문자열
    static int subString(String t, String p) {
        int count = 0;
        for (int i = 0; i < t.length() - p.length() + 1; i++) {
            Long a = Long.parseLong(t.substring(i, i+p.length()));
            System.out.println(a);
            if (a <= Long.parseLong(p)){
                count++;
            }
        }
        return count;
    }

    //최소 직사각형
    static int min(int[][] sizes) {
        int xMin = sizes[0][0];
        int xMax = 0;

        int yMin = sizes[0][1];
        int yMax = 0;
        for (int i = 0; i < sizes.length; i++) {
            if (sizes[i][0] < sizes[i][1]) {
                int tmp = sizes[i][1];
                sizes[i][1] = sizes[i][0];
                sizes[i][0] = tmp;
            }

            if (sizes[i][0] < xMin) {
                xMin = sizes[i][0];
            } else if (sizes[i][0] > xMax) {
                xMax = sizes[i][0];
            }

            if (sizes[i][1] < yMin) {
                yMin = sizes[i][1];
            } else if (sizes[i][1] > yMax) {
                yMax = sizes[i][1];
            }
        }
        return xMax * yMax;
    }

    // 시저 암호
    static String pass(String s, int n) {
        char[] arr = s.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ' ') {
                arr[i] = ' ';
            } else {
                if (arr[i] <= 90) {
                    arr[i] += n;
                    if (arr[i] > 90) {
                        arr[i] -= 26;
                    }
                } else {
                    arr[i] += n;
                    if (arr[i] > 122) {
                        arr[i] -= 26;
                    }
                }
            }
        }
        return String.valueOf(arr);
    }




    public static void main(String[] args) {
//        System.out.println(sol("abcabcdcba"));
//        System.out.println(hanoi(10));

//        move_tower(3, 'A', 'B', 'C');

        // 하노이
//        System.out.println(Arrays.deepToString(hanoi_result(2)));
//        int[][] param1 = new int[2][2];
        int[][] a = {{1, 2}, {2, 3}};
        int[][] b = {{3, 4},{5, 6}};


        // 같은 숫자는 싫어 (스택/큐)
//        System.out.println(Arrays.deepToString(sum(a, b)));

        int[] param1 = new int[]{1,1,3,3,0,1,1};
//        System.out.println(Arrays.toString(stack(param1)));

        // 3진법 뒤집기
//        System.out.println(jin3(45));

        // 크기가 작은 부분 문자열
//        System.out.println(subString("10203", "15"));

        // 최소 직사각형
        int[][] param2 = {{60, 50},{30, 70},{60, 30},{80, 40}};
//        System.out.println(min(param2));

        System.out.println(pass("a B z",4));



//        int a = 1*(-3) + 2*(-1) + 3*0 + 4*2;
//        System.out.println(a);

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
