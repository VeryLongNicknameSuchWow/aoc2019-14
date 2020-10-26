package pl.rynbou.aoc2019.day14;

import pl.rynbou.aoc2019.day14.nanofactory.Nanofactory;
import pl.rynbou.aoc2019.day14.nanofactory.chemical.Chemical;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        Nanofactory nanofactory = new Nanofactory(Files.readAllLines(Paths.get("src/main/resources/input.txt")));
        Nanofactory.Worker worker = nanofactory.getWorker();

        Chemical FUEL = nanofactory.getChemicalRepository().getChemical("FUEL");
        Chemical ORE = nanofactory.getChemicalRepository().getChemical("ORE");
        worker.addBaseIngredientChemical(ORE);

        worker.setProduct(FUEL, 1);
        long part1 = worker.calculateIngredientsAmount().getOrDefault(ORE, 0L);
        System.out.println("Part 1: " + part1);

        System.out.println("Part 2: " + part2(worker, FUEL, ORE, 1000000000000L, part1));
    }

    public static long part2(Nanofactory.Worker worker, Chemical fuel, Chemical ore, long target, long perOne) {
        return part2(worker, fuel, ore, target, target / perOne, (target / perOne) * 2);
    }

    public static long part2(Nanofactory.Worker worker, Chemical fuel, Chemical ore, long target, long min, long max) {
        long mid = (min + max) / 2;

        worker.reset();
        worker.setProduct(fuel, mid);
        long producedOre = worker.calculateIngredientsAmount().getOrDefault(ore, 0L);

        if (max - min <= 1  || producedOre == target) {
            return mid;
        } else if (producedOre < target) {
            return part2(worker, fuel, ore, target, mid, max);
        } else {
            return part2(worker, fuel, ore, target, min, mid);
        }
    }
}
