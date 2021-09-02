/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.data;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import tsguild.capstonefinal.entities.Category;
import tsguild.capstonefinal.entities.Item;
import tsguild.capstonefinal.entities.Site;
import tsguild.capstonefinal.entities.Sold;

/**
 *
 * @author briannaschladweiler
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository repo;

    @Autowired
    private SoldRepository soldRepo;

    public ItemRepositoryTest() {
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
        assertTrue(repo.findAll().size() > 0);
    }

    @Test
    public void testFindBySold() {
        Sold sold = soldRepo.findAll().get(1);

        assertEquals(89, repo.findBySold(sold).size());

    }

    @Test
    public void testFindById() {

        Item item = repo.findById(1)
                .orElse(null);

        assertNotNull(item);
        System.out.println(item);
    }

    @Test
    public void testCreate() {

        Item item = makeTestItem();

        item = repo.save(item);

        int id = item.getItemId();

        Item actual = repo.findById(id)
                .orElse(null);

        assertNotNull(actual);

        repo.deleteById(id);
    }

    @Test
    public void testUpdate() {

        Item item = makeTestItem();
        item = repo.save(item);

        Item toUpdate = makeTestItem();
        toUpdate.setName("Snow");

        String test = "Snow";

        toUpdate = repo.save(toUpdate);

        int id = toUpdate.getItemId();

        Item actual = repo.findById(id).orElse(null);
        assertNotNull(actual);
        assertEquals(test, actual.getName());

        repo.deleteById(id);
    }

    private Item makeTestItem() {
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Art");

        Site site = new Site();
        site.setSiteId(2);
        site.setName("Etsy");

        String costString = "4.99";
        BigDecimal cost = new BigDecimal(costString);

        String hoursString = "3";
        BigDecimal hours = new BigDecimal(hoursString);

        Item item = new Item();
        int id = item.getItemId();
        item.setItemId(id);
        item.setCategory(category);
        item.setName("Painting");
        item.setCost(cost);
        item.setHours(hours);
        item.setDate(LocalDate.of(2016, 4, 4));

        Sold sold = soldRepo.findAll().get(0);
        item.setSold(sold);

        return item;
    }

}
