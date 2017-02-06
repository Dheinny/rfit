package com.dheinny.services;

import com.dheinny.entities.Ingredient;
import com.dheinny.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by dmarques on 01/01/2017.
 */
@Service
public class IngredientServices {
    @Autowired
    IngredientRepository ingredientRepository;

    public Ingredient createAndUptateIngredient(Ingredient ingredient) {
        if ((ingredient.getId() == null) && !(this.validateUniqueName(ingredient.getName()))) {
            throw (new RuntimeException());
        }
        return ingredientRepository.saveAndFlush(ingredient);
    }

    public void deleteIngredient(Ingredient ingredient) {
        ingredientRepository.delete(ingredient);
    }

    public void deleteIngredientById(Integer ingredientId) {
        ingredientRepository.delete(ingredientId);
    }

    public List<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient findIngredient(Integer id) { return ingredientRepository.findOne(id); }

    public List<Ingredient> findIngredient(Ingredient example){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withMatcher("name", GenericPropertyMatcher::contains)
                .withMatcher("type", GenericPropertyMatcher::endsWith)
                .withMatcher("type", GenericPropertyMatcher::startsWith);
        Example<Ingredient> ingredientExample = Example.of(example, matcher);
        List<Ingredient> ingredient = ingredientRepository.findAll(ingredientExample);

        return ingredient;
    }

    public boolean validateUniqueName(String name) {
        Optional<Ingredient> ingredient = ingredientRepository.findIngredientByNameIgnoreCase(name);
        return !(ingredient.isPresent());

    }
}
