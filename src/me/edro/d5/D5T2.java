package me.edro.d5;

import java.util.*;

public class D5T2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean drawing = true;
        int stacks = -1;
        List<Stack<Character>> crates = new ArrayList<>();

        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if (line.isBlank()) {
                drawing = false;

                for (int i = 0; i < stacks; i++){
                    Collections.reverse(crates.get(i));
                }
                continue;
            }

            if (drawing) {
                if (stacks < 0){
                    stacks = (line.length() + 1) / 4;
                    System.out.println("Stacks: " + stacks);

                    for (int i = 0; i < stacks; i++){
                        crates.add(new Stack<>());
                    }
                }

                for (int i = 0; i < stacks; i++){
                    char crate = line.charAt(i*4 + 1);
                    if (crate >= 'A' && crate <= 'Z'){
                        crates.get(i).add(crate);
                    }
                }

            } else {
                // move 1 from 2 to 1
                String[] parts = line.split(" ");
                int cnt = Integer.parseInt(parts[1]);
                int from = Integer.parseInt(parts[3])-1;
                int to = Integer.parseInt(parts[5])-1;

                Stack<Character> grabber = new Stack<>();
                while(cnt > 0){
                    Character crate = crates.get(from).pop();
                    grabber.push(crate);
                    cnt--;
                }
                Collections.reverse(grabber);
                crates.get(to).addAll(grabber);
            }
        }

        for (int i = 0; i < stacks; i++){
            System.out.print(crates.get(i).peek());
        }
        System.out.println("");
    }


}
