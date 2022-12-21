package me.edro.d21;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class D21T2 {

    public static abstract class Job {
        long calculate() throws Exception {
            throw new Exception("not impl");
        };

        Job l;
        Job r;

        String name;

        void validate() throws Exception {
            if (name.equals("humn") || name.equals("root")){
                throw new Exception("not allowed");
            }
        }
    }

    public static class Constant extends Job {
        private final long value;

        Constant(long value, String name) {
            this.value = value;
            this.name = name;
        }

        @Override
        public long calculate() throws Exception {
            validate();
            return value;
        }
    }

    public static class Plus extends Job {
        Plus(Job l, Job r, String name) {
            this.l = l;
            this.r = r;
            this.name = name;
        }

        @Override
        public long calculate() throws Exception {
            validate();
            return l.calculate() + r.calculate();
        }
    }

    public static class Minus extends Job {
        Minus(Job l, Job r, String name) {
            this.l = l;
            this.r = r;
            this.name = name;
        }

        @Override
        public long calculate() throws Exception {
            validate();
            return l.calculate() - r.calculate();
        }
    }

    public static class Divide extends Job {
        Divide(Job l, Job r, String name) {
            this.l = l;
            this.r = r;
            this.name = name;
        }

        @Override
        public long calculate() throws Exception {
            validate();
            return l.calculate() / r.calculate();
        }
    }

    public static class Multiply extends Job {
        Multiply(Job l, Job r, String name) {
            this.l = l;
            this.r = r;
            this.name = name;
        }

        @Override
        public long calculate() throws Exception {
            validate();
            return l.calculate() * r.calculate();
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        Map<String, String> script = new HashMap<>();
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (!line.isBlank()) {
                String[] parts = line.split(": ");

                script.put(parts[0], parts[1]);
            }
        }

        Map<String, Job> parsed = new HashMap<>();
        parse("root", script, parsed);

        Job root = parsed.get("root");
        simplify(root);

        Job iter;
        long ans;
        if (root.l instanceof Constant){
            iter = root.r;
            ans = root.l.calculate();
        } else {
            iter = root.l;
            ans = root.r.calculate();
        }

        while (!iter.name.equals("humn")){
            long c;
            Job t;
            boolean rev;
            if (iter.l instanceof Constant && !iter.l.name.equals("humn")){
                c = iter.l.calculate();
                t = iter.r;
                rev = false;
            } else {
                c = iter.r.calculate();
                t = iter.l;
                rev = true;
            }

            if (iter instanceof Divide){
                if (!rev){
                    ans = ans / c;
                } else {
                    ans = c * ans;
                }
            }

            if (iter instanceof Multiply){
                if (!rev){
                    ans = ans / c;
                } else {
                    ans = ans / c;
                }
            }

            if (iter instanceof Minus){
                if (!rev){
                    ans = c - ans;
                } else {
                    ans = ans + c;
                }
            }

            if (iter instanceof Plus){
                if (!rev){
                    ans = ans - c;
                } else {
                    ans = ans - c;
                }
            }

            iter = t;
        }

        System.out.println(ans);
    }

    private static Job simplify(Job j) {
        if (j instanceof Constant){
            return j;
        }

        try {
            return new Constant(j.calculate(), j.name);
        } catch (Exception ignored){
            j.l = simplify(j.l);
            j.r = simplify(j.r);
        }

        return j;
    }


    private static Job parse(String node, Map<String, String> script, Map<String, Job> parsed) {
        String[] op = script.get(node).split(" ");
        Job j;
        if (op.length == 1) {
            j = new Constant(Long.parseLong(op[0]), node);
        } else {
            switch (op[1]) {
                case "*":
                    j = new Multiply(parse(op[0], script, parsed), parse(op[2], script, parsed), node);
                    break;
                case "/":
                    j = new Divide(parse(op[0], script, parsed), parse(op[2], script, parsed), node);
                    break;
                case "+":
                    j = new Plus(parse(op[0], script, parsed), parse(op[2], script, parsed), node);
                    break;
                case "-":
                default:
                    j = new Minus(parse(op[0], script, parsed), parse(op[2], script, parsed), node);
                    break;
            }
        }

        parsed.put(node, j);

        return j;
    }


}
