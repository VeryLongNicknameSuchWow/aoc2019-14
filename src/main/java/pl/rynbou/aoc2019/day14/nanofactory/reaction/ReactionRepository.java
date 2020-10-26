package pl.rynbou.aoc2019.day14.nanofactory.reaction;

import pl.rynbou.aoc2019.day14.nanofactory.chemical.Chemical;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ReactionRepository {

    private final Set<Reaction> reactions = new HashSet<>();

    public void addReaction(Reaction reaction) {
        reactions.add(reaction);
    }

    public Set<Reaction> getReactions() {
        return Collections.unmodifiableSet(reactions);
    }

    public Reaction findReaction(Chemical product) {
        return reactions.stream().filter(reaction -> reaction.containsProduct(product)).findAny().orElse(null);
    }
}
