package me.edro.d23;

import java.util.*;
import java.util.stream.Collectors;

public class D23T1 {

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
    }

    public static class Elf {
        public int x;
        public int y;

        public Elf(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Elf copy() {
            return new Elf(x, y);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Elf elve = (Elf) o;
            return x == elve.x && y == elve.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Elf{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }

        public Coords add(Coords move) {
            return new Coords(x + move.x, y + move.y);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Set<Elf> elves = new HashSet<>();
        int lineNr = 0;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (line.isBlank()) {
                break;
            }

            for (int i = 0; i < line.length(); i++) {
                if (line.charAt(i) == '#') {
                    elves.add(new Elf(i, lineNr));
                }
            }

            lineNr++;
        }

        for (int i = 0; i < 1000; i++) {
            Coords[] c = new Coords[]{
                    new Coords(0, -1),
                    new Coords(0, 1),
                    new Coords(-1, 0),
                    new Coords(1, 0),
            };

            Set<Coords> coords = elves.stream().map(elf -> new Coords(elf.x, elf.y)).collect(Collectors.toSet());
            Map<Coords, Elf> newCoords = new HashMap<>();
            for (Elf e : elves) {
                if (
                        coords.contains(new Coords(e.x + 1, e.y + 1))
                                || coords.contains(new Coords(e.x + 1, e.y))
                                || coords.contains(new Coords(e.x + 1, e.y - 1))
                                || coords.contains(new Coords(e.x, e.y - 1))
                                || coords.contains(new Coords(e.x - 1, e.y - 1))
                                || coords.contains(new Coords(e.x - 1, e.y))
                                || coords.contains(new Coords(e.x - 1, e.y + 1))
                                || coords.contains(new Coords(e.x, e.y + 1))
                ) {
                    for (int d = 0; d < c.length; d++) {
                        Coords move = c[(i + d) % 4];
                        Coords move1 = move.copy();
                        Coords move2 = move.copy();
                        if (move.x == 0) {
                            move1.x++;
                            move2.x--;
                        } else {
                            move1.y++;
                            move2.y--;
                        }

                        if (
                                !coords.contains(e.add(move))
                                        && !coords.contains(e.add(move1))
                                        && !coords.contains(e.add(move2))
                        ) {

                            if (newCoords.containsKey(e.add(move))) {
                                newCoords.remove(e.add(move));
                            } else {
                                newCoords.put(e.add(move), e);
                            }
                            break;
                        }
                    }
                }
            }

            if (newCoords.size() == 0) {
                System.out.println("No move detected: " + (i + 1));
                break;
            }

            newCoords.forEach(((nc, elf) -> {
                elf.x = nc.x;
                elf.y = nc.y;
            }));

            print(elves);
        }

        Coords min = new Coords(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Coords max = new Coords(Integer.MIN_VALUE, Integer.MIN_VALUE);

        elves.forEach(elf -> {
            if (min.x > elf.x) {
                min.x = elf.x;
            }
            if (min.y > elf.y) {
                min.y = elf.y;
            }
            if (max.x < elf.x) {
                max.x = elf.x;
            }
            if (max.y < elf.y) {
                max.y = elf.y;
            }
        });

        int cnt = 0;
        Set<Coords> coords = elves.stream().map(elf -> new Coords(elf.x, elf.y)).collect(Collectors.toSet());
        for (int i = min.y; i <= max.y; i++) {
            for (int j = min.x; j <= max.x; j++) {
                Coords a = new Coords(j, i);
                if (!coords.contains(a)) {
                    cnt++;
                }
            }
        }

        System.out.println(cnt);
    }

    private static void print(Set<Elf> elves) {
        Coords min = new Coords(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Coords max = new Coords(Integer.MIN_VALUE, Integer.MIN_VALUE);

        elves.forEach(elf -> {
            if (min.x > elf.x) {
                min.x = elf.x;
            }
            if (min.y > elf.y) {
                min.y = elf.y;
            }
            if (max.x < elf.x) {
                max.x = elf.x;
            }
            if (max.y < elf.y) {
                max.y = elf.y;
            }
        });

        Set<Coords> coords = elves.stream().map(elf -> new Coords(elf.x, elf.y)).collect(Collectors.toSet());

        System.out.println();
        for (int i = min.y; i <= max.y; i++) {
            for (int j = min.x; j <= max.x; j++) {
                Coords a = new Coords(j, i);
                if (!coords.contains(a)) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }


}
