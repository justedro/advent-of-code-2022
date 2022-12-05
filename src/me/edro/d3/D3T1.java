package me.edro.d3;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class D3T1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;

        while (scanner.hasNext()){
            String line = scanner.nextLine().trim();

            Set<Character> items = new HashSet<>();
            for (int i = 0; i < line.length(); i++){
                char item = line.charAt(i);

                if (i < line.length()/2){
                    items.add(item);
                } else {
                    if (items.contains(item)){
                        sum += prioritize(item);
                        break;
                    }
                }
            }
        }

        System.out.println(sum);
    }

    public static int prioritize(char item){
        if (item >= 'a' && item <= 'z'){
            return item - 'a' + 1;
        }
        return item - 'A' + 27;
    }
}
