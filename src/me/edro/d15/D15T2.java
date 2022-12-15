package me.edro.d15;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class D15T2 {

    private static class Sensor {
        public int x;
        public int y;
        public int radius;

        @Override
        public String toString() {
            return "Sensor{" +
                    "x=" + x +
                    ", y=" + y +
                    ", radius=" + radius +
                    '}';
        }
    }

    public static class Coords {
        public int x;
        public int y;

        public Coords(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coords coords = (Coords) o;
            return x == coords.x && y == coords.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Coords{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        public boolean isValid() {
            return x >= 0 && x <= 4000000 && y >= 0 && y <= 4000000;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /// Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        String regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)";
        Pattern pattern = Pattern.compile(regex);

        ArrayList<Sensor> sensors = new ArrayList<>();

        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                var sx = Integer.parseInt(matcher.group(1));
                var sy = Integer.parseInt(matcher.group(2));
                var bx = Integer.parseInt(matcher.group(3));
                var by = Integer.parseInt(matcher.group(4));

                var radius = abs(sx-bx) + abs(sy-by) + 1;

                var s = new Sensor();
                s.x = sx;
                s.y = sy;
                s.radius = radius;

                sensors.add(s);
            }
        }

        System.out.println();

        for (int i = 0; i < sensors.size(); i++){
            var s = sensors.get(i);
            System.out.println("Checking sensor border: " + s);
            Set<Coords> border = new HashSet<>();
            for(int a = 0; a < s.radius + 1; a++){
                Coords p1 = new Coords(s.x - a, s.y - s.radius + a);
                Coords p2 = new Coords(s.x + a, s.y - s.radius + a);
                Coords p3 = new Coords(s.x - a, s.y + s.radius - a);
                Coords p4 = new Coords(s.x + a, s.y + s.radius - a);
                if (p1.isValid()) border.add(p1);
                if (p2.isValid()) border.add(p2);
                if (p3.isValid()) border.add(p3);
                if (p4.isValid()) border.add(p4);
            }

            System.out.println("Border consists of " + border.size() + " elements");

            for (int j = 0; j < sensors.size(); j++){
                if (i == j) {
                    continue;
                }

                var sr = sensors.get(j);
                Set<Coords> remove = new HashSet<>();
                for (Coords c: border){
                    var dist = abs(c.x-sr.x) + abs(c.y-sr.y) + 1;
                    if (dist <= sr.radius){
                        remove.add(c);
                    }
                }
                border.removeAll(remove);
            }

            if (border.size() == 1){
                Coords coords = border.stream().findFirst().get();
                System.out.println("Sol: " + coords);
                System.out.println("Sol: " + (coords.x * 4000000L + coords.y));
                break;
            } else {
                System.out.println("Wrong combination, still available: " + border.size());
            }
        }

        System.out.println();
    }
}
