package me.edro.d22;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class D22T2 {

    public static class Coords {
        public int x;
        public int y;
        public int dir;

        public Coords(int x, int y, int dir) {
            this.x = x;
            this.y = y;

            if (dir < 0) {
                dir += 4;
            }
            if (dir >= 4) {
                dir -= 4;
            }
            this.dir = dir;
        }

        public Coords copy() {
            return new Coords(x, y, dir);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coords coords = (Coords) o;
            return x == coords.x && y == coords.y && dir == coords.dir;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, dir);
        }

        @Override
        public String toString() {
            return "Coords{" +
                    "x=" + x +
                    ", y=" + y +
                    ", dir=" + dir +
                    '}';
        }
    }


    public static void main(String[] args) throws Exception {

        //ass(new Coords(3, 1, 2).equals(rotL(new Coords(2, 3, 3))));

        Scanner scanner = new Scanner(System.in);

        List<String> map = new ArrayList<>();
        boolean first = true;
        Coords coords = new Coords(0, 0, 0);

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
        List<String> trace = new ArrayList<>(map);

        String cmd = scanner.nextLine();
        int pos = 0;
        while (pos < cmd.length()) {

            int r = cmd.indexOf('R', pos);
            int l = cmd.indexOf('L', pos);
            int nextTurn = r;
            if (l < r && l >= 0 || r < 0) {
                nextTurn = l;
            }

            if (nextTurn < 0) {
                nextTurn = cmd.length();
            }
            if (nextTurn > pos) {
                int steps = Integer.parseInt(cmd.substring(pos, nextTurn));
                while (steps > 0) {
                    if (!stepAvail(map, coords)) {
                        break;
                    }

                    StringBuilder row = new StringBuilder(trace.get(coords.y));
                    row.setCharAt(coords.x, ">v<^".charAt(coords.dir));
                    trace.set(coords.y, row.toString());

                    coords = advance(map, coords);
                    steps--;
                }

                pos = nextTurn;
            } else {
                char turn = cmd.charAt(pos);

                if (turn == 'R') {
                    coords.dir++;
                }
                if (turn == 'L') {
                    coords.dir--;
                }
                if (coords.dir < 0) {
                    coords.dir += 4;
                }
                if (coords.dir >= 4) {
                    coords.dir -= 4;
                }

                pos++;
            }
        }

        System.out.println();
        for (String row : trace) {
            System.out.println(row);
        }

        coords.x++;
        coords.y++;
        System.out.println(coords.y * 1000 + coords.x * 4 + coords.dir);

        System.out.println("117005 was too low");
    }

    private static final int edge = 50;
    //private static final int edge = 4;

    private static Coords advance(List<String> map, final Coords coords) throws Exception {
        Coords next = coords.copy();

        if (coords.dir == 0) {
            next.x++;
        }
        if (coords.dir == 1) {
            next.y++;
        }
        if (coords.dir == 2) {
            next.x--;
        }
        if (coords.dir == 3) {
            next.y--;
        }

        char t = tile(map, next);
        if (t == ' ') {
            int ex = (next.x + edge * 100) / edge - 100;
            int ey = (next.y + edge * 100) / edge - 100;

            Coords e = new Coords((next.x + edge * 100) % edge, (next.y + edge * 100) % edge, next.dir);

            if (ex == 1 && ey == -1) {
                ass(e.dir == 3);
                ex = 0;
                ey = 3;
                e = rotR(e);
            } else if (ex == 2 && ey == -1) {
                ass(e.dir == 3);
                ex = 0;
                ey = 3;
                e = e;
            } else if (ex == 3 && ey == 0) {
                ass(e.dir == 0);
                ex = 1;
                ey = 2;
                e = rotR(rotR(e));
            } else if (ex == 2 && ey == 1) {
                if (e.dir == 1) {
                    ex = 1;
                    ey = 1;
                    e = rotR(e);
                } else {
                    ass(e.dir == 0);
                    ex = 2;
                    ey = 0;
                    e = rotL(e);
                }
            } else if (ex == 2 && ey == 2) {
                ass(e.dir == 0);
                ex = 2;
                ey = 0;
                e = rotR(rotR(e));
            } else if (ex == 1 && ey == 3) {
                if (e.dir == 1) {
                    ex = 0;
                    ey = 3;
                    e = rotR(e);
                } else {
                    ass(e.dir == 0);
                    ex = 1;
                    ey = 2;
                    e = rotL(e);
                }
            } else if (ex == 0 && ey == 4) {
                ass(e.dir == 1);
                ex = 2;
                ey = 0;
                e = e;
            } else if (ex == -1 && ey == 3) {
                ass(e.dir == 2);
                ex = 1;
                ey = 0;
                e = rotL(e);
            } else if (ex == -1 && ey == 2) {
                ass(e.dir == 2);
                ex = 1;
                ey = 0;
                e = rotR(rotR(e));
            } else if (ex == 0 && ey == 1) {
                if (e.dir == 3) {
                    ex = 1;
                    ey = 1;
                    e = rotR(e);
                } else {
                    ass(e.dir == 2);
                    ex = 0;
                    ey = 2;
                    e = rotL(e);
                }
            } else if (ex == 0 && ey == 0) {
                ass(e.dir == 2);
                ex = 0;
                ey = 2;
                e = rotR(rotR(e));
            } else {
                throw new Exception("Illegal move: " + next);
            }

            next.x = ex * edge + e.x;
            next.y = ey * edge + e.y;
            next.dir = e.dir;

            System.out.println("Teleported: " + coords + " -> " + next);
        }

        return next;
    }

    private static Coords advance2(List<String> map, final Coords coords) throws Exception {
        Coords next = coords.copy();

        if (coords.dir == 0) {
            next.x++;
        }
        if (coords.dir == 1) {
            next.y++;
        }
        if (coords.dir == 2) {
            next.x--;
        }
        if (coords.dir == 3) {
            next.y--;
        }

        char t = tile(map, next);
        if (t == ' ') {
            int ex = next.x / edge;
            int ey = next.y / edge;

            Coords e = new Coords(next.x % edge, next.y % edge, next.dir);

            if (ex == 0 && ey == 0) {
                ass(e.dir == 3);
                ex = 2;
                ey = 0;
                e = rotR(rotR(e));
            } else if (ex == 1 && ey == 0) {
                if (e.dir == 3) {
                    ex = 2;
                    ey = 0;
                    e = rotR(e);
                } else {
                    ass(e.dir == 2);
                    ex = 1;
                    ey = 1;
                    e = rotL(e);
                }
            } else if (ex == 2 && ey == -1) {
                ass(e.dir == 3);
                ex = 0;
                ey = 1;
                e = rotR(rotR(e));
            } else if (ex == 3 && ey == 0) {
                ass(e.dir == 0);
                ex = 3;
                ey = 2;
                e = rotR(rotR(e));
            } else if (ex == 3 && ey == 1) {
                if (e.dir == 0) {
                    ex = 3;
                    ey = 2;
                    e = rotR(e);
                } else {
                    ass(e.dir == 3);
                    ex = 2;
                    ey = 1;
                    e = rotL(e);
                }
            } else if (ex == 4 && ey == 2) {
                ass(e.dir == 0);
                ex = 2;
                ey = 0;
                e = rotR(rotR(e));
            } else if (ex == 3 && ey == 3) {
                ass(e.dir == 1);
                ex = 0;
                ey = 1;
                e = rotL(e);
            } else if (ex == 2 && ey == 3) {
                ass(e.dir == 1);
                ex = 0;
                ey = 1;
                e = rotR(rotR(e));
            } else if (ex == 1 && ey == 2) {
                if (e.dir == 2) {
                    ex = 1;
                    ey = 1;
                    e = rotR(e);
                } else {
                    ass(e.dir == 1);
                    ex = 2;
                    ey = 2;
                    e = rotL(e);
                }
            } else if (ex == 0 && ey == 2) {
                ass(e.dir == 1);
                ex = 2;
                ey = 2;
                e = rotR(rotR(e));
            } else if (ex == -1 && ey == 1) {
                ass(e.dir == 2);
                ex = 3;
                ey = 2;
                e = rotR(e);
            } else {
                throw new Exception("Illegal move: " + next);
            }

            next.x = ex * edge + e.x;
            next.y = ey * edge + e.y;
            next.dir = e.dir;

            System.out.println("Teleported: " + coords + " -> " + next);
        }

        return next;
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private static Coords rotR(Coords c) {
        int x = edge - 1 - c.y;
        int y = c.x;
        return new Coords(x, y, c.dir + 1);
    }

    private static Coords rotL(Coords c) {
        return rotR(rotR(rotR(c)));
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

    private static boolean stepAvail(List<String> map, Coords coords) throws Exception {
        Coords next = advance(map, coords);
        char t = tile(map, next);
        return t == '.';
    }

    private static void ass(boolean c) throws Exception {
        if (!c) {
            throw new Exception("Assertion failure");
        }
    }
}
