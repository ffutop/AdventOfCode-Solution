package com.ffutop.aoc.y2020;

import java.util.*;

/**
 * @author fangfeng
 * @since 2020-12-21
 */
public class Day21 extends BasicDay {

    public static void main(String[] args) {
        Day21 day21 = new Day21();
        List<Food> foods = day21.readLists((input, lineNo) -> new Food(lineNo, input));
        day21.solve(foods);
    }

    private void solve(List<Food> foods) {
        Map<String, List<Integer>> allergenToFoodsMap = new HashMap<>();
        Set<String> allIngredients = new HashSet<>();
        for (Food food : foods) {
            for (String allergen : food.allergens) {
                allergenToFoodsMap.compute(allergen, (key, oldValue) -> {
                    List<Integer> allergenWhichFoodContains = oldValue == null ? new ArrayList<>() : oldValue;
                    allergenWhichFoodContains.add(food.id);
                    return allergenWhichFoodContains;
                });
            }
            allIngredients.addAll(Arrays.asList(food.ingredients));
        }

        System.out.println(Arrays.toString(allergenToFoodsMap.keySet().toArray()));

        Map<String, Set<String>> possibleMap = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : allergenToFoodsMap.entrySet()) {
            Set<String> sameIngredients = new HashSet<>(allIngredients);
            for (Integer index : entry.getValue()) {
                sameIngredients.retainAll(Arrays.asList(foods.get(index).ingredients));
            }
            possibleMap.put(entry.getKey(), sameIngredients);
        }

        Map<String, String> allergenToIngredientMap = new HashMap<>();
        Map<String, String> ingredientToAllergenMap = new HashMap<>();
        boolean changed = true;
        while (changed) {
            changed = false;
            for (Map.Entry<String, Set<String>> entry : possibleMap.entrySet()) {
                if (!allergenToIngredientMap.containsKey(entry.getKey()) && entry.getValue().size() == 1) {
                    allergenToIngredientMap.put(entry.getKey(), entry.getValue().stream().findFirst().get());
                    ingredientToAllergenMap.put(entry.getValue().stream().findFirst().get(), entry.getKey());
                    changed = true;
                }
            }
            for (Map.Entry<String, Set<String>> entry : possibleMap.entrySet()) {
                if (entry.getValue().size() != 1) {
                    entry.getValue().removeAll(allergenToIngredientMap.values());
                    if (entry.getValue().size() == 1) {
                        changed = true;
                    }
                }
            }
        }
        for (Map.Entry<String, String> entry : allergenToIngredientMap.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        Collection<String> ingredientsContainsAllergen = allergenToIngredientMap.values();
        System.out.println(Arrays.toString(ingredientsContainsAllergen.toArray()));
        int count = 0;
        for (Food food : foods) {
            for (String allergen : food.ingredients) {
                count += ingredientsContainsAllergen.contains(allergen) ? 0 : 1;
            }
        }
        System.out.println(count);


        List<String> ingredients = new ArrayList<>(ingredientsContainsAllergen);
        ingredients.sort((x, y) -> {
            return ingredientToAllergenMap.get(x).compareTo(ingredientToAllergenMap.get(y));
        });
        System.out.println(Arrays.toString(ingredients.toArray()).replaceAll(" ", ""));
    }

    private static class Food {
        int id;
        String[] ingredients;
        String[] allergens;

        public Food(int id, String input) {
            this.id = id;
            String allergens = input.substring(0, input.indexOf('('));
            this.ingredients = allergens.split(" ");

            String ingredients = input.substring(input.indexOf('(') + 1 + "contains ".length(), input.length()-1);
            this.allergens = ingredients.split(", ");
        }
    }
}
