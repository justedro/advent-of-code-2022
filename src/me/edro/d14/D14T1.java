package me.edro.d14;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class D14T1 {
    private static class Line {
        public int sx;
        public int sy;
        public int ex;
        public int ey;

        public Line(int sx, int sy, int ex, int ey) {
            this.sx = min(sx, ex);
            this.sy = min(sy, ey);
            this.ex = max(sx, ex);
            this.ey = max(sy, ey);
        }

        public boolean isHorizontal() {
            return sy == ey;
        }
    }

    private static char[][] map;
    private static int minx = Integer.MAX_VALUE;
    private static int miny = 0;
    private static int maxx = Integer.MIN_VALUE;
    private static int maxy = Integer.MIN_VALUE;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Line> rocks = new ArrayList<>();

        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (line.isBlank()) {
                continue;
            }

            var begin = true;
            var sx = 0;
            var sy = 0;
            for (String point : line.split(" -> ")) {
                var coords = point.split(",");
                var x = Integer.parseInt(coords[0]);
                var y = Integer.parseInt(coords[1]);

                if (begin) {
                    begin = false;
                } else {
                    rocks.add(new Line(sx, sy, x, y));
                }
                sx = x;
                sy = y;

                maxx = max(maxx, x);
                maxy = max(maxy, y);
                minx = min(minx, x);
                miny = min(miny, y);
            }
        }


        map = new char[maxx - minx + 1][maxy - miny + 1];
        for (int i = 0; i < maxx - minx + 1; i++) {
            for (int j = 0; j < maxy - miny + 1; j++) {
                map[i][j] = '.';
            }
        }

        for (Line l : rocks) {
            if (!l.isHorizontal()) {
                // vert
                for (int i = l.sy; i <= l.ey; i++) {
                    map[l.sx - minx][i - miny] = '#';
                }
            } else {
                // hor
                for (int i = l.sx; i <= l.ex; i++) {
                    map[i - minx][l.sy - miny] = '#';
                }
            }
        }

        int sand = 0;
        var fallen = false;
        while (!fallen) {
            int sandX = 500;
            int sandY = 0;
            var moved = true;

            while (moved) {
                // down
                int down = checkMap(sandX, sandY + 1);
                if (down == 1) {
                    sandY++;
                    continue;
                }
                if (down == -1){
                    fallen = true;
                    break;
                }

                // down-left
                int dl = checkMap(sandX - 1, sandY + 1);
                if (dl == 1) {
                    sandY++;
                    sandX--;
                    continue;
                }
                if (dl == -1){
                    fallen = true;
                    break;
                }

                // down-right
                int dr = checkMap(sandX + 1, sandY + 1);
                if (dr == 1) {
                    sandY++;
                    sandX++;
                    continue;
                }
                if (dr == -1){
                    fallen = true;
                    break;
                }

                map[sandX - minx][sandY - miny] = 'o';
                moved = false;
            }
            if (!fallen) {
                sand++;
            }
        }

        System.out.println(sand);
    }

    private static int checkMap(int x, int y){
        if (x < minx || x > maxx || y < miny || y > maxy){
            return -1;
        }
        if (map[x - minx][y - miny] == '.'){
            return 1;
        }
        return 0;
    }

}
