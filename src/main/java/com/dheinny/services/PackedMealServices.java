package com.dheinny.services;

import com.dheinny.entities.Ingredient;
import com.dheinny.entities.PackedMeal;
import com.dheinny.exceptions.RfitDuplicatedRegisterException;
import com.dheinny.repositories.PackedMealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.util.List;
/**
 * Created by dmarques on 17/01/2017.
 */
@Service
public class PackedMealServices {
    @Autowired
    public PackedMealRepository packedMealRepository;

    public void deletePackedMeal(PackedMeal packedMeal) { packedMealRepository.delete(packedMeal); }

    public void deletePackedMealById(int id) { packedMealRepository.delete(id); }

    public void deletePackedMealByIds(List<Integer> ids) { packedMealRepository.deleteByIdList(ids); }

    public List<PackedMeal> findAllPackedMeals() { return packedMealRepository.findAll(); }

    public PackedMeal findPackedMeal(Integer id) { return packedMealRepository.findOne(id); }

    public List<PackedMeal> findPackedMealByIngredientName(Ingredient ingredient) {
        return findPackedMealByIngredientName(ingredient.getName());
    }

    public List<PackedMeal> findPackedMealByIngredientName(String ingredientName) {
        return packedMealRepository.findByIngredients_nameOrderByCategoryAscNameDesc(ingredientName);
    }

    public List<PackedMeal> findPackedMeal(PackedMeal example){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withMatcher("name", GenericPropertyMatcher::contains)
                .withMatcher("category", GenericPropertyMatcher::contains);
        Example<PackedMeal> packedMealExample = Example.of(example, matcher);
        List<PackedMeal> packedMealList = packedMealRepository.findAll(packedMealExample,
                new Sort(Sort.Direction.ASC, "category", "name"));
        return packedMealList;
    }

    public PackedMeal createAndUpdatePackedMeal(PackedMeal packedMeal) throws RfitDuplicatedRegisterException {
        if(!(validateUniqueName(packedMeal.getName()))){
            throw new RfitDuplicatedRegisterException();
        }

        return packedMealRepository.saveAndFlush(packedMeal);
    }

    private boolean validateUniqueName(String name) {
        PackedMeal packedMeal = new PackedMeal();
        packedMeal.setName(name);
        List<PackedMeal> result = findPackedMeal(packedMeal);
        return result.isEmpty();
    }
}
