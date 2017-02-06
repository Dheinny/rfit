package com.dheinny.controllers;

import com.dheinny.entities.Ingredient;
import com.dheinny.services.IngredientServices;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.ws.Response;
import java.util.List;

/**
 * Created by dmarques on 18/01/2017.
 */
@RestController
//@RequestMapping("/{userId}/bookmarks") OPTIONAL
public class IngredientController {
    @Autowired
    IngredientServices ingredientServices; //Services  which will do all data retrieval/manipulation work

    //Retrieve all Ingredients
    @RequestMapping(value = "/ingredient/", method = RequestMethod.GET)
    public ResponseEntity<List<Ingredient>> listAllIngredient () {
        List<Ingredient> ingredientList = ingredientServices.findAllIngredients();
        if(ingredientList.isEmpty()) {
            System.out.println("No ingredient was found");
            return new ResponseEntity<List<Ingredient>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ingredient>>(ingredientList, HttpStatus.OK);
    }

    //Retrieve a singleIngredient
    @RequestMapping (value = "/ingredient/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ingredient> getIngredient(@PathVariable("id") int id) {
        Ingredient ingredient = ingredientServices.findIngredient(id);

        /*Ingredient ingredient = new Ingredient();
        ingredient.setId(id);
        ingredient.setName("Joao");
        ingredient.setCalCount(900);
        ingredient.setQuantity(400);
        ingredient.setType("Legumes");
        */
        System.out.println("Looking for Ingredient with id: " + id);
        if (ingredient == null){
            System.out.println("Ingredient with id: " + id + " not found");
            return new ResponseEntity<Ingredient>(HttpStatus.NO_CONTENT);
        }

        System.out.println( "passando aqui" + ingredient.getId());
        return new ResponseEntity<Ingredient>(ingredient,HttpStatus.OK);

    }

    //Create a new Ingredient
    @RequestMapping (value = "/ingredient/", method = RequestMethod.POST)
    public ResponseEntity<Void> createIngredient(@RequestBody Ingredient ingredient, UriComponentsBuilder ucBuilder){
        if(!(ingredientServices.validateUniqueName(ingredient.getName()))) {
            System.out.println("A Ingredient with name:" + ingredient.getName() + " already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        ingredientServices.createAndUptateIngredient(ingredient);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/ingredient/{id}").buildAndExpand(ingredient.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    //Update a existing Ingredient
    @RequestMapping (value = "/ingredient/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable("id") Integer id, @RequestBody Ingredient ingredient) {
        Ingredient currentIngredient = ingredientServices.findIngredient(id);

        if(currentIngredient == null) {
            System.out.println("Packed Meal with id "+ ingredient.getId() + " was not found");
            return new ResponseEntity<Ingredient>(HttpStatus.NOT_FOUND);
        }

        //if don't work, setting all properties of currentIngredient and update it
        ingredient.setId(currentIngredient.getId());

        ingredientServices.createAndUptateIngredient(ingredient);
        return new ResponseEntity<Ingredient>(ingredient, HttpStatus.OK);
    }

    //Delete a Ingredient
    @RequestMapping (value = "/ingredient/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable("id") Integer id) {
        Ingredient ingredientToDelete = ingredientServices.findIngredient(id);
        if(ingredientToDelete == null) {
            System.out.println("Ingredient with id " + id + "not found");
            return new ResponseEntity<Ingredient> (HttpStatus.NOT_FOUND);
        }

        ingredientServices.deleteIngredientById(id);
        return new ResponseEntity<Ingredient>(HttpStatus.NO_CONTENT);
    }

    //Delete a Ingredient
    @RequestMapping (value = "/ingredient/", method = RequestMethod.DELETE)
    public  ResponseEntity<Ingredient> deleteIngredient(@RequestBody Ingredient ingredient) {
        List<Ingredient> ingredientsToDelete = ingredientServices.findIngredient(ingredient);
        if(ingredientsToDelete.isEmpty()) {
            System.out.println("No ingredient to be deleted");
            return new ResponseEntity<Ingredient>(HttpStatus.NOT_FOUND);
        }

        ingredientsToDelete.stream().forEach(ingredientToDelete -> ingredientServices.deleteIngredient(ingredientToDelete));
        return new ResponseEntity<Ingredient>(HttpStatus.OK);
    }
}
