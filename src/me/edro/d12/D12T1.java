package me.edro.d12;

import java.util.*;

public class D12T1 {
    static Character[][] map;
    static int[][] path;
    static int width = 0;
    static int height = 0;

    public static class Coords {
        public int x;
        public int y;

        public Coords(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    public static Queue<Coords> q = new LinkedList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<String> rawMap = new ArrayList<>();
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if (!line.isBlank()) {
                rawMap.add(line);
            }
        }

        width = rawMap.get(0).length();
        height = rawMap.size();

        map = new Character[width][height];
        path = new int[width][height];

        int startX = 0;
        int startY = 0;

        int endX = 0;
        int endY = 0;

        int currentRow = 0;
        for (String row: rawMap){
            for (int i = 0; i < row.length(); i++){
                map[i][currentRow] = row.charAt(i);
                path[i][currentRow] = -1;

                if (row.charAt(i) == 'S'){
                    startX = i;
                    startY = currentRow;
                    map[i][currentRow] = 'a';
                }

                if (row.charAt(i) == 'E'){
                    endX = i;
                    endY = currentRow;
                    map[i][currentRow] = 'z';
                }

            }
            currentRow++;
        }

        enqueue(startX, startY, 'a', -1);

        while (!q.isEmpty()){
            Coords next = q.poll();
            int x = next.x;
            int y = next.y;
            enqueue(x - 1, y, map[x][y], path[x][y]);
            enqueue(x + 1, y, map[x][y], path[x][y]);
            enqueue(x, y - 1, map[x][y], path[x][y]);
            enqueue(x, y + 1, map[x][y], path[x][y]);
        }

        System.out.println("From " + startX + "," + startY + " to " + endX + "," + endY);

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
               System.out.printf("%4d", path[j][i]);
            }
            System.out.println();
        }

        System.out.println(path[endX][endY]);
    }

    public static void enqueue(int x, int y, char h, int d){
        if (x >= 0 && x < width && y >= 0 && y < height && path[x][y] == -1){
            if (map[x][y] - h <= 1){
                q.add(new Coords(x, y));
                path[x][y] = d + 1;
            }
        }
    }

}
