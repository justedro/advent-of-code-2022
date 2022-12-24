package me.edro.d22;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D22T1 {

    public static class Coords {
        public int x;
        public int y;

        public Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coords copy() {
            return new Coords(x, y);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> map = new ArrayList<>();
        boolean first = true;
        Coords coords = new Coords(0, 0);
        int dir = 0;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }
            if (first) {
                first = false;
                coords.x = line.indexOf('.');
            }

            map.add(line);
        }

        String cmd = scanner.nextLine();
        int pos = 0;
        while (pos < cmd.length()) {

            int r = cmd.indexOf('R', pos);
            int l = cmd.indexOf('L', pos);
            int nextTurn = r;
            if (l < r && l >= 0 || r < 0){
                nextTurn = l;
            }

            if (nextTurn < 0) {
                nextTurn = cmd.length();
            }
            if (nextTurn > pos) {
                int steps = Integer.parseInt(cmd.substring(pos, nextTurn));
                while (steps > 0) {
                    if (!stepAvail(map, coords, dir)) {
                        break;
                    }

                    coords = advance(map, coords, dir);
                    steps--;
                }

                pos = nextTurn;
            } else {
                char turn = cmd.charAt(pos);

                if (turn == 'R') {
                    dir++;
                }
                if (turn == 'L') {
                    dir--;
                }
                if (dir < 0) {
                    dir += 4;
                }
                if (dir >= 4) {
                    dir -= 4;
                }

                pos++;
            }
        }

        coords.x ++;
        coords.y ++;
        System.out.println(coords.y * 1000 + coords.x * 4 + dir);
    }

    private static Coords advance(List<String> map, Coords coords, int dir) {
        Coords next = coords.copy();

        if (dir == 0) {
            next.x++;
            char t = tile(map, next);
            if (t == ' ') {
                next.x = 0;
                while (tile(map, next) == ' ') {
                    next.x++;
                }
            }
        }
        if (dir == 1) {
            next.y++;
            char t = tile(map, next);
            if (t == ' ') {
                next.y = 0;
                while (tile(map, next) == ' ') {
                    next.y++;
                }
            }
        }
        if (dir == 2) {
            next.x--;
            char t = tile(map, next);
            if (t == ' ') {
                next.x = map.get(next.y).length() - 1;
                while (tile(map, next) == ' ') {
                    next.x--;
                }
            }
        }
        if (dir == 3) {
            next.y--;
            char t = tile(map, next);
            if (t == ' ') {
                next.y = map.size() - 1;
                while (tile(map, next) == ' ') {
                    next.y--;
                }
            }
        }

        return next;
    }

    private static char tile(List<String> map, Coords c) {
        if (c.y < 0 || c.y >= map.size()) {
            return ' ';
        }
        String row = map.get(c.y);
        if (c.x < 0 || c.x >= row.length()) {
            return ' ';
        }
        return row.charAt(c.x);
    }

    private static boolean stepAvail(List<String> map, Coords coords, int dir) {
        Coords next = advance(map, coords, dir);
        char t = tile(map, next);
        return t == '.';
    }


}
