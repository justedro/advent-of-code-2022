package me.edro.d11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class D11T2 {
    public static class Monkey{
        public List<Integer> items;
        public String op;
        public String operand;
        public int test;
        public int ifTrue;
        public int ifFalse;

        public int inspections = 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /*
        Monkey 0:
          Starting items: 79, 98
          Operation: new = old * 19
          Test: divisible by 23
            If true: throw to monkey 2
            If false: throw to monkey 3
         */
        List<Monkey> monkeys = new ArrayList<>();
        long someCommonMultiple = 1L;
        while (scanner.hasNext()){
            String line;
            do {
                line = scanner.nextLine();
            } while (line.isBlank());

            // new monkey
            Monkey m = new Monkey();
            m.items = Arrays.stream(scanner.nextLine().split(": ")[1].split(", ")).map(Integer::parseInt).collect(Collectors.toList());

            String op = scanner.nextLine().split(": new = old ")[1];
            String[] opParts = op.split(" ");
            m.op = opParts[0];
            m.operand = opParts[1];

            m.test = Integer.parseInt(scanner.nextLine().split(": divisible by ")[1]);
            m.ifTrue = Integer.parseInt(scanner.nextLine().split(": throw to monkey ")[1]);
            m.ifFalse = Integer.parseInt(scanner.nextLine().split(": throw to monkey ")[1]);

            monkeys.add(m);
            someCommonMultiple *= m.test;
        }

        for (int i = 0; i < 10000; i++){
            for (Monkey m: monkeys){
                for (Integer item: m.items){
                    long worry = item;
                    if (m.operand.equals("old")){
                        worry *= worry;
                    } else {
                        if (m.op.equals("+")){
                            worry += Integer.parseInt(m.operand);
                        } else {
                            // *
                            worry *= Integer.parseInt(m.operand);
                        }
                    }
                    worry %= someCommonMultiple;
                    int next = worry % m.test == 0? m.ifTrue : m.ifFalse;

                    monkeys.get(next).items.add((int)worry);
                }

                m.inspections += m.items.size();
                m.items.clear();
            }

            System.out.println("Round ended: " + (i + 1));
            for (int j = 0; j < monkeys.size(); j++){
                System.out.println("Monkey "+ j +": " + monkeys.get(j).items.stream().map(Object::toString).collect(Collectors.joining(", ")));
            }
            System.out.println();
        }

        List<Long> ins = monkeys.stream().map(a -> (long)a.inspections).sorted().collect(Collectors.toList());

        System.out.println(ins.get(ins.size()-1) * ins.get(ins.size()-2));
    }

}
