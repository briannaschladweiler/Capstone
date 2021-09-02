/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsguild.capstonefinal.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tsguild.capstonefinal.data.RoleDao;
import tsguild.capstonefinal.data.UserDao;
import tsguild.capstonefinal.entities.Category;
import tsguild.capstonefinal.entities.Item;
import tsguild.capstonefinal.entities.Role;
import tsguild.capstonefinal.entities.Sale;
import tsguild.capstonefinal.entities.Site;
import tsguild.capstonefinal.entities.Sold;
import tsguild.capstonefinal.entities.User;
import tsguild.capstonefinal.service.LookupService;

/**
 *
 * @author briannaschladweiler
 */
@Controller
public class MainController {

    @Autowired
    LookupService service;
    
    @Autowired
    UserDao users;
    
    @Autowired
    RoleDao roles;
    
    @Autowired
    PasswordEncoder encoder;

    Set<ConstraintViolation<Item>> violations = new HashSet<>();
    Set<ConstraintViolation<Sale>> violationsSale = new HashSet<>();
    Set<ConstraintViolation<Category>> violationsCategory = new HashSet<>();
    Set<ConstraintViolation<Site>> violationsSite = new HashSet<>();

    @GetMapping("/index")
    public String displayItems(Model model) {
        List<Item> items = service.findAllItems();
        Collections.sort(items, new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                return i2.getDate().compareTo(i1.getDate());
            }
        });
        model.addAttribute("items", items);
        
        //Num of Sales By Category
            //Num sold in Art
            int artSold = service.findNumCategorySold(service.findCategoryById(1));
            model.addAttribute("artSold", artSold);
        
            //Num sold in Clothing
            int clothingSold = service.findNumCategorySold(service.findCategoryById(2));
            model.addAttribute("clothingSold", clothingSold);
        
            //Num sold in Home
            int homeSold = service.findNumCategorySold(service.findCategoryById(3));
            model.addAttribute("homeSold", homeSold);
        
            //Num sold in Jewelry
            int jewelrySold = service.findNumCategorySold(service.findCategoryById(4));
            model.addAttribute("jewelrySold", jewelrySold);
        
        //Num of Available by Category
            //Num avail in Art
            int artAvail = service.findNumCategoryAvailable(service.findCategoryById(1));
            model.addAttribute("artAvail", artAvail);
            
            //Num avail in Clothing
            int clothingAvail = service.findNumCategoryAvailable(service.findCategoryById(2));
            model.addAttribute("clothingAvail", clothingAvail);
            
            //Num avail in Home
            int homeAvail = service.findNumCategoryAvailable(service.findCategoryById(3));
            model.addAttribute("homeAvail", homeAvail);
            
            //Num avail in Jewlery
            int jewelryAvail = service.findNumCategoryAvailable(service.findCategoryById(4));
            model.addAttribute("jewelryAvail", jewelryAvail);
            
        //Total Sales in $ by Category
            //$ sales in Art
            BigDecimal artSales = service.findSalesByCat(service.findCategoryById(1));
            model.addAttribute("artSales", artSales);
            
            //$ sales in Clothing
            BigDecimal clothingSales = service.findSalesByCat(service.findCategoryById(2));
            model.addAttribute("clothingSales", clothingSales);
            
            //$ sales in Home
            BigDecimal homeSales = service.findSalesByCat(service.findCategoryById(3));
            model.addAttribute("homeSales", homeSales);
            
            //$ sales in Jewelry
            BigDecimal jewelrySales = service.findSalesByCat(service.findCategoryById(4));
            model.addAttribute("jewelrySales", jewelrySales);

        return "index";
    }

    @GetMapping("availableItems")
    public String displayAvailableItems(Model model) {
        model.addAttribute("errors", violations);
        //To get only NOT SOLD items:
        Sold sold = service.findSoldById(1);
        List<Item> items = service.findBySold(sold);
        //To sort by date (newest at top, oldest at bottom)
        Collections.sort(items, new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                return i2.getDate().compareTo(i1.getDate());
            }
        });
        model.addAttribute("items", items);

        List<Category> categories = service.findAllCategories();
        Collections.sort(categories, new Comparator<Category>() {
            public int compare(Category c1, Category c2) {
                return (c1.getName()).toLowerCase().compareTo((c2.getName()).toLowerCase());
            }
        });
        model.addAttribute("categories", categories);

        List<Sold> solds = service.findAllSold();
        model.addAttribute("solds", solds);

        return "availableItems";
    }

    @PostMapping("addItem")
    public String addItem(HttpServletRequest request, Model model) {
        Item item = new Item();

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        String name = request.getParameter("name");

        item.setCategory(service.findCategoryById(categoryId));
        item.setName(name);

        try {
            String costString = request.getParameter("cost");
            BigDecimal cost = new BigDecimal(costString);
            item.setCost(cost);
        } catch (NumberFormatException e) {
            item.setCost(null);
        }

        try {
            String hoursString = request.getParameter("hours");
            BigDecimal hours = new BigDecimal(hoursString);
            item.setHours(hours);
        } catch (NumberFormatException e) {
            item.setHours(null);
        }

        try {
            LocalDate date = LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ISO_DATE);
            LocalDate today = LocalDate.now();
            if (date.isAfter(today)) {
                item.setDate(null);
            } else {
                item.setDate(date);
            }
        } catch (DateTimeParseException e) {
            item.setDate(null);
        }

        item.setSold(service.findSoldById(1));
        item.setNotes(request.getParameter("notes"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(item);

        boolean hasErrors;
        if (violations.isEmpty()) {
            hasErrors = false;
        } else {
            hasErrors = true;
        }

        //if hasErrors is false:
        if (!hasErrors) {
            service.addUpdateItem(item);
            return "redirect:/availableItems";
        }

        return "redirect:/availableItems";
    }

    @GetMapping("editItem")
    public String editItem(int id, Model model) {
        //Load the Item into the view & edit details page
        Item item = service.findItemById(id);
        model.addAttribute("item", item);

        model.addAttribute("errors", violations);

        List<Category> categories = service.findAllCategories();
        model.addAttribute("categories", categories);

        List<Sold> solds = service.findAllSold();
        model.addAttribute("solds", solds);

        return "editItem";
    }

    @PostMapping("editItem")
    public String editItem(HttpServletRequest request) {
        Item item = new Item();

        String itemIdString = request.getParameter("itemId");
        int itemId = Integer.parseInt(itemIdString);
        item.setItemId(itemId);

        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        item.setCategory(service.findCategoryById(categoryId));

        String name = request.getParameter("name");
        item.setName(name);

        String soldString = request.getParameter("sold");
        if (soldString.equals("Available")) {
            item.setSold(service.findSoldById(1));
            try {
                LocalDate date = LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ISO_DATE);
                LocalDate today = LocalDate.now();
                if (date.isAfter(today)) {
                    item.setDate(null);
                } else {
                    item.setDate(date);
                }
            } catch (DateTimeParseException e) {
                item.setDate(null);
            }
        } else if (soldString.equals("Sold")) {
            item.setSold(service.findSoldById(2));
            try {
                LocalDate date = LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ISO_DATE);
                Sale sale = service.findByItem(item);
                LocalDate soldDate = sale.getDate();
                if (date.isAfter(soldDate)) {
                    item.setDate(null);
                } else if (date.isBefore(soldDate)) {
                    item.setDate(date);
                }
            } catch (DateTimeParseException e) {
                item.setDate(null);
            }
        }

        try {
            String costString = request.getParameter("cost");
            BigDecimal cost = new BigDecimal(costString);
            item.setCost(cost);
        } catch (NumberFormatException e) {
            item.setCost(null);
        }

        try {
            String hoursString = request.getParameter("hours");
            BigDecimal hours = new BigDecimal(hoursString);
            item.setHours(hours);
        } catch (NumberFormatException e) {
            item.setHours(null);
        }

        item.setNotes(request.getParameter("notes"));

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(item);

        boolean hasErrors;
        if (violations.isEmpty()) {
            hasErrors = false;
        } else {
            hasErrors = true;
        }

        //if hasErrors is false:
        if (!hasErrors) {
            service.addUpdateItem(item);
            return "redirect:/index";
        }

        return "redirect:/editItem?id=" + itemId;
    }

    @GetMapping("soldItems")
    public String displaySoldItems(Model model) {
        model.addAttribute("errors", violationsSale);
        //To get only Sales:
        List<Sale> sales = service.findAllSales();
        //To sort by date (newest at top, oldest at bottom)
        Collections.sort(sales, new Comparator<Sale>() {
            public int compare(Sale s1, Sale s2) {
                return s2.getDate().compareTo(s1.getDate());
            }
        });
        model.addAttribute("sales", sales);

        //To get only NOT SOLD items:
        Sold sold = service.findSoldById(1);
        List<Item> items = service.findBySold(sold);
        //To sort by name A - Z
        Collections.sort(items, new Comparator<Item>() {
            public int compare(Item i1, Item i2) {
                return (i1.getName()).toLowerCase().compareTo((i2.getName()).toLowerCase());
            }
        });
        model.addAttribute("items", items);

        List<Category> categories = service.findAllCategories();
        model.addAttribute("categories", categories);

        List<Site> sites = service.findAllSites();
        Collections.sort(sites, new Comparator<Site>() {
            public int compare(Site s1, Site s2) {
                return (s1.getName()).toLowerCase().compareTo((s2.getName()).toLowerCase());
            }
        });
        model.addAttribute("sites", sites);

        return "soldItems";
    }

    @PostMapping("addSale")
    public String addSale(HttpServletRequest request, Model model) {
        Sale sale = new Sale();

        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int siteId = Integer.parseInt(request.getParameter("siteId"));

        Item itemSold = service.findItemById(itemId);
        Site site = service.findSiteById(siteId);

        sale.setItem(itemSold);
        sale.setSite(site);

        try {
            String priceString = request.getParameter("price");
            BigDecimal price = new BigDecimal(priceString);
            String minString = "0";
            BigDecimal min = new BigDecimal(minString);
            if (price.compareTo(min) <= 0) {
                sale.setPrice(null);
            } else {
                sale.setPrice(price);
            }
        } catch (NumberFormatException e) {
            sale.setPrice(null);
        }

        try {
            LocalDate date = LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ISO_DATE);
            LocalDate dateMade = itemSold.getDate();
            LocalDate today = LocalDate.now();
            if (date.isBefore(dateMade)) {
                sale.setDate(null);
            } else if (date.isAfter(today)) {
                sale.setDate(null);
            } else {
                sale.setDate(date);
            }
        } catch (DateTimeParseException e) {
            sale.setDate(null);
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsSale = validate.validate(sale);

        boolean hasErrors;
        if (violationsSale.isEmpty()) {
            hasErrors = false;
        } else {
            hasErrors = true;
        }

        //if hasErrors is false:
        if (!hasErrors) {
            itemSold.setSold(service.findSoldById(2));
            service.addUpdateSale(sale);
            return "redirect:/soldItems";
        }

        return "redirect:/soldItems";
    }

    @GetMapping("metrics")
    public String displayMetrics(Model model) {
       //Num of Sales By Category
            //Num sold in Art
            int artSold = service.findNumCategorySold(service.findCategoryById(1));
            model.addAttribute("artSold", artSold);
        
            //Num sold in Clothing
            int clothingSold = service.findNumCategorySold(service.findCategoryById(2));
            model.addAttribute("clothingSold", clothingSold);
        
            //Num sold in Home
            int homeSold = service.findNumCategorySold(service.findCategoryById(3));
            model.addAttribute("homeSold", homeSold);
        
            //Num sold in Jewelry
            int jewelrySold = service.findNumCategorySold(service.findCategoryById(4));
            model.addAttribute("jewelrySold", jewelrySold);
            
            //Total Num Sold
            int totalSold = artSold + clothingSold + homeSold + jewelrySold;
            model.addAttribute("totalSold", totalSold);
              
        //Num of Available by Category
            //Num avail in Art
            int artAvail = service.findNumCategoryAvailable(service.findCategoryById(1));
            model.addAttribute("artAvail", artAvail);
            
            //Num avail in Clothing
            int clothingAvail = service.findNumCategoryAvailable(service.findCategoryById(2));
            model.addAttribute("clothingAvail", clothingAvail);
            
            //Num avail in Home
            int homeAvail = service.findNumCategoryAvailable(service.findCategoryById(3));
            model.addAttribute("homeAvail", homeAvail);
            
            //Num avail in Jewlery
            int jewelryAvail = service.findNumCategoryAvailable(service.findCategoryById(4));
            model.addAttribute("jewelryAvail", jewelryAvail);
            
            //Total num avail
            int totalAvail = artAvail + clothingAvail + homeAvail + jewelryAvail;
            model.addAttribute("totalAvail", totalAvail);
            
        //Total Sales in $ by Category
            //$ sales in Art
            BigDecimal artSales = service.findSalesByCat(service.findCategoryById(1));
            model.addAttribute("artSales", artSales);
            
            //$ sales in Clothing
            BigDecimal clothingSales = service.findSalesByCat(service.findCategoryById(2));
            model.addAttribute("clothingSales", clothingSales);
            
            //$ sales in Home
            BigDecimal homeSales = service.findSalesByCat(service.findCategoryById(3));
            model.addAttribute("homeSales", homeSales);
            
            //$ sales in Jewelry
            BigDecimal jewelrySales = service.findSalesByCat(service.findCategoryById(4));
            model.addAttribute("jewelrySales", jewelrySales);
            
            //Total sales
            BigDecimal totalSales = artSales.add(clothingSales).add(homeSales).add(jewelrySales);
            model.addAttribute("totalSales", totalSales);
            
        //To find avg cost per item in category
            //Avg cost in Art
            BigDecimal artCost = service.findAvgCostInCat(service.findCategoryById(1));
            model.addAttribute("artCost", artCost);
            
            //Avg cost in Clothing
            BigDecimal clothingCost = service.findAvgCostInCat(service.findCategoryById(2));
            model.addAttribute("clothingCost", clothingCost);
            
            //Avg cost in Home
            BigDecimal homeCost = service.findAvgCostInCat(service.findCategoryById(3));
            model.addAttribute("homeCost", homeCost);
            
            //Avg cost in Jewelry
            BigDecimal jewelryCost = service.findAvgCostInCat(service.findCategoryById(4));
            model.addAttribute("jewelryCost", jewelryCost);
            
        //to find average sale price per category
            //average sale in Art
            BigDecimal artAvgSale = service.findAvgSaleByCat(service.findCategoryById(1));
            model.addAttribute("artAvgSale", artAvgSale);
            
            //average sale in Clothing
            BigDecimal clothingAvgSale = service.findAvgSaleByCat(service.findCategoryById(2));
            model.addAttribute("clothingAvgSale", clothingAvgSale);
            
            //average sale in Home
            BigDecimal homeAvgSale = service.findAvgSaleByCat(service.findCategoryById(3));
            model.addAttribute("homeAvgSale", homeAvgSale);
            
            //average sale in Jewelry
            BigDecimal jewelryAvgSale = service.findAvgSaleByCat(service.findCategoryById(4));
            model.addAttribute("jewelryAvgSale", jewelryAvgSale);
            
        //to find average days on market 
            //average days for Art
            long artDays = service.findAvgDaysByCat(service.findCategoryById(1));
            model.addAttribute("artDays", artDays);
            
            //average days for Clothing
            long clothingDays = service.findAvgDaysByCat(service.findCategoryById(2));
            model.addAttribute("clothingDays", clothingDays);
            
            //average days for Home
            long homeDays = service.findAvgDaysByCat(service.findCategoryById(3));
            model.addAttribute("homeDays", homeDays);
            
            //average days for Jewelry
            long jewelryDays = service.findAvgDaysByCat(service.findCategoryById(4));
            model.addAttribute("jewelryDays", jewelryDays);
        
        //to find # sold by site
            //num sold Amazon
            int amazonNum = service.findNumSoldBySite(1);
            model.addAttribute("amazonNum", amazonNum);
            
            //num sold eBay
            int ebayNum = service.findNumSoldBySite(2);
            model.addAttribute("ebayNum", ebayNum);
            
            //num sold etsy
            int etsyNum = service.findNumSoldBySite(3);
            model.addAttribute("etsyNum", etsyNum);
            
            //num sold poshmark
            int poshNum = service.findNumSoldBySite(4);
            model.addAttribute("poshNum", poshNum);
            
            //num sold society6
            int societyNum = service.findNumSoldBySite(5);
            model.addAttribute("societyNum", societyNum);
            
        //to find sales by site
            //amazon sales
            BigDecimal amazonSale = service.findSalesBySite(1);
            model.addAttribute("amazonSale", amazonSale);
            
            //ebay sales
            BigDecimal ebaySale = service.findSalesBySite(2);
            model.addAttribute("ebaySale", ebaySale);
            
            //etsy sales
            BigDecimal etsySale = service.findSalesBySite(3);
            model.addAttribute("etsySale", etsySale);
            
            //poshmark sales
            BigDecimal poshSale = service.findSalesBySite(4);
            model.addAttribute("poshSale", poshSale);
            
            //society6 sales
            BigDecimal societySale = service.findSalesBySite(5);
            model.addAttribute("societySale", societySale);

        return "metrics";
    }

    @GetMapping("deleteItem")
    public String deleteItem(int id, Model model) {
        //Load the Item into the view & edit details page
        Item item = service.findItemById(id);
        model.addAttribute("item", item);

        List<Category> categories = service.findAllCategories();
        model.addAttribute("categories", categories);

        List<Sold> solds = service.findAllSold();
        model.addAttribute("solds", solds);

        return "deleteItem";
    }

    @GetMapping("deleteItemInfo")
    public String deleteItemInfo(int id) {
        Item item = service.findItemById(id);

        Sold soldStatus = item.getSold();
        int soldId = soldStatus.getSoldId();

        if (soldId == 2) {
            Sale sale = service.findByItem(item);

            service.deleteSale(sale);
        }

        service.deleteItemById(id);
        return "redirect:/index";
    }

    @GetMapping("deleteSale")
    public String deleteSale(int id, Model model) {
        //Load the Item into the view & edit details page
        Item item = service.findItemById(id);
        model.addAttribute("item", item);

        Sale sale = service.findByItem(item);
        model.addAttribute("sale", sale);

        List<Category> categories = service.findAllCategories();
        model.addAttribute("categories", categories);

        List<Sold> solds = service.findAllSold();
        model.addAttribute("solds", solds);

        List<Site> sites = service.findAllSites();
        model.addAttribute("sites", sites);

        return "deleteSale";
    }

    @GetMapping("deleteSaleInfo")
    public String deleteSaleInfo(int id) {
        Item item = service.findItemById(id);

        Sold sold = service.findSoldById(1);

        Sale sale = service.findByItem(item);
        item.setSold(sold);

        service.deleteSale(sale);
        service.addUpdateItem(item);
        return "redirect:/soldItems";
    }

    @GetMapping("editSale")
    public String editSale(int id, Model model) {
        //Load the Item into the view & edit details page
        Item item = service.findItemById(id);
        model.addAttribute("item", item);

        model.addAttribute("errors", violationsSale);

        Sale sale = service.findByItem(item);
        model.addAttribute("sale", sale);

        List<Category> categories = service.findAllCategories();
        model.addAttribute("categories", categories);

        List<Sold> solds = service.findAllSold();
        model.addAttribute("solds", solds);

        List<Site> sites = service.findAllSites();
        model.addAttribute("sites", sites);

        return "editSale";
    }

    @PostMapping("editSale")
    public String editSale(HttpServletRequest request) {

        String itemIdString = request.getParameter("itemId");
        int itemId = Integer.parseInt(itemIdString);
        Item item = service.findItemById(itemId);

        Sale sale = service.findByItem(item);

        try {
            String priceString = request.getParameter("price");
            BigDecimal price = new BigDecimal(priceString);
            String minString = "0";
            BigDecimal min = new BigDecimal(minString);
            if (price.compareTo(min) <= 0) {
                sale.setPrice(null);
            } else {
                sale.setPrice(price);
            }
        } catch (NumberFormatException e) {
            sale.setPrice(null);
        }

        try {
            LocalDate date = LocalDate.parse(request.getParameter("date"), DateTimeFormatter.ISO_DATE);
            LocalDate dateMade = item.getDate();
            LocalDate today = LocalDate.now();
            if (date.isBefore(dateMade)) {
                sale.setDate(null);
            } else if (date.isAfter(today)) {
                sale.setDate(null);
            } else {
                sale.setDate(date);
            }
        } catch (DateTimeParseException e) {
            sale.setDate(null);
        }

        int siteId = Integer.parseInt(request.getParameter("siteId"));

        Site site = service.findSiteById(siteId);

        sale.setSite(site);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsSale = validate.validate(sale);

        boolean hasErrors;
        if (violationsSale.isEmpty()) {
            hasErrors = false;
        } else {
            hasErrors = true;
        }

        //if hasErrors is false:
        if (!hasErrors) {
            service.addUpdateSale(sale);
            return "redirect:/soldItems";
        }

        return "redirect:/editSale?id=" + itemId;
    }

    @GetMapping("admin")
    public String admin(Model model) {
        model.addAttribute("errors", violationsCategory);
        model.addAttribute("errorsTwo", violationsSite);
        List<Category> categories = service.findAllCategories();
        Collections.sort(categories, new Comparator<Category>() {
            public int compare(Category c1, Category c2) {
                return (c1.getName()).toLowerCase().compareTo((c2.getName()).toLowerCase());
            }
        });
        model.addAttribute("categories", categories);

        List<Site> sites = service.findAllSites();
        Collections.sort(sites, new Comparator<Site>() {
            public int compare(Site s1, Site s2) {
                return (s1.getName()).toLowerCase().compareTo((s2.getName()).toLowerCase());
            }
        });
        model.addAttribute("sites", sites);
        
        model.addAttribute("users", users.getAllUsers());

        return "admin";
    }
    
    @PostMapping("/addUser")
    public String addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEnabled(true);
        
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roles.getRoleByRole("ROLE_USER"));
        user.setRoles(userRoles);
        
        users.createUser(user);
        
        return "redirect:/admin";
    }
    
    @PostMapping("/deleteUser")
    public String deleteUser(Integer id) {
        users.deleteUser(id);
        return "redirect:/admin";
    }
    
    @GetMapping("/editUser")
    public String editUserDisplay(Model model, Integer id, Integer error) {
        User user = users.getUserById(id);
        List roleList = roles.getAllRoles();
        
        model.addAttribute("user", user);
        model.addAttribute("roles", roleList);
        
        if(error != null) {
            if(error == 1) {
                model.addAttribute("error", "Passwords did not match, password was not updated.");
            }
        }
        
        return "editUser";
    }
    
    @PostMapping(value="/editUser")
    public String editUserAction(String[] roleIdList, Boolean enabled, Integer id) {
        User user = users.getUserById(id);
        if(enabled != null) {
            user.setEnabled(enabled);
        } else {
            user.setEnabled(false);
        }
        
        Set<Role> roleList = new HashSet<>();
        for(String roleId : roleIdList) {
            Role role = roles.getRoleById(Integer.parseInt(roleId));
            roleList.add(role);
        }
        user.setRoles(roleList);
        users.updateUser(user);
        
        return "redirect:/admin";
    }
    
    @PostMapping("editPassword") 
    public String editPassword(Integer id, String password, String confirmPassword) {
        User user = users.getUserById(id);
        
        if(password.equals(confirmPassword)) {
            user.setPassword(encoder.encode(password));
            users.updateUser(user);
            return "redirect:/admin";
        } else {
            return "redirect:/editUser?id=" + id + "&error=1";
        }
    }

    @PostMapping("addCategory")
    public String addCategory(HttpServletRequest request, Model model) {
        Category category = new Category();

        String name = request.getParameter("name");

        category.setName(name);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsCategory = validate.validate(category);

        boolean hasErrors;
        if (violationsCategory.isEmpty()) {
            hasErrors = false;
        } else {
            hasErrors = true;
        }

        //if hasErrors is false:
        if (!hasErrors) {
            service.addUpdateCategory(category);
            return "redirect:/admin";
        }

        return "redirect:/admin";
    }

    @PostMapping("addSite")
    public String addSite(HttpServletRequest request, Model model) {
        Site site = new Site();

        String name = request.getParameter("name");

        site.setName(name);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsSite = validate.validate(site);

        boolean hasErrors;
        if (violationsSite.isEmpty()) {
            hasErrors = false;
        } else {
            hasErrors = true;
        }

        //if hasErrors is false:
        if (!hasErrors) {
            service.addUpdateSite(site);
            return "redirect:/admin";
        }

        return "redirect:/admin";
    }

    @GetMapping("deleteCategory")
    public String deleteCategory(int id, Model model) {
        Category category = service.findCategoryById(id);
        model.addAttribute("category", category);

        return "deleteCategory";
    }

    @GetMapping("deleteCategoryInfo")
    public String deleteCategoryInfo(int id) {
        Category category = service.findCategoryById(id);

        List<Item> items = service.findByCategory(category);
        for (Item i : items) {
            Sale sale = service.findByItem(i);
            if (sale != null) {
                service.deleteSale(sale);
            }
            int itemId = i.getItemId();

            service.deleteItemById(itemId);
        }

        service.deleteCategoryById(id);

        return "redirect:/admin";
    }

    @GetMapping("deleteSite")
    public String deleteSite(int id, Model model) {
        Site site = service.findSiteById(id);
        model.addAttribute("site", site);

        return "deleteSite";
    }

    @GetMapping("deleteSiteInfo")
    public String deleteSiteInfo(int id) {
        Site site = service.findSiteById(id);

        List<Sale> sales = service.findBySite(site);
        for (Sale s : sales) {
            Item item = s.getItem();
            item.setSold(service.findSoldById(1));
            
            service.deleteSale(s);
            }

        service.deleteSiteById(id);

        return "redirect:/admin";
    }

    @GetMapping("clearErrorsAll")
    public String clearErrorsAllItems() {
        violations.clear();
        violationsSale.clear();
        violationsCategory.clear();
        violationsSite.clear();
        return "redirect:/index";
    }

    @GetMapping("clearErrorsAdmin")
    public String clearErrorsAdminItems() {
        violations.clear();
        violationsSale.clear();
        violationsCategory.clear();
        violationsSite.clear();
        return "redirect:/admin";
    }

    @GetMapping("clearErrorsPurchased")
    public String clearErrorsPurchasedItems() {
        violations.clear();
        violationsSale.clear();
        violationsCategory.clear();
        violationsSite.clear();
        return "redirect:/soldItems";
    }

    @GetMapping("clearErrorsMetrics")
    public String clearErrorsMetrics() {
        violations.clear();
        violationsSale.clear();
        violationsCategory.clear();
        violationsSite.clear();
        return "redirect:/metrics";
    }

    @GetMapping("clearErrorsAvailable")
    public String clearErrorsAvailable() {
        violations.clear();
        violationsSale.clear();
        violationsCategory.clear();
        violationsSite.clear();
        return "redirect:/availableItems";
    }

}
