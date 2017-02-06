package com.dheinny;

import com.dheinny.entities.Ingredient;
import com.dheinny.entities.PackedMeal;
import com.dheinny.repositories.IngredientRepository;
import com.dheinny.repositories.PackedMealRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RfitApplicationTests {
	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private PackedMealRepository packedMealRepository;

	@Before
	public void setUp() {
		ingredientRepository.deleteAll();

		Ingredient ing = ingredientRepository.save(Ingredient.builder()
				.name("Batata")
				.type("Legumes")
				.quantity(200)
				.calCount(120)
				.build());

		List<Ingredient> ingList = new ArrayList<Ingredient>();
		ingList.add(ing);
		packedMealRepository.save(PackedMeal.builder()
				.name("Carne")
				.category("Carne")
				.isAvailable(true)
				.ingredients(ingList)
		.build());
	}

	@Test
	public void testFindAll() throws Exception {
		List<Ingredient> ingList = ingredientRepository.findAll();//Ctrl + Alt + V -> nomeia de ingList...
		ingList.forEach(System.out::println);

		List<PackedMeal> packList = packedMealRepository.findAll();
		packList.forEach(System.out::println);
	}
}
