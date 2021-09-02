/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author briannaschladweiler
 */
@Entity
@Data
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int siteId;

    @Column(name = "site_name", nullable = false)
    @NotBlank(message = "Site name is required and must be less than 100 characters.")
    @Size(max = 100, message = "Site name is required and must be less than 100 characters.")
    private String name;
}
