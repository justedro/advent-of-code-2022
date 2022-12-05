package me.edro.d2;

import java.util.Scanner;

public class D2T1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;

        while (scanner.hasNext()){
            String line = scanner.nextLine().trim();

            String[] s = line.split(" ");


            int sc = score(s[0], s[1]);
            int w = weight(s[1]);

            sum += sc + w;
            System.out.println(sc + " " + w);
        }

        System.out.println(sum);
    }

    private static int score(String a, String b){
        // Rock Paper Scissors
        int [][] map = new int[][]{
                new int[]{3, 6, 0},
                new int[]{0, 3, 6},
                new int[]{6, 0, 3},
        };

        return map[weight(a)-1][weight(b)-1];
    }

    private static int weight(String a){
        if (a.equals("A") || a.equals("X")){
            return 1;
        } else if (a.equals("B") || a.equals("Y")){
            return 2;
        }
        return 3;
    }
}
