package me.edro.d1;

import java.util.*;

public class D1T2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int sum = 0;
        List<Integer> elves = new ArrayList<>();
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if (!line.isBlank()){
                sum += Integer.parseInt(line);
            } else {
                if (sum > 0) {
                    elves.add(sum);
                }
                sum = 0;
            }
        }
        if (sum > 0) {
            elves.add(sum);
        }

        Collections.sort(elves);
        List<Integer> top3 = elves.subList(elves.size() - 3, elves.size());
        System.out.println(top3.stream().mapToInt(a->a).sum());
    }
}
