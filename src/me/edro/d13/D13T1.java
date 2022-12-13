package me.edro.d13;

import org.json.JSONArray;

import java.util.List;
import java.util.Scanner;

public class D13T1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int sum = 0;
        int index = 1;
        while (scanner.hasNext()) {
            JSONArray o1 = new JSONArray(scanner.nextLine());
            JSONArray o2 = new JSONArray(scanner.nextLine());

            if (compareLists(o1, o2) >= 1) {
                sum += index;
                System.out.println("OK");
            } else {
                System.out.println("NOT OK");
            }

            if (scanner.hasNext()) {
                scanner.nextLine();
            }

            index++;
        }


        System.out.println(sum);
    }

    private static int compareLists(JSONArray o1, JSONArray o2) {
        int pos = 0;
        while (true) {
            if (o1.length() <= pos && o2.length() <= pos) {
                return 0;
            }
            if (o1.length() <= pos) {
                return 1;
            }
            if (o2.length() <= pos) {
                return -1;
            }

            Object a = o1.get(pos);
            Object b = o2.get(pos);

            if (a instanceof JSONArray || b instanceof JSONArray) {
                if (!(a instanceof JSONArray)) {
                    a = new JSONArray(List.of(a));
                }
                if (!(b instanceof JSONArray)) {
                    b = new JSONArray(List.of(b));
                }

                int res = compareLists((JSONArray) a, (JSONArray) b);
                if (res != 0) {
                    return res;
                }
            } else {
                if ((Integer) a < (Integer) b) {
                    return 1;
                }
                if ((Integer) a > (Integer) b) {
                    return -1;
                }
            }

            pos++;
        }
    }

}
