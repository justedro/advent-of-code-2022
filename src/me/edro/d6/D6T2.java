package me.edro.d6;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class D6T2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int pos = 0;

        while (scanner.hasNext()){
            String line = scanner.nextLine();

            for (int i = 14; i < line.length(); i++){
                Set<Character> c = new HashSet<>();
                for (int j = 0; j < 14; j++){
                    c.add(line.charAt(i - j - 1));
                }

                if (c.size() >= 14){
                    System.out.println(i);
                    break;
                }
            }
        }


    }


}
