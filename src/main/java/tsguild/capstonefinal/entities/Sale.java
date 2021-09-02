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
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author briannaschladweiler
 */
@Entity
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int saleId;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price is required and must be a positive value.")
    private BigDecimal price;

    @Column(name = "date_sold", nullable = false)
    @NotNull(message = "Sold Date is required. Cannot be a future date and must be after the item's Listing Date.")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

}
