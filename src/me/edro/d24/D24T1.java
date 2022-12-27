package me.edro.d24;

import java.util.*;

public class D24T1 {
    static class Coords {
        public int x;
        public int y;

        public Coords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Coords copy() {
            return new Coords(x, y);
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
    }

    public static class Blizzard {
        public int x;
        public int y;
        public int dx;
        public int dy;

        public Blizzard(int x, int y, int dx, int dy) {
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
        }

        public Blizzard copy() {
            return new Blizzard(x, y, dx, dy);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Blizzard blizzard = (Blizzard) o;
            return x == blizzard.x && y == blizzard.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public void advance(int mapWidth, int mapHeight) {
            x += dx;
            y += dy;

            if (x <= 0) {
                x = mapWidth - 2;
            }
            if (x >= mapWidth - 1) {
                x = 1;
            }
            if (y <= 0) {
                y = mapHeight - 2;
            }
            if (y >= mapHeight - 1) {
                y = 1;
            }
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Blizzard> blizzards = new ArrayList<>();
        int mapHeight = 0;
        int mapWidth = 0;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }

            mapWidth = line.length();

            for (int i = 0; i < line.length(); i++) {
                if (
                        line.charAt(i) == '>'
                                || line.charAt(i) == '<'
                                || line.charAt(i) == '^'
                                || line.charAt(i) == 'v'
                ) {
                    int dx = line.charAt(i) == '>' ? 1 : (line.charAt(i) == '<' ? -1 : 0);
                    int dy = line.charAt(i) == 'v' ? 1 : (line.charAt(i) == '^' ? -1 : 0);

                    blizzards.add(new Blizzard(i, mapHeight, dx, dy));
                }
            }

            mapHeight++;
        }

        List<Coords> moves = new ArrayList<>();
        moves.add(new Coords(1, 0));

        int steps = 0;
        while (true) {
            Set<Coords> currentBlizzards = new HashSet<>();
            for (Blizzard b : blizzards) {
                currentBlizzards.add(new Coords(b.x, b.y));
            }

            Set<Coords> nextMoves = new HashSet<>();
            boolean finish = false;
            for (Coords c : moves) {
                // finish condition
                if (c.x == mapWidth - 2 && c.y == mapHeight - 1) {
                    finish = true;
                    break;
                }

                if (
                        currentBlizzards.contains(c)
                                || c.x <= 0
                                || c.x >= mapWidth - 1
                                || c.y <= 0 && c.x != 1
                                || c.y >= mapHeight - 1
                ) {
                    continue; // skip impossible
                }

                // wait
                nextMoves.add(c);

                // move
                nextMoves.add(new Coords(c.x + 1, c.y));
                nextMoves.add(new Coords(c.x - 1, c.y));
                nextMoves.add(new Coords(c.x, c.y + 1));
                nextMoves.add(new Coords(c.x, c.y - 1));
            }

            if (finish) {
                break;
            }

            steps++;
            if (nextMoves.size() == 0){
                System.out.println("No steps possible after step " + steps);
                break;
            }

            moves = new ArrayList<>(nextMoves);
            System.out.println("Checked step " + steps + ", new possible positions: " + moves.size());

            for (Blizzard b : blizzards) {
                b.advance(mapWidth, mapHeight);
            }
        }

        System.out.println(steps);
    }

}
