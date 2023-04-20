package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.FoodCategory;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key.
 */
@Entity
@Table(name = "foods")
@Getter
@Setter
public class Foods implements Serializable{

    private static final long serialVersionUID = 1L;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private FoodCategory category;

    @Column
    private boolean highres;


}