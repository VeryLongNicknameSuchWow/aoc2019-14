package pl.rynbou.aoc2019.day14.nanofactory;

import pl.rynbou.aoc2019.day14.nanofactory.chemical.Chemical;
import pl.rynbou.aoc2019.day14.nanofactory.chemical.ChemicalRepository;
import pl.rynbou.aoc2019.day14.nanofactory.reaction.Reaction;
import pl.rynbou.aoc2019.day14.nanofactory.reaction.ReactionRepository;

import java.util.*;

public class Nanofactory {

    private final ChemicalRepository chemicalRepository = new ChemicalRepository();
    private final ReactionRepository reactionRepository = new ReactionRepository();

    public Nanofactory(List<String> reactions) {
        for (String reactionString : reactions) {
            reactionRepository.addReaction(new Reaction(reactionString, chemicalRepository));
        }
    }

    public Worker getWorker() {
        return new Worker();
    }

    public ChemicalRepository getChemicalRepository() {
        return chemicalRepository;
    }

    public class Worker {

        //these two hashmaps never change, they are here so that i can reset the worker to its initial state
        //the final products, usually just FUEL with different amounts
        private final Map<Chemical, Long> products = new HashMap<>();
        //base ingredients that are provided and do not need to be crafted, usually just ORE
        private final Set<Chemical> baseIngredients = new HashSet<>();

        //chemicals to be made
        private final Map<Chemical, Long> toProduce = new HashMap<>();
        //leftover chemicals
        private final Map<Chemical, Long> leftovers = new HashMap<>();

        public void reset() {
            toProduce.clear();
            leftovers.clear();
        }

        public void setProduct(Chemical chemical, long amount) {
            this.products.put(chemical, amount);
        }

        public void addBaseIngredientChemical(Chemical chemical) {
            this.baseIngredients.add(chemical);
        }

        public Map<Chemical, Long> calculateIngredientsAmount() {
            toProduce.putAll(products);

            while (true) {
                //find some chemical that is not a base ingredient
                Chemical current = toProduce.keySet().stream()
                        .filter(chemical -> !baseIngredients.contains(chemical))
                        .findAny()
                        .orElse(null);

                //if there are none the work is done
                if (current == null) {
                    break;
                }

                makeChemical(current);
            }

            //this will return when there are only base ingredients in toProduce HashMap,
            //calculating how much of each should be supplied
            return toProduce;
        }

        public void makeChemical(Chemical chemical) {
            //do not try to craft base ingredients
            if (baseIngredients.contains(chemical)) {
                return;
            }

            long amountToProduce = toProduce.getOrDefault(chemical, 0L);
            long leftover = leftovers.getOrDefault(chemical, 0L);

            //if there are more leftovers than amount to be crafted
            //there is no need to craft any
            if (leftover > amountToProduce) {
                toProduce.remove(chemical);
                leftovers.put(chemical, leftover - amountToProduce);
                return;
            } else if (leftover == amountToProduce) {
                toProduce.remove(chemical);
                leftovers.remove(chemical);
                return;
            }

            //there may be some leftovers, but it's not enough
            //less chemicals are to be made
            amountToProduce -= leftover;
            leftovers.remove(chemical);

            Reaction reaction = reactionRepository.findReaction(chemical);
            long reactionProductAmount = reaction.getProducts().get(chemical);
            //find the amount of iterations, round that up
            int reactionIterations = (int) Math.ceil((double) amountToProduce / reactionProductAmount);

            reaction.getIngredients().forEach((ingredient, amountNeeded) -> {
                amountNeeded *= reactionIterations;

                //schedule all reaction ingredients for production
                toProduce.put(ingredient, toProduce.getOrDefault(ingredient, 0L) + amountNeeded);
            });

            long finalAmountToProduce = amountToProduce;
            //reactions only have one product, but I had a hashmap there for future-proofing
            //this should only run once
            reaction.getProducts().forEach((product, amountProduced) -> {
                amountProduced *= reactionIterations;

                //with AOC inputs this should never be false as there would always be only one product
                if (product == chemical) {
                    if (amountProduced > finalAmountToProduce) {
                        //produced more than necessary, adding to leftovers
                        toProduce.remove(product);
                        leftovers.put(product, leftovers.getOrDefault(product, 0L) + amountProduced - finalAmountToProduce);
                    } else if (amountProduced == finalAmountToProduce) {
                        //produced the exact amount needed, no leftovers
                        toProduce.remove(product);
                    }
                }
            });
        }
    }
}
