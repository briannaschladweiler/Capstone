/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static java.util.concurrent.TimeUnit.DAYS;
import org.springframework.stereotype.Service;
import tsguild.capstonefinal.data.CategoryRepository;
import tsguild.capstonefinal.data.ItemRepository;
import tsguild.capstonefinal.data.SaleRepository;
import tsguild.capstonefinal.data.SiteRepository;
import tsguild.capstonefinal.data.SoldRepository;
import tsguild.capstonefinal.entities.Category;
import tsguild.capstonefinal.entities.Item;
import tsguild.capstonefinal.entities.Sale;
import tsguild.capstonefinal.entities.Site;
import tsguild.capstonefinal.entities.Sold;

/**
 *
 * @author briannaschladweiler
 */
@Service
public class LookupService {

    private final SiteRepository siteRepo;
    private final CategoryRepository categoryRepo;
    private final ItemRepository itemRepo;
    private final SaleRepository saleRepo;
    private final SoldRepository soldRepo;

    public LookupService(SiteRepository siteRepo,
            CategoryRepository categoryRepo,
            ItemRepository itemRepo,
            SaleRepository saleRepo,
            SoldRepository soldRepo) {
        this.siteRepo = siteRepo;
        this.categoryRepo = categoryRepo;
        this.itemRepo = itemRepo;
        this.saleRepo = saleRepo;
        this.soldRepo = soldRepo;
    }

    //Site Methods:
    public List<Site> findAllSites() {
        return siteRepo.findAll();
    }

    public Site findSiteById(int siteId) {
        return siteRepo.findById(siteId)
                .orElse(null);
    }

    //admin Site method:
    public Site addUpdateSite(Site s) {
        return siteRepo.save(s);
    }

    //admin Site method:
    public void deleteSiteById(int id) {
        siteRepo.deleteById(id);
    }

    //Category Methods:
    public List<Category> findAllCategories() {
        return categoryRepo.findAll();
    }

    public Category findCategoryById(int id) {
        return categoryRepo.findById(id)
                .orElse(null);
    }

    //admin Category method:
    public Category addUpdateCategory(Category c) {
        return categoryRepo.save(c);
    }

    //admin Category method:
    public void deleteCategoryById(int id) {
        categoryRepo.deleteById(id);
    }

    //Sold Methods:
    public List<Sold> findAllSold() {
        return soldRepo.findAll();
    }

    public Sold findSoldById(int id) {
        return soldRepo.findById(id)
                .orElse(null);
    }

    //Item Methods:
    public List<Item> findAllItems() {
        return itemRepo.findAll();
    }

    public Item findItemById(int id) {
        return itemRepo.findById(id).orElse(null);
    }

    public Item addUpdateItem(Item i) {
        return itemRepo.save(i);
    }

    public void deleteItemById(int id) {
        itemRepo.deleteById(id);
    }

    public List<Item> findBySold(Sold sold) {
        return itemRepo.findBySold(sold);
    }

    public List<Item> findByCategory(Category category) {
        return itemRepo.findByCategory(category);
    }

    //Sale Methods:
    public List<Sale> findAllSales() {
        return saleRepo.findAll();
    }

    public Sale findSaleById(int id) {
        return saleRepo.findById(id).orElse(null);
    }

    public Sale findByItem(Item item) {
        return saleRepo.findByItem(item);
    }

    public Sale addUpdateSale(Sale s) {
        return saleRepo.save(s);
    }

    public void deleteSale(Sale s) {
        saleRepo.delete(s);
    }

    public List<Sale> findBySite(Site site) {
        return saleRepo.findBySite(site);
    }

    //Graph Methods:
    //to find number of items sold in a category
    public int findNumCategorySold(Category category) {
        List<Item> itemInCat = itemRepo.findByCategory(category);
        List<Item> itemsSoldInCat = new ArrayList<>();
        for (Item i : itemInCat) {
            if (i.getSold() == findSoldById(2)) {
                itemsSoldInCat.add(i);
            }
        }

        int number = itemsSoldInCat.size();

        return number;
    }

    //to find number of items available in a category
    public int findNumCategoryAvailable(Category category) {
        List<Item> itemInCat = itemRepo.findByCategory(category);
        List<Item> itemsAvailableInCat = new ArrayList<>();
        for (Item i : itemInCat) {
            if (i.getSold() == findSoldById(1)) {
                itemsAvailableInCat.add(i);
            }
        }

        int number = itemsAvailableInCat.size();

        return number;
    }

    //to find total in sales in a category
    public BigDecimal findSalesByCat(Category category) {
        List<Sale> allSales = saleRepo.findAll();
        List<Item> itemsInCat = itemRepo.findByCategory(category);
        BigDecimal total = new BigDecimal("0");
        for (Sale s : allSales) {
            for (Item i : itemsInCat) {
                if (s.getItem() == i) {
                    total = total.add(s.getPrice());
                }
            }
        }
        return total;
    }

    //to find average cost per item in category
    public BigDecimal findAvgCostInCat(Category category) {
        List<Item> itemInCat = itemRepo.findByCategory(category);
        BigDecimal total = new BigDecimal("0");
        for (Item i : itemInCat) {
            total = total.add(i.getCost());
        }
        int numOfItemsInt = itemInCat.size();
        String numOfItemsString = Integer.toString(numOfItemsInt);
        BigDecimal numOfItems = new BigDecimal(numOfItemsString).setScale(2, RoundingMode.HALF_UP);
        BigDecimal avgCost = total.divide(numOfItems, 2, RoundingMode.HALF_UP);
        return avgCost;
    }

    //to find avg sale price in a category
    public BigDecimal findAvgSaleByCat(Category category) {
        List<Sale> allSales = saleRepo.findAll();
        List<Item> itemsInCat = itemRepo.findByCategory(category);
        BigDecimal total = new BigDecimal("0");
        int counter = 0;
        for (Sale s : allSales) {
            for (Item i : itemsInCat) {
                if (s.getItem() == i) {
                    total = total.add(s.getPrice());
                    counter = counter + 1;
                }
            }
        }
        String counterString = Integer.toString(counter);
        BigDecimal numOfItems = new BigDecimal(counterString).setScale(2, RoundingMode.HALF_UP);
        BigDecimal avgPrice = total.divide(numOfItems, 2, RoundingMode.HALF_UP);
        return avgPrice;
    }

    //to find avg days on market in a category
    public long findAvgDaysByCat(Category category) {
        List<Sale> allSales = saleRepo.findAll();
        List<Item> itemsInCat = itemRepo.findByCategory(category);
        long total = 0;
        int counter = 0;
        for (Sale s : allSales) {
            for (Item i : itemsInCat) {
                if (s.getItem() == i) {
                    long daysOnMarket = ChronoUnit.DAYS.between(i.getDate(), s.getDate());
                    total = total + daysOnMarket;
                    counter = counter + 1;
                }
            }
        }
        long avgDays = total / counter;

        return avgDays;
    }

    //to find how many sold by site
    public int findNumSoldBySite(int id) {
        List<Sale> allSales = saleRepo.findAll();
        Site site = siteRepo.findById(id).orElse(null);
        int counter = 0;
        for (Sale s : allSales) {
            if (s.getSite() == site) {
                counter = counter + 1;
            }
        }

        return counter;
    }

    //to find sales by site
    public BigDecimal findSalesBySite(int id) {
        List<Sale> allSales = saleRepo.findAll();
        Site site = siteRepo.findById(id).orElse(null);
        int counter = 0;
        BigDecimal total = new BigDecimal("0");
        for (Sale s : allSales) {
            if (s.getSite() == site) {
                counter = counter + 1;
                total = total.add(s.getPrice());
            }
        }
        return total;
    }

}
