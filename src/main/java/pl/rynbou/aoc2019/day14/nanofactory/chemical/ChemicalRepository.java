package pl.rynbou.aoc2019.day14.nanofactory.chemical;

import java.util.HashMap;
import java.util.Map;

public class ChemicalRepository {

    private final Map<String, Chemical> chemicalMap = new HashMap<>();

    public Chemical getChemical(String name) {
        if (!chemicalMap.containsKey(name)) {
            chemicalMap.put(name, new Chemical(name));
        }

        return chemicalMap.get(name);
    }
}
