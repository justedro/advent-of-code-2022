package me.edro.d25;

import java.util.*;

public class D25T1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        var sum = 0L;

        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }

            sum += decode(line);
        }

        System.out.println(encode(sum));
    }

    private static String encode(long sum) {
        int[] digits = new int[20];
        var digit = 0;
        while (sum > 0) {
            var num = sum % 5;
            sum /= 5;

            if (num > 2) {
                digits[digit + 1]++;
                digits[digit] += num - 5;
            } else {
                digits[digit] += num;
            }

            if (digits[digit] >= 3){
                digits[digit] -= 5;
                digits[digit + 1] ++;
            }

            digit++;
        }

        StringBuilder res = new StringBuilder();
        for (int i = digit - 1; i >= 0; i--) {
            switch (digits[i]){
                case -2:
                    res.append('=');
                    break;
                case -1:
                    res.append('-');
                    break;
                default:
                    res.append(digits[i]);
                    break;
            }
        }
        return res.toString();
    }


    private static long decode(String line) {
        long pow = 1L;
        var res = 0L;
        for (int i = line.length() - 1; i >= 0; i--) {
            switch (line.charAt(i)) {
                case '=':
                    res += -2L * pow;
                    break;
                case '-':
                    res += -1L * pow;
                    break;
                case '0':
                    res += 0L * pow;
                    break;
                case '1':
                    res += 1L * pow;
                    break;
                case '2':
                    res += 2L * pow;
                    break;
            }
            pow *= 5L;
        }
        return res;
    }

}
