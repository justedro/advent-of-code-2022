package me.edro.d16;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class D16T1 {
    static Map<String, String[]> map = new HashMap<>();
    static Map<String, Integer> flows = new HashMap<>();
    static Map<String, Integer> enumerated = new HashMap<>();
    static List<Integer> active = new ArrayList<>();
    static List<String> reverse = new ArrayList<>();
    static Map<Integer, Integer> bitpos = new HashMap<>();
    static Map<String, String> backtrack = new HashMap<>();

    //static LinkedList<String> path = new LinkedList<>(Arrays.asList("DD", "BB", "JJ", "HH", "EE", "CC"));

    // opened, pos, TL
    static int[][][] dp;
    static int[][] distances;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Valve GG has flow rate=0; tunnels lead to valves FF, HH
        //Valve HH has flow rate=22; tunnel leads to valve GG

        String regex = "Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? ([\\w\\, ]+)";
        Pattern pattern = Pattern.compile(regex);

        int e = 0;
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                var valve = matcher.group(1);
                var flow = Integer.parseInt(matcher.group(2));
                var dirs = matcher.group(3);

                map.put(valve, dirs.split(", "));
                flows.put(valve, flow);

                enumerated.put(valve, e);
                reverse.add(valve);
                if (flow > 0) {
                    active.add(e);
                    bitpos.put(e, active.size() - 1);
                }
                e++;
            }
        }

        // calc distances
        distances = new int[map.size()][map.size()];
        for (int i = 0; i < map.size(); i++) {
            Arrays.fill(distances[i], -1);
            measure(distances[i], i);
        }


        // opened, pos, TL
        dp = new int[2 << active.size()][map.size()][31];
        for (int[][] ints : dp) {
            for (int[] anInt : ints) {
                Arrays.fill(anInt, -1);
            }
        }

        boolean[] opened = new boolean[active.size()];
        int res = visit(enumerated.get("AA"), opened, 30, 0);

        System.out.println(res);
    }



    static int visit(int valve, boolean[] opened, int tl, int depth) {
        int res;
        var bits = pack(opened);

        System.out.println(" ".repeat(depth) + "-> " + reverse.get(valve) + " (" + tl + ")");

        if (dp[bits][valve][tl] == -1) {
            int flow = flows.get(reverse.get(valve));
            res = (tl - 1) * flow;
            res += goFrom(valve, opened, tl - (flow > 0? 1: 0), depth);

            dp[bits][valve][tl] = res;
        } else {
            res = dp[bits][valve][tl];
            System.out.println(" ".repeat(depth) + " DP catch: " + res);
        }

        System.out.println(" ".repeat(depth) + "<- " + reverse.get(valve) + ": " + res);

        return res;
    }

    static int goFrom(int valve, boolean[] opened, int tl, int depth) {
        if (tl <= 0) {
            return 0;
        }

        var best = 0;
        for (int dir : active) {
            /*
            if (path.size() > 0 && path.peekFirst().equals(reverse.get(dir))){
                path.removeFirst();
            } else {
                continue;
            }
            */

            if (!opened[bitpos.get(dir)]) {
                var cost = distances[valve][dir];
                if (tl - cost > 1) {
                    opened[bitpos.get(dir)] = true;

                    var curr = visit(dir, opened, tl - cost, depth + 1);
                    if (curr > best) {
                        best = curr;
                    }
                    opened[bitpos.get(dir)] = false;
                }
            }
        }

        return best;
    }

    static void measure(int[] distances, int valve) {
        Queue<Integer> q = new LinkedList<>();
        q.add(valve);
        distances[valve] = 0;

        while (q.size() > 0) {
            int cur = q.poll();
            int dist = distances[cur];

            String curs = reverse.get(cur);
            for (String dir : map.get(curs)) {
                int to = enumerated.get(dir);
                if (distances[to] == -1) {
                    q.add(to);
                    distances[to] = dist + 1;
                }
            }
        }
    }

    static int pack(boolean[] bits) {
        int res = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) {
                res |= 1 << i;
            }
        }
        return res;
    }
}
