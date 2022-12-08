package me.edro.d8;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class D8T2 {
    static List<String> rawMap = new ArrayList<>();
    static Integer mapWidth;
    static Integer mapHeight;
    static int[][][][] mem; // dir, height, x, y
    static int[][] map; // x, y

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            rawMap.add(line);
            mapWidth = line.length();
        }
        mapHeight = rawMap.size();

        map = new int[mapWidth][mapHeight];
        for (int i = 0; i < mapHeight; i++) {
            for (int j = 0; j < mapWidth; j++) {
                map[j][i] = rawMap.get(i).charAt(j) - '0';
            }
        }

        mem = new int[4][10][mapWidth][mapHeight];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                for (int k = 0; k < mapWidth; k++) {
                    for (int m = 0; m < mapHeight; m++) {
                        mem[i][j][k][m] = -1;
                    }
                }
            }
        }

        int max = 0;

        for (int i = 0; i < mapWidth; i++) {
            for (int j = 0; j < mapHeight; j++) {
                int r = rate(i, j);
                if (r > max){
                    max = r;
                }
            }
        }

        System.out.println(max);
    }

    static int rate(int x, int y) {
        int height = map[x][y];
        return rateSide(x, y, height, 0)
                * rateSide(x, y, height, 1)
                * rateSide(x, y, height, 2)
                * rateSide(x, y, height, 3);
    }

    public static int coordXFromSide(int x, int side) {
        if (side == 0) {
            return x - 1;
        }
        if (side == 2) {
            return x + 1;
        } else return x;
    }

    public static int coordYFromSide(int y, int side) {
        if (side == 1) {
            return y + 1;
        }
        if (side == 3) {
            return y - 1;
        } else return y;
    }

    static int rateSide(int x, int y, int height, int side) {
        int sx = coordXFromSide(x, side);
        int sy = coordYFromSide(y, side);

        if (!withinMap(sx, sy)) {
            return 0;
        }

        if (height <= map[sx][sy]){
            return 1;
        }

        // dir, height, x, y
        if (mem[side][height][sx][sy] == -1){
            mem[side][height][sx][sy] = rateSide(sx, sy, height, side);
        }

        return mem[side][height][sx][sy] + 1;
    }


    static boolean withinMap(int x, int y) {
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
    }
}
