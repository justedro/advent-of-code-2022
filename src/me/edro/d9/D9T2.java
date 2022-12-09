package me.edro.d9;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

import static java.lang.Math.abs;

public class D9T2 {

    static class Knot {
        private int x;
        private int y;

        Knot(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Knot knot = (Knot) o;
            return x == knot.x && y == knot.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        public void shift(char dir){
            switch (dir){
                case 'R':
                    x++;
                    break;
                case 'L':
                    x--;
                    break;
                case 'U':
                    y++;
                    break;
                case 'D':
                    y--;
                    break;
            }
        }

        public void moveTowards(Knot knot){
            while (true){
                int dx = knot.x - x;
                int dy = knot.y - y;

                if (abs(dx) <= 1 && abs(dy) <= 1){
                    break;
                }

                x += Integer.compare(dx, 0);
                y += Integer.compare(dy, 0);
            }
        }

        public Knot copy(){
            return new Knot(x, y);
        }
    }

    static Set<Knot> memory = new HashSet<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Knot[] snake = new Knot[10];
        for (int i = 0; i < snake.length; i++){
            snake[i] = new Knot(0,0);
        }

        memory.add(snake[9].copy());

        while (scanner.hasNext()){
            String line = scanner.nextLine();
            char dir = line.charAt(0);
            int steps = Integer.parseInt(line.split(" ")[1]);

            while (steps > 0){
                snake[0].shift(dir);
                for (int i = 1; i < snake.length; i++) {
                    snake[i].moveTowards(snake[i-1]);
                }
                memory.add(snake[9].copy());
                steps--;
            }
        }

        System.out.println(memory.size());
    }
    /*
R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
     */
}
