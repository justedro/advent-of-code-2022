package me.edro.d21;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class D21T1 {

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

        System.out.println(calculate("root", script));
    }

    private static long calculate(String node, Map<String, String> script) {
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
