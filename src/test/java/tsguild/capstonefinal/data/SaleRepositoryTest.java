/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tsguild.capstonefinal.entities.Item;
import tsguild.capstonefinal.entities.Sale;
import tsguild.capstonefinal.entities.Site;

/**
 *
 * @author briannaschladweiler
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SaleRepositoryTest {

    @Autowired
    private ItemRepository itemRepo;

    @Autowired
    private SaleRepository saleRepo;

    @Autowired
    private SiteRepository siteRepo;

    public SaleRepositoryTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testFindAll() {
        List<Sale> all = saleRepo.findAll();
        assertEquals(89, all.size());
        for (Sale s : all) {
            System.out.println(s);
        }
    }
    
    @Test
    public void testFindByItem() {
        Item item = itemRepo.findAll().get(1);
        Sale sale = saleRepo.findByItem(item);
        int id = sale.getSaleId();
        assertEquals(1, id);
    }

    @Test
    public void testCreate() {

        Item item = itemRepo.findAll().get(0);
        Site site = siteRepo.findAll().get(0);

        Sale sale = new Sale();
        int id = sale.getSaleId();
        sale.setSaleId(id);
        sale.setItem(item);

        String priceString = "21.99";
        BigDecimal price = new BigDecimal(priceString);

        sale.setPrice(price);
        sale.setDate(LocalDate.of(2020, 1, 1));
        sale.setSite(site);

        Sale actual = saleRepo.save(sale);
        assertTrue(actual.getSaleId() > 0);

        actual = saleRepo.findById(actual.getSaleId())
                .orElse(null);

        assertNotNull(actual);

        System.out.println(actual);

        saleRepo.deleteById(actual.getSaleId());
    }

}
