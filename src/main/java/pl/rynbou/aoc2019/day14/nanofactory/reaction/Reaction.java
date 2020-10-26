package pl.rynbou.aoc2019.day14.nanofactory.reaction;

import pl.rynbou.aoc2019.day14.nanofactory.chemical.Chemical;
import pl.rynbou.aoc2019.day14.nanofactory.chemical.ChemicalRepository;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Reaction {

    private final Map<Chemical, Long> ingredients = new HashMap<>();
    private final Map<Chemical, Long> products = new HashMap<>();

    public Reaction(String reaction, ChemicalRepository chemicalRepository) {
        String[] split = reaction.split(" => ");

        for (String ingredientString : split[0].split(", ")) {
            String[] ingredientParts = ingredientString.split(" ");

            long ingredientAmount = Integer.parseInt(ingredientParts[0]);
            Chemical ingredient = chemicalRepository.getChemical(ingredientParts[1].strip());

            this.ingredients.put(ingredient, ingredientAmount);
        }

        for (String productString : split[1].split(", ")) {
            String[] productParts = productString.split(" ");

            long productAmount = Integer.parseInt(productParts[0]);
            Chemical product = chemicalRepository.getChemical(productParts[1].strip());

            this.products.put(product, productAmount);
        }
    }

    public boolean containsProduct(Chemical chemical) {
        return this.products.containsKey(chemical);
    }

    public Map<Chemical, Long> getIngredients() {
        return Collections.unmodifiableMap(ingredients);
    }

    public Map<Chemical, Long> getProducts() {
        return Collections.unmodifiableMap(products);
    }
}
