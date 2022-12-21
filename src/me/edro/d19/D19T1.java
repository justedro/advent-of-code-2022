package me.edro.d19;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class D19T1 {

    static class Recipe {
        public int oreRobotOre;
        public int clayRobotOre;
        public int obsidianRobotOre;
        public int obsidianRobotClay;
        public int geodeRobotOre;
        public int geodeRobotObsidian;
    }

    static class Robots {
        public int oreRobots = 0;
        public int clayRobots = 0;
        public int obsidianRobots = 0;
        public int geodeRobots = 0;

        public Robots() {
        }

        public Robots(int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots) {
            this.oreRobots = oreRobots;
            this.clayRobots = clayRobots;
            this.obsidianRobots = obsidianRobots;
            this.geodeRobots = geodeRobots;
        }

        public Resources generate() {
            Resources resources = new Resources();
            resources.ore = oreRobots;
            resources.clay = clayRobots;
            resources.obsidian = obsidianRobots;
            resources.geode = geodeRobots;
            return resources;
        }

        public Robots buildGeo() {
            Robots copy = new Robots(oreRobots, clayRobots, obsidianRobots, geodeRobots);
            copy.geodeRobots++;
            return copy;
        }

        public Robots buildObs() {
            Robots copy = new Robots(oreRobots, clayRobots, obsidianRobots, geodeRobots);
            copy.obsidianRobots++;
            return copy;
        }

        public Robots buildCla() {
            Robots copy = new Robots(oreRobots, clayRobots, obsidianRobots, geodeRobots);
            copy.clayRobots++;
            return copy;
        }

        public Robots buildOre() {
            Robots copy = new Robots(oreRobots, clayRobots, obsidianRobots, geodeRobots);
            copy.oreRobots++;
            return copy;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Robots robots = (Robots) o;
            return oreRobots == robots.oreRobots && clayRobots == robots.clayRobots && obsidianRobots == robots.obsidianRobots && geodeRobots == robots.geodeRobots;
        }

        @Override
        public int hashCode() {
            return Objects.hash(oreRobots, clayRobots, obsidianRobots, geodeRobots);
        }
    }

    static class Resources {
        public int ore = 0;
        public int clay = 0;
        public int obsidian = 0;
        public int geode = 0;

        public Resources() {
        }

        public Resources(int ore, int clay, int obsidian, int geode) {
            this.ore = ore;
            this.clay = clay;
            this.obsidian = obsidian;
            this.geode = geode;
        }

        public boolean valid() {
            return geode >= 0 && obsidian >= 0 && clay >= 0 && ore >= 0;
        }

        public void add(Resources r) {
            ore += r.ore;
            clay += r.clay;
            obsidian += r.obsidian;
            geode += r.geode;
        }

        public Resources buildGeo(Recipe rec) {
            Resources copy = new Resources(ore, clay, obsidian, geode);
            copy.ore -= rec.geodeRobotOre;
            copy.obsidian -= rec.geodeRobotObsidian;
            return copy;
        }

        public Resources buildObs(Recipe rec) {
            Resources copy = new Resources(ore, clay, obsidian, geode);
            copy.ore -= rec.obsidianRobotOre;
            copy.clay -= rec.obsidianRobotClay;
            return copy;
        }

        public Resources buildCla(Recipe rec) {
            Resources copy = new Resources(ore, clay, obsidian, geode);
            copy.ore -= rec.clayRobotOre;
            return copy;
        }

        public Resources buildOre(Recipe rec) {
            Resources copy = new Resources(ore, clay, obsidian, geode);
            copy.ore -= rec.oreRobotOre;
            return copy;
        }

        public Resources copy() {
            return new Resources(ore, clay, obsidian, geode);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Resources resources = (Resources) o;
            return ore == resources.ore && clay == resources.clay && obsidian == resources.obsidian && geode == resources.geode;
        }

        @Override
        public int hashCode() {
            return Objects.hash(ore, clay, obsidian, geode);
        }

        public int checkOre(Recipe rec, Resources gen) {
            return steps(rec.oreRobotOre - ore, gen.ore);
        }

        public int checkCla(Recipe rec, Resources gen) {
            return steps(rec.clayRobotOre - ore, gen.ore);
        }

        public int checkObs(Recipe rec, Resources gen) {
            return max(
                    steps(rec.obsidianRobotOre - ore, gen.ore),
                    steps(rec.obsidianRobotClay - clay, gen.clay)
            );
        }

        public int checkGeo(Recipe rec, Resources gen) {
            return max(
                    steps(rec.geodeRobotOre - ore, gen.ore),
                    steps(rec.geodeRobotObsidian - obsidian, gen.obsidian)
            );
        }

        public Resources mult(int t) {
            return new Resources(ore * t, clay * t, obsidian * t, geode * t);
        }
    }

    static int steps(int demand, int generation) {
        if (generation == 0) return Integer.MAX_VALUE;

        demand = max(demand, 0);
        var full = demand / generation;
        var part = demand % generation;
        return full + (part > 0 ? 1 : 0) + 1;
    }

    static class DPEntry {
        private Robots robots;
        private Resources resources;
        private int timeLimit;

        DPEntry(Robots robots, Resources resources, int timeLimit) {
            this.robots = robots;
            this.resources = resources;
            this.timeLimit = timeLimit;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DPEntry dpEntry = (DPEntry) o;
            return timeLimit == dpEntry.timeLimit && robots.equals(dpEntry.robots) && resources.equals(dpEntry.resources);
        }

        @Override
        public int hashCode() {
            return Objects.hash(robots, resources, timeLimit);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regex = "Blueprint (\\d+):\\s+Each ore robot costs (\\d+) ore\\.\\s+Each clay robot costs (\\d+) ore\\.\\s+Each obsidian robot costs (\\d+) ore and (\\d+) clay\\.\\s+Each geode robot costs (\\d+) ore and (\\d+) obsidian\\.";
        Pattern pattern = Pattern.compile(regex);

        final Integer[] cnt = {0};
        scanner.findAll(pattern).forEach(matcher -> {
            var id = Integer.parseInt(matcher.group(1));

            Recipe r = new Recipe();
            r.oreRobotOre = Integer.parseInt(matcher.group(2));
            r.clayRobotOre = Integer.parseInt(matcher.group(3));
            r.obsidianRobotOre = Integer.parseInt(matcher.group(4));
            r.obsidianRobotClay = Integer.parseInt(matcher.group(5));
            r.geodeRobotOre = Integer.parseInt(matcher.group(6));
            r.geodeRobotObsidian = Integer.parseInt(matcher.group(7));

            Robots robots = new Robots();
            robots.oreRobots = 1;

            Resources res = new Resources();

            Map<DPEntry, Integer> dp = new HashMap<>();

            var best = simulate(
                    r,
                    robots,
                    res,
                    new Resources(),
                    24,
                    dp
            );

            System.out.println("BP " + id + " geode: " + best);

            cnt[0] += best * id;
        });

        System.out.println(cnt[0]);
    }


    static int[] dbg = new int[]{
            0, // 0
            0, // 1,
            0, // 2,
            0, // 3,
            4, // 4,
            0, // 5
            0, // 6
            4, // 7
            0, // 8
            0, // 9
            3, // 10
            0, // 11
            0, // 12
            2, // 13/
            3, // 14
            0, // 15
            0, // 16
            0, // 17
            2, // 18
            0, // 19
            2, // 20
            0, // 21
            2, // 22
            0, // 23
            0, // 24
    };

    private static int simulate(
            Recipe rec,
            Robots robots,
            Resources resources,
            Resources generated,
            int timeLimit,
            Map<DPEntry, Integer> dp
    ) {
        if (timeLimit < 0) {
            return 0;
        }

        if (!resources.valid()) {
            return 0;
        }
        resources.add(generated);

        if (timeLimit == 0) {
            return resources.geode;
        }

        int mm;
        //DPEntry dpEntry = new DPEntry(robots, resources, timeLimit);
        //if (!dp.containsKey(dpEntry)) {
        Resources gen = robots.generate();

        mm = 0;
        int stepsForOre = Integer.MAX_VALUE;
        int stepsForCla = Integer.MAX_VALUE;
        int stepsForObs = Integer.MAX_VALUE;

        int stepsForGeo = resources.checkGeo(rec, gen);
        if (stepsForGeo <= 1) {
            mm = max(mm, simulate(rec, robots.buildGeo(), resources.buildGeo(rec), gen.mult(stepsForGeo), timeLimit - stepsForGeo, dp));
        } else {
            stepsForOre = resources.checkOre(rec, gen);
            stepsForCla = resources.checkCla(rec, gen);
            stepsForObs = resources.checkObs(rec, gen);

            mm = max(mm, simulate(rec, robots.buildOre(), resources.buildOre(rec), gen.mult(stepsForOre), timeLimit - stepsForOre, dp));
            mm = max(mm, simulate(rec, robots.buildCla(), resources.buildCla(rec), gen.mult(stepsForCla), timeLimit - stepsForCla, dp));
            mm = max(mm, simulate(rec, robots.buildObs(), resources.buildObs(rec), gen.mult(stepsForObs), timeLimit - stepsForObs, dp));

            var st = max(mmin(stepsForOre, stepsForCla, stepsForObs, stepsForGeo) - 1, 1);
            mm = max(mm, simulate(rec, robots, resources, gen.mult(st), timeLimit - st, dp));
        }

        //    dp.put(dpEntry, mm);
        //} else {
        //    mm = dp.get(dpEntry);
        //}

        return mm;
    }

    private static int mmax(int a, int b, int c, int d) {
        return max(max(a, b), max(c, d));
    }

    private static int mmin(int a, int b, int c, int d) {
        return min(min(a, b), min(c, d));
    }
}
