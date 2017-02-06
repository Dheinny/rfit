package com.dheinny.repositories;

import com.dheinny.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by dmarques on 30/12/2016.
 */
@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    @Query ("Select i from Ingredient i where upper(trim(both from i.name)) = upper(trim(both from :name))")
    public Optional<Ingredient> findIngredientByNameIgnoreCase(@Param("name") String name);

    public Optional<Ingredient> findIngredientByTypeIgnoreCase(@Param("type") String type);


}
