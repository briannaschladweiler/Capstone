/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.data;

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
import tsguild.capstonefinal.entities.Site;

/**
 *
 * @author briannaschladweiler
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SiteRepositoryTest {

    @Autowired
    private SiteRepository repo;

    public SiteRepositoryTest() {
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
        List<Site> sites = repo.findAll();
        assertEquals(5, sites.size());
    }

    @Test
    public void testFindById() {
        assertEquals("Amazon", repo.findById(1).orElse(null).getName());
        assertEquals("eBay", repo.findById(2).orElse(null).getName());
        assertEquals("Society6", repo.findById(5).orElse(null).getName());
    }

}
