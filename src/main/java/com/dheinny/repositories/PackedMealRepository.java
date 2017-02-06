package com.dheinny.repositories;

import com.dheinny.entities.PackedMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dmarques on 30/12/2016.
 */

@Repository
public interface PackedMealRepository extends JpaRepository<PackedMeal, Integer> {

    public List<PackedMeal> findByIngredients_nameOrderByCategoryAscNameDesc(String ingredientName);

    /**
     * Equivalente a
     * SELECT pm.*, i.* FROM packed_meals pm
     * INNER JOIN ing_pack ip ON pm.id = ip.id_pack
     * INNER JOIN ingredients i ON i.id = ip.id_ing
     * WHERE i.name=''
     * ORDER BY pm.category ASC, pm.name DESC
     * @param ingredientName
     * @return
     */
    @Query ("from PackedMeal pm join fetch pm.ingredients i " +
            "where i.name=:ingName order by pm.category asc, pm.name desc")
    public List<PackedMeal> findByIngredient(@Param("ingName") String ingredientName);


    @Modifying
    @Query ("Delete from PackedMeal pm where pm.id in :ids")
    public void deleteByIdList(@Param("ids") List<Integer> ids);

}
