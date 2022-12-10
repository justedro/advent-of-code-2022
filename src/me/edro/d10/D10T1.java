package me.edro.d10;

import java.util.Scanner;

public class D10T1 {
    static int cycle = 0;
    static int sum = 0;
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

        System.out.println(sum);
    }

    static void tick(){
        cycle++;
        if (cycle <= 220 && (cycle - 20) % 40 == 0){
            sum += cycle * x;
        }
    }

}
