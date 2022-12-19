package me.edro.d18;

import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class D18T1 {

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
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Set<Kubik> world = new HashSet<>();

        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (!line.isBlank()) {
                String[] parts = line.split(",");
                int x = Integer.parseInt(parts[0]);
                int y = Integer.parseInt(parts[1]);
                int z = Integer.parseInt(parts[2]);

                world.add(new Kubik(x, y, z));
            }
        }

        int cnt = 0;
        for (Kubik k : world) {
            cnt += world.contains(new Kubik(k.x - 1, k.y, k.z)) ? 0 : 1;
            cnt += world.contains(new Kubik(k.x + 1, k.y, k.z)) ? 0 : 1;
            cnt += world.contains(new Kubik(k.x, k.y - 1, k.z)) ? 0 : 1;
            cnt += world.contains(new Kubik(k.x, k.y + 1, k.z)) ? 0 : 1;
            cnt += world.contains(new Kubik(k.x, k.y, k.z - 1)) ? 0 : 1;
            cnt += world.contains(new Kubik(k.x, k.y, k.z + 1)) ? 0 : 1;
        }

        System.out.println(cnt);
    }


}
