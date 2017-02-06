package com.dheinny.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmarques on 30/12/2016.
 */
@Entity
@Table (name = "packed_meals")
@Data
@SequenceGenerator(name="packedMealSeq", allocationSize = 10, sequenceName = "packed_meals_seq")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "ingredients")
@EqualsAndHashCode (exclude = "ingredients")
public class PackedMeal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "packedMealSeq")
    private Integer id;
    private String name;
    private String category;
    @Column (name = "is_available")
    private boolean isAvailable = false;
    private Float price;
    @Column (name = "price_off")
    private Float priceOff;

    @ManyToMany
    @JoinTable(
            name = "ing_pack",
            joinColumns = @JoinColumn(
                    name = "id_ing",
                    referencedColumnName = "id"),

            inverseJoinColumns = @JoinColumn(
                    name = "id_pack",
                    referencedColumnName = "id" )
            )
    private List<Ingredient> ingredients;

    @PrePersist
    public void prePersist() {
        if (this.ingredients != null) {
           this.ingredients.stream().filter(ing -> ing.getPackedmeals() == null)
                   .forEach(ing -> ing.setPackedmeals(new ArrayList<>()));
           this.ingredients.forEach(ing -> ing.getPackedmeals().add(this));
        }
    }
}
