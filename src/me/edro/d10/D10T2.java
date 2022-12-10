package me.edro.d10;

import java.util.Scanner;

import static java.lang.Math.abs;

public class D10T2 {
    static int cycle = 0;

    static int x = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            if (parts[0].equals("noop")){
                tick();
            } else {
                tick();
                tick();
                x += Integer.parseInt(parts[1]);
            }

            //20th, 60th, 100th, 140th, 180th, and 220th cycles
        }


    }

    static void tick(){
        if (cycle % 40 == 0){
            System.out.println("");
        }

        if (cycle <= 240){
            int col = cycle % 40;

            if (abs(col - x) <= 1){
                System.out.print("X");
            } else {
                System.out.print(".");
            }
        }

        cycle++;
    }

}
