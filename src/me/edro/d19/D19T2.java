package me.edro.d19;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class D19T2 {

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

        public Resources add(Resources r) {
            Resources copy = new Resources(ore, clay, obsidian, geode);
            copy.ore += r.ore;
            copy.clay += r.clay;
            copy.obsidian += r.obsidian;
            copy.geode += r.geode;
            return copy;
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
    }

    static int steps(int demand, int generation) {
        if (generation == 0) return Integer.MAX_VALUE;

        demand = max(demand, 0);
        var full = demand / generation;
        var part = demand % generation;
        return full + (part > 0 ? 1 : 0) + 1;
    }

    static class State {
        private Robots robots;
        private Resources resources;

        State(Robots robots, Resources resources) {
            this.robots = robots;
            this.resources = resources;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            State dpEntry = (State) o;
            return robots.equals(dpEntry.robots) && resources.equals(dpEntry.resources);
        }

        @Override
        public int hashCode() {
            return Objects.hash(robots, resources);
        }

        public int value(int time) {
            return ((resources.geode + time * robots.geodeRobots) * 1000000)
                    + resources.obsidian * 10000
                    + resources.clay * 100
                    + resources.ore;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String regex = "Blueprint (\\d+):\\s+Each ore robot costs (\\d+) ore\\.\\s+Each clay robot costs (\\d+) ore\\.\\s+Each obsidian robot costs (\\d+) ore and (\\d+) clay\\.\\s+Each geode robot costs (\\d+) ore and (\\d+) obsidian\\.";
        Pattern pattern = Pattern.compile(regex);

        final Integer[] cnt = {1};
        int n = 3;
        scanner.findAll(pattern).forEach(matcher -> {
            var id = Integer.parseInt(matcher.group(1));

            if (id > 3){
                return;
            }

            Recipe r = new Recipe();
            r.oreRobotOre = Integer.parseInt(matcher.group(2));
            r.clayRobotOre = Integer.parseInt(matcher.group(3));
            r.obsidianRobotOre = Integer.parseInt(matcher.group(4));
            r.obsidianRobotClay = Integer.parseInt(matcher.group(5));
            r.geodeRobotOre = Integer.parseInt(matcher.group(6));
            r.geodeRobotObsidian = Integer.parseInt(matcher.group(7));


            var best = simulate(r);

            System.out.println("BP " + id + " geode: " + best);

            cnt[0] *= best;
        });

        System.out.println(cnt[0]);
    }

    static int[] dbg = new int[]{
            0,
            0,
            0,
            0,
            1,
            0,
            0,
            4,
            0,
            0,
            3,
            0,
            0,
            2,
            3,
            0,
            0,
            0,
            2,
            0,
            2,
            0,
            2,
            0,
            0,
    };

    private static int simulate(Recipe rec) {
        Robots robots = new Robots();
        robots.oreRobots = 1;
        Resources res = new Resources();
        State start = new State(robots, res);

        List<State> mem = new ArrayList<>();
        mem.add(start);

        for (int i = 32; i > 0; i--){
            List<State> nextStep = new ArrayList<>();
            mem.forEach(state -> {
                Resources gen = state.robots.generate();
                Resources ns;

                ns = state.resources.buildGeo(rec);
                if (ns.valid())
                    nextStep.add(new State(state.robots.buildGeo(), ns.add(gen)));

                ns = state.resources.buildOre(rec);
                if (ns.valid())
                    nextStep.add(new State(state.robots.buildOre(), ns.add(gen)));

                ns = state.resources.buildCla(rec);
                if (ns.valid())
                    nextStep.add(new State(state.robots.buildCla(), ns.add(gen)));

                ns = state.resources.buildObs(rec);
                if (ns.valid())
                    nextStep.add(new State(state.robots.buildObs(), ns.add(gen)));

                ns = state.resources;
                if (ns.valid())
                    nextStep.add(new State(state.robots, ns.add(gen)));
            });

            int finalI = i;
            nextStep.sort((a, b) -> b.value(finalI) - a.value(finalI));

            mem = nextStep.subList(0, min(100000, nextStep.size()));
        }

        return mem.get(0).resources.geode;
    }


}
