package com.dheinny.controllers;

import com.dheinny.entities.Ingredient;
import com.dheinny.entities.PackedMeal;
import com.dheinny.exceptions.RfitDuplicatedRegisterException;
import com.dheinny.services.PackedMealServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Created by dmarques on 19/01/2017.
 */
@RestController
public class PackedMealController {
    @Autowired
    PackedMealServices packedMealServices;

    @RequestMapping (value = "/packedmeal/", method = RequestMethod.GET)
    public ResponseEntity<List<PackedMeal>> listAllPackedMeal () {
        System.out.println("Getting all Packed Meals");
        List<PackedMeal> packedMeals = packedMealServices.findAllPackedMeals();
        if(packedMeals.isEmpty()) {
            System.out.println("No Packed Meal was found");
            return new ResponseEntity<List<PackedMeal>>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<List<PackedMeal>>(packedMeals, HttpStatus.OK);
    }

    @RequestMapping (value = "/packedmeal/{id}", method = RequestMethod.GET)
    public ResponseEntity<PackedMeal> getPackedMeal(@PathVariable("id") Integer id) {
        System.out.println("Getting Packed Meal with id = " + id);
        PackedMeal packedMeal = packedMealServices.findPackedMeal(id);
        if(packedMeal == null) {
            System.out.println("Packed Meal with id: " + id + " not found");
            return new ResponseEntity<PackedMeal>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<PackedMeal>(packedMeal, HttpStatus.OK);
    }

    @RequestMapping (value = "/packedmeal/example/{name}", method = RequestMethod.GET)
    //public ResponseEntity<PackedMeal> getPackedMeal(@RequestBody PackedMeal packedMeal) {
    public ResponseEntity<PackedMeal> getPackedMeal(@PathVariable("name") String name) {
        System.out.println("Getting Packed Meal by Example");
        PackedMeal packedMeal = new PackedMeal();
        packedMeal.setName(name);

        List<PackedMeal> packedMealList = packedMealServices.findPackedMeal(packedMeal);
        if(packedMealList.isEmpty()) {
            System.out.println("Filtro de buscar marmitas por example NAO estah funcionando");
            return new ResponseEntity<PackedMeal>(HttpStatus.NOT_FOUND);
        }

        System.out.println("Filtro de buscar marmitas por example ESTA funcionando");
        return new ResponseEntity<PackedMeal>(packedMeal, HttpStatus.OK);
    }

    @RequestMapping (value = "/packedmealCreate/", method = RequestMethod.POST)
    public ResponseEntity<Void> createPackedMeal(@RequestBody PackedMeal packedMeal, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating new Packed Meal");
        try {
            packedMealServices.createAndUpdatePackedMeal(packedMeal);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(ucBuilder.path("/packedmeal/").build().toUri());
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CREATED);
        } catch (RfitDuplicatedRegisterException e) {
            System.out.println("A Packed Meal with name:" + packedMeal.getName() + " already exists");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(ucBuilder.path("/packedmeal/").build().toUri());
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CONFLICT);
        }
    }

    @RequestMapping (value = "/packedmealUptade/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updatePackedMeal (@PathVariable("id") Integer id, @RequestBody PackedMeal packedMeal, UriComponentsBuilder ucBuilder) {
        System.out.println("Updating Packed Meal with id = " + id);
        PackedMeal packedMealToUpdate = packedMealServices.findPackedMeal(id);
        if (packedMealToUpdate == null) {
            System.out.println("Packed Meal with id " + packedMeal.getId() + " was not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        try {
            packedMealServices.createAndUpdatePackedMeal(packedMeal);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(ucBuilder.path("/packedmeal/").build().toUri());
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.OK);
        } catch (RfitDuplicatedRegisterException e) {
            System.out.println("A Packed Meal with name:" + packedMeal.getName() + " already exists");

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(ucBuilder.path("/packedmeal/").build().toUri());
            return new ResponseEntity<Void>(httpHeaders, HttpStatus.CONFLICT);
        }
    }

    @RequestMapping (value = "/packedmealDelete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePackedMeal (@PathVariable("id") Integer id) {
        System.out.println("Deleting Packed Meal with id = " + id);
        PackedMeal packedMeal = packedMealServices.findPackedMeal(id);
        if(packedMeal == null) {
            System.out.println("Packed Meal with id " + id + "not found");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        packedMealServices.deletePackedMealById(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping (value = "/packedmealDelete/", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePackedMeals(@RequestBody PackedMeal packedMeal) {
        System.out.println("Deleting Packed Meals");
        List<PackedMeal> packedMealList = packedMealServices.findPackedMeal(packedMeal);
        if (packedMealList.isEmpty()) {
            System.out.println("No PackMeal to be deleted");
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }

        packedMealList.stream().forEach(packedMealToDelete -> packedMealServices.deletePackedMeal(packedMealToDelete));
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
