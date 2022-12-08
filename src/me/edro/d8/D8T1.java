package me.edro.d8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class D8T1 {
    static List<String> map = new ArrayList<>();
    static Integer mapWidth;
    static Integer mapHeight;
    static List<List<List<Character>>> rays = new ArrayList<>();


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        rays.add(new ArrayList<>()); // left
        rays.add(new ArrayList<>()); // bottom
        rays.add(new ArrayList<>()); // right
        rays.add(new ArrayList<>()); // top

        while (scanner.hasNext()){
            String line = scanner.nextLine();
            map.add(line);

            mapWidth = line.length();
            addLine(rays.get(0), line.length());
            addLine(rays.get(1), line.length());
            addLine(rays.get(2), line.length());
            addLine(rays.get(3), line.length());
        }

        mapHeight = map.size();

        int sum = 0;

        for (int i = 0; i < map.size(); i++){
            for (int j = 0; j < map.get(0).length(); j++) {
                if (visible(j, i, map.get(i).charAt(j))){
                    sum++;
                }
            }
        }

        System.out.println(sum);
    }

    static void addLine(List<List<Character>> map, int length){
        List<Character> line = new ArrayList<>();
        while(length > 0){
            line.add('x');
            length--;
        }
        map.add(line);
    }

    static boolean visible(int x, int y, char height){
        return rayHeight(x, y, 0) < height
                || rayHeight(x, y, 1) < height
                || rayHeight(x, y, 2) < height
                || rayHeight(x, y, 3) < height;
    }

    public static int coordXFromSide(int x, int side){
        if (side == 0){
            return x - 1;
        }
        if (side == 2){
            return x + 1;
        }
        else return x;
    }

    public static int coordYFromSide(int y, int side){
        if (side == 1){
            return y + 1;
        }
        if (side == 3){
            return y - 1;
        }
        else return y;
    }

    static char rayHeight(int x, int y, int side){
        int sx = coordXFromSide(x, side);
        int sy = coordYFromSide(y, side);

        if (!withinMap(sx, sy)){
            return '0'-1;
        }

        if (rays.get(side).get(y).get(x) == 'x'){
            rays.get(side).get(y).set(x, max(rayHeight(sx, sy, side), map.get(sy).charAt(sx)));
        }

        return rays.get(side).get(y).get(x);
    }


    static boolean withinMap(int x, int y){
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight;
    }

    static Character max(Character a, Character b){
        if (a > b) return a;
        return b;
    }
}
