/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tsguild.capstonefinal.entities.Category;
import tsguild.capstonefinal.entities.Item;
import tsguild.capstonefinal.entities.Sold;

/**
 *
 * @author briannaschladweiler
 */
@Repository
public interface ItemRepository
        extends JpaRepository<Item, Integer> {
    
    List<Item> findBySold(Sold sold);
    
    List<Item> findByCategory(Category category);

}