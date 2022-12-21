package me.edro.d21;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class D21T2 {

    public interface Job {
        long calculate();
    }

    public static class Constant implements Job {
        private long value;

        Constant(long value){
            this.value = value;
        }

        @Override
        public long calculate() {
            return value;
        }
    }

    public static class Plus implements Job {
        Job l;
        Job r;
        Plus(Job l, Job r){
            this.l = l;
            this.r = r;
        }

        @Override
        public long calculate(){
            return l.calculate() + r.calculate();
        }
    }

    public static class Minus implements Job {
        Job l;
        Job r;
        Minus(Job l, Job r){
            this.l = l;
            this.r = r;
        }

        @Override
        public long calculate(){
            return l.calculate() - r.calculate();
        }
    }

    public static class Divide implements Job {
        Job l;
        Job r;
        Divide(Job l, Job r){
            this.l = l;
            this.r = r;
        }

        @Override
        public long calculate(){
            return l.calculate() / r.calculate();
        }
    }

    public static class Multiply implements Job {
        Job l;
        Job r;
        Multiply(Job l, Job r){
            this.l = l;
            this.r = r;
        }

        @Override
        public long calculate(){
            return l.calculate() * r.calculate();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, String> script = new HashMap<>();
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (!line.isBlank()) {
                String[] parts = line.split(": ");

                script.put(parts[0], parts[1]);
            }
        }

        parse("root", script);

        System.out.println();
    }

    private static long parse(String node, Map<String, String> script, Map<String, Job> parsed) {
        String[] op = script.get(node).split(" ");
        if (op.length == 1){
            return Long.parseLong(op[0]);
        }

        switch (op[1]){
            case "*":
                return calculate(op[0], script) * calculate(op[2], script);
            case "/":
                return calculate(op[0], script) / calculate(op[2], script);
            case "+":
                return calculate(op[0], script) + calculate(op[2], script);
            case "-":
            default:
                return calculate(op[0], script) - calculate(op[2], script);
        }
    }
}
