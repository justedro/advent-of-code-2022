package me.edro.d18;

import java.util.*;

public class D18T2 {

    static class Kubik {
        public int x;
        public int y;
        public int z;

        Kubik(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Kubik kubik = (Kubik) o;
            return x == kubik.x && y == kubik.y && z == kubik.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }

        @Override
        public String toString() {
            return "Kubik{" +
                    "x=" + x +
                    ", y=" + y +
                    ", z=" + z +
                    '}';
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Set<Kubik> world = new HashSet<>();

        Kubik min = new Kubik(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        Kubik max = new Kubik(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (!line.isBlank()) {
                String[] parts = line.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int z = Integer.parseInt(parts[2]);

                if (x < min.x) min.x = x;
                if (y < min.y) min.y = y;
                if (z < min.z) min.z = z;

                if (x > max.x) max.x = x;
                if (y > max.y) max.y = y;
                if (z > max.z) max.z = z;

                world.add(new Kubik(x, y, z));
            }
        }

        max.x+=1;
        max.y+=1;
        max.z+=1;
        min.x-=1;
        min.y-=1;
        min.z-=1;

        System.out.println("Min: " + min);
        System.out.println("Max: " + max);

        for (int i = min.x; i <= max.x; i++) {
            for (int j = min.y; j <= max.y; j++) {
                for (int k = min.z; k <= max.z; k++) {
                    Kubik o = new Kubik(i, j, k);
                    if (!world.contains(o) && blow(i, j, k, new HashSet<>(), world, min, max)) {
                        world.add(o);
                    }
                }
            }
        }

        int cnt = 0;
        for (Kubik k : world) {
            cnt += (world.contains(new Kubik(k.x - 1, k.y, k.z))) ? 0 : 1;
            cnt += (world.contains(new Kubik(k.x + 1, k.y, k.z))) ? 0 : 1;
            cnt += (world.contains(new Kubik(k.x, k.y - 1, k.z))) ? 0 : 1;
            cnt += (world.contains(new Kubik(k.x, k.y + 1, k.z))) ? 0 : 1;
            cnt += (world.contains(new Kubik(k.x, k.y, k.z - 1))) ? 0 : 1;
            cnt += (world.contains(new Kubik(k.x, k.y, k.z + 1))) ? 0 : 1;
        }

        System.out.println(cnt);
    }

    static boolean blow(int x, int y, int z, Set<Kubik> blown, Set<Kubik> world, Kubik min, Kubik max) {
        Queue<Kubik> q = new LinkedList<>();
        q.add(new Kubik(x, y, z));

        boolean res = true;
        while (!q.isEmpty()) {
            Kubik k = q.poll();

            if (k.x < min.x || k.x > max.x) res = false;
            if (k.y < min.y || k.y > max.y) res = false;
            if (k.z < min.z || k.z > max.z) res = false;

            if (!res){
                break;
            }

            if (world.contains(k)) {
                continue;
            }

            if (blown.contains(k)) {
                continue;
            }

            blown.add(k);
            q.add(new Kubik(k.x + 1, k.y, k.z));
            q.add(new Kubik(k.x - 1, k.y, k.z));
            q.add(new Kubik(k.x, k.y + 1, k.z));
            q.add(new Kubik(k.x, k.y - 1, k.z));
            q.add(new Kubik(k.x, k.y, k.z + 1));
            q.add(new Kubik(k.x, k.y, k.z - 1));
        }

        return res;
    }
}
