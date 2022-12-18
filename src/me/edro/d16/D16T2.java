package me.edro.d16;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.max;

public class D16T2 {
    static Map<String, String[]> map = new HashMap<>();
    static Map<String, Integer> flows = new HashMap<>();
    static Map<String, Byte> enumerated = new HashMap<>();
    static List<Byte> active = new ArrayList<>();
    static List<String> reverse = new ArrayList<>();
    static Map<Byte, Byte> bitpos = new HashMap<>();

    static int[][] distances;

    static Map<Node, Integer> dpm = new HashMap<>();
    static class Node {
        int opened;
        byte pos1;
        byte pos2;
        byte tl1;
        byte tl2;

        Node(int opened, byte pos1, byte pos2, byte tl1, byte tl2){
            this.opened = opened;
            this.pos1 = pos1;
            this.pos2 = pos2;
            this.tl1 = tl1;
            this.tl2 = tl2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return opened == node.opened && pos1 == node.pos1 && pos2 == node.pos2 && tl1 == node.tl1 && tl2 == node.tl2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(opened, pos1, pos2, tl1, tl2);
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Valve GG has flow rate=0; tunnels lead to valves FF, HH
        //Valve HH has flow rate=22; tunnel leads to valve GG

        String regex = "Valve (\\w+) has flow rate=(\\d+); tunnels? leads? to valves? ([\\w\\, ]+)";
        Pattern pattern = Pattern.compile(regex);

        byte e = 0;
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
                    bitpos.put(e, (byte)(active.size() - 1));
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
        System.out.println("active: " + active.size() + " -> " + (2 << active.size()));
        System.out.println("map: " + map.size());

        boolean[] opened = new boolean[active.size()];
        int res = visit(enumerated.get("AA"), enumerated.get("AA"), opened, (byte)26, (byte)26, 0);

        System.out.println(res); // 1707
    }


    static int visit(byte pos1, byte pos2, boolean[] opened, byte tl1, byte tl2, int depth) {
        if (tl1 <= 0 || tl2 <= 0) {
            return 0;
        }

        int res;
        var bits = pack(opened);

        Node dpn = new Node(bits, pos1, pos2, tl1, tl2);

        if (depth == 0 || !dpm.containsKey(dpn)) {
            var best = 0;
            for (byte dir : active) {
                if (!opened[bitpos.get(dir)]) {
                    opened[bitpos.get(dir)] = true;

                    var flow = flows.get(reverse.get(dir));
                    var b1 = (tl1 - distances[pos1][dir] - 1) * flow;
                    var b2 = (tl2 - distances[pos2][dir] - 1) * flow;

                    var p1 = visit(dir, pos2, opened, (byte)(tl1 - distances[pos1][dir] - 1), tl2, depth + 1);
                    var p2 = visit(pos1, dir, opened, tl1, (byte)(tl2 - distances[pos2][dir] - 1), depth + 1);

                    var bp = max(p1 + b1, p2 + b2);

                    if (bp > best) {
                        best = bp;
                    }

                    opened[bitpos.get(dir)] = false;
                }
            }
            res = best;

            dpm.put(dpn, res);
        } else {
            res = dpm.get(dpn);
        }

        return res;
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
