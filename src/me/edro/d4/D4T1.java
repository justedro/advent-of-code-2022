package me.edro.d4;

import java.util.Scanner;

public class D4T1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;

        while (scanner.hasNext()){
            String[] line = scanner.nextLine().trim().split(",");
            String[] e1 = line[0].split("-");
            String[] e2 = line[1].split("-");

            int e1l = Integer.parseInt(e1[0]);
            int e1r = Integer.parseInt(e1[1]);
            int e2l = Integer.parseInt(e2[0]);
            int e2r = Integer.parseInt(e2[1]);

            if (
                e1l <= e2l && e1r >= e2r
                || e1l >= e2l && e1r <= e2r
            ) {
                sum ++;
                System.out.println(line[0]);
                System.out.println(line[1]);
                System.out.println("ok");
            }
        }

        System.out.println(sum);
    }


}
