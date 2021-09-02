/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.data;

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

/**
 *
 * @author briannaschladweiler
 */
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class SoldRepositoryTest {
    
    @Autowired
    private SoldRepository repo;
    
    public SoldRepositoryTest() {
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
        assertEquals(2, repo.findAll().size());
    }

    @Test
    public void testFindById() {
        assertEquals("Available", repo.findById(1).orElse(null).getName());
        assertEquals("Sold", repo.findById(2).orElse(null).getName());
    }
    
}
