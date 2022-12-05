package me.edro.d1;

import java.util.Scanner;

public class D1T1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int maxSum = 0;
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if (!line.isBlank()){
                sum += Integer.parseInt(line);
            } else {
                maxSum = Math.max(maxSum, sum);
                sum = 0;
            }
        }
        maxSum = Math.max(maxSum, sum);

        System.out.println(maxSum);
    }
}
