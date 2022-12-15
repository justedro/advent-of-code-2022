package me.edro.d15;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

public class D15T1 {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        /// Sensor at x=2, y=18: closest beacon is at x=-2, y=15
        String regex = "Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)";
        Pattern pattern = Pattern.compile(regex);

        //var y = 10;
        var y = 2000000;
        Set<Integer> coverage = new HashSet<>();
        Set<Integer> beacons = new HashSet<>();
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                var sx = Integer.parseInt(matcher.group(1));
                var sy = Integer.parseInt(matcher.group(2));
                var bx = Integer.parseInt(matcher.group(3));
                var by = Integer.parseInt(matcher.group(4));

                if (by == y){
                    beacons.add(bx);
                }

                var radius = abs(sx-bx) + abs(sy-by) + 1;

                var dy = abs(sy - y);
                var er = radius - dy;
                var i = 0;
                while(er > 0){
                    coverage.add(sx + i);
                    coverage.add(sx - i);
                    er--;
                    i++;
                }
            }
        }

        coverage.removeAll(beacons);
        System.out.println(coverage.size());
    }


}
