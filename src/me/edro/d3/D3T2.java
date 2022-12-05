package me.edro.d3;

import java.util.*;

public class D3T2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        int num = 0;

        Map<Character, Integer> groupItems = new HashMap<>();
        char commonGroupItem = '-';

        while (scanner.hasNext()){
            String line = scanner.nextLine().trim();
            Set<Character> items = new HashSet<>();

            for (int i = 0; i < line.length(); i++){
                char item = line.charAt(i);
                items.add(item);
            }

            for (Character c: items){
                if (groupItems.containsKey(c)){
                    Integer freq = groupItems.get(c);
                    groupItems.put(c, freq + 1);

                    if (freq >= 2){
                        commonGroupItem = c;
                    }
                } else {
                    groupItems.put(c, 1);
                }
            }

            num++;
            if (num % 3 == 0){
                groupItems = new HashMap<>();
                sum += prioritize(commonGroupItem);
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
