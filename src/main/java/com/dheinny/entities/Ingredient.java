package com.dheinny.entities;

import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.List;

/**
 * Created by dmarques on 30/12/2016.
 */
@Entity
@Table(name = "ingredients")
@Data
@SequenceGenerator(name = "ingredientsSeq", allocationSize=10, sequenceName = "ingredients_seq")
@ToString (exclude = "packedmeals")
@EqualsAndHashCode (exclude = "packedmeals")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="ingredientsSeq")
    private Integer id;
    private String name;
    private String type;
    @Column(name = "qtt_per_portion")
    private Integer quantity;
    @Column(name = "cal_count")
    private Integer calCount;

    @ManyToMany (mappedBy = "ingredients")
    private List<PackedMeal> packedmeals;
}
