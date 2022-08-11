package fr.skah.skmdl.api.commons.collection;

/*
 *  * @Created on 23/05/2022
 *  * @Project SKMDL
 *  * @Author Jimmy  / SKAH#7513
 */

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomMap<E> {

    private NavigableMap<Double, Object> map = new TreeMap<>();
    private Random random;
    private double total = 0;

    public RandomMap() {
        this(new Random());
    }

    public RandomMap(Random random) {
        this.random = random;
    }

    public RandomMap<E> add(double weight, Object object) {
        if (weight <= 0)
            return this;
        total += weight;
        map.put(total, object);
        return this;
    }

    public Object next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }

    public NavigableMap<Double, Object> getMap() {
        return map;
    }
}