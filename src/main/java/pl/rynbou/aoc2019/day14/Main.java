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
        //OUTPUTS 431448, this is correct
        System.out.println("Part 1: " + worker.calculateIngredientsAmount().getOrDefault(ORE, 0L));

        worker.reset();
        worker.setProduct(FUEL, 2477865);
        //OUTPUTS 755605291766, this is correct
        System.out.println("Test: " + worker.calculateIngredientsAmount().getOrDefault(ORE, 0L));

        worker.reset();
        worker.setProduct(FUEL, 2477866);
        //hangs, gets stuck in an infinite loop
        System.out.println("Test: " + worker.calculateIngredientsAmount().getOrDefault(ORE, 0L));
    }
}
