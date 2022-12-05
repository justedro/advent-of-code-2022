package me.edro.d2;

import java.util.Scanner;

public class D2T2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;

        while (scanner.hasNext()){
            String line = scanner.nextLine().trim();

            String[] s = line.split(" ");

            String m = choose(s[0], s[1]);

            int sc = score(s[0], m);
            int w = weight(m);

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

    private static String choose(String a, String b){
        // Rock X Paper Y Scissors Z
        // X means you need to lose,
        // Y means you need to end the round in a draw, and
        // Z means you need to win.
        String [][] map = new String[][]{
                new String[]{"Z", "X", "Y"},
                new String[]{"X", "Y", "Z"},
                new String[]{"Y", "Z", "X"},
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
