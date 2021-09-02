/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tsguild.capstonefinal.entities.Item;
import tsguild.capstonefinal.entities.Sale;
import tsguild.capstonefinal.entities.Site;

/**
 *
 * @author briannaschladweiler
 */
public interface SaleRepository
        extends JpaRepository<Sale, Integer> {
    
    Sale findByItem(Item item);
    
    List<Sale> findBySite(Site site);
    
}
