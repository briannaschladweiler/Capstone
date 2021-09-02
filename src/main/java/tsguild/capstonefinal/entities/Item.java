/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author briannaschladweiler
 */
@Entity
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "item_name", nullable = false)
    @NotBlank(message = "Item Name is required.")
    @Size(max = 100, message = "Item Name must be less than 100 characters.")
    private String name;

    @Column(name = "cost", nullable = false)
    @NotNull(message = "Cost is required and must be a positive value.")
    private BigDecimal cost;

    @Column(name = "hours", nullable = false)
    @NotNull(message = "Hours is required and must be a positive value.")
    private BigDecimal hours;

    @Column(name = "date_made", nullable = false)
    @NotNull(message = "Listing Date is required, cannot be a future date, and must be after the Sold Date (if applicable).")
    private LocalDate date;
    
    @ManyToOne
    @JoinColumn(name = "sold_id")
    private Sold sold;
    
    @Column(name = "notes")
    private String notes;

}
