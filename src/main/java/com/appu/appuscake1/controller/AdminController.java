package com.appu.appuscake1.controller;


import com.appu.appuscake1.dao.*;
import com.appu.appuscake1.helper.Message;
import com.appu.appuscake1.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.sql.Date;
import java.util.Set;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Userdao userdao;

    @Autowired
    private Productdao productdao;

    @Autowired
    private Categorydao categorydao;

    @Autowired
    private Articledao articledao;

    @Autowired
    private Orderdao orderdao;


    //method for adding common data to response
    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        String username = null;
        if(principal != null)
            username = principal.getName();
        if(username!=null)
            System.out.println("USERNAME " + username);

        User user = null;
        if(username!=null)
            user = userdao.getUserByEmail(username);

        model.addAttribute("currUser",user);
    }

    @GetMapping("/category-list")
    public String categoryList(Model model) {
        List<Category> categories = categorydao.getAllCategories();
        model.addAttribute("categories",categories);
        return "category-list";
    }

    @GetMapping("/category-detail/{cid}")
    public String categoryDetail(@PathVariable int cid, Model model) {
        Category category = categorydao.getCategoryByID(cid);
        model.addAttribute("category",category);
        return "category-detail";
    }

    @GetMapping("/category-delete/{cid}")
    public String categoryDelete(@PathVariable int cid, Model model) {
        categorydao.delete(cid);
        return "redirect:/admin/category-list";
    }

    @GetMapping("/category-edit/{cid}")
    public String categoryEdit(@PathVariable int cid, Model model) {
        Category category = categorydao.getCategoryByID(cid);
        model.addAttribute("category",category);
        return "category-edit";
    }

    @PostMapping("/process-category-edit/{cid}")
    public String processCategoryEdit(@ModelAttribute("category") Category category, @PathVariable int cid, Model model, HttpSession session, Principal principal)
    {
        try {
            category.setId(cid);
            categorydao.update(category);
            category = categorydao.getCategoryByID(category.getId());
            model.addAttribute("category",category);
            session.setAttribute("message",new Message("Edit successful","alert-success"));
            return "category-detail";

        } catch(Exception e) {
            e.printStackTrace();
            category.setId(cid);
            category = categorydao.getCategoryByID(category.getId());
            model.addAttribute("category",category);
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "category-edit";
        }
    }

    @GetMapping("/category-add")
    public String categoryAdd(Model model) {
        model.addAttribute("category",new Category());
        return "category-add";
    }

    @PostMapping("/process-category-add")
    public String processCategoryAdd(@ModelAttribute("category") Category category, Model model, HttpSession session)
    {
        try {

            categorydao.save(category);
            model.addAttribute("category",new Category());
            session.setAttribute("message",new Message("Added successfully","alert-success"));
            return "category-add";

        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("category", category);
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "category-add";
        }
    }



    @GetMapping("/edit-product/{pid}")
    public String editProduct(@PathVariable int pid, Model model) {
        Product product = productdao.getProductByID(pid);
        List<Category> categories = categorydao.getAllCategories();
        model.addAttribute("product",product);
        model.addAttribute("categories",categories);
        return "product-edit";
    }

    @GetMapping("/article-add")
    public String articleAdd(Model model) {
        model.addAttribute("article",new Article());
        return "article-add";
    }

    @PostMapping("/process-article-add")
    public String processArticleAdd(@ModelAttribute("article") Article article, Model model, HttpSession session) {
        try {
            int n = article.getContent().length();
            if(article.getContent().charAt(0)==',')
                article.setContent(article.getContent().substring(1,n));
            long millis=System.currentTimeMillis();
            Date date=new java.sql.Date(millis);
            article.setDate(date);
            articledao.save(article);
            model.addAttribute("article",new Article());
            session.setAttribute("message",new Message("Added successfully","alert-success"));
            return "article-add";

        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("article", article);
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "article-add";
        }
    }

    @GetMapping("/article-detail/{aid}")
    public String articleDetail(@PathVariable int aid, Model model) {
        Article article = articledao.getArticleByID(aid);
        model.addAttribute("article",article);
        return "article-detail";
    }

    @GetMapping("/article-delete/{aid}")
    public String articleDelete(@PathVariable int aid, Model model) {
        articledao.delete(aid);
        return "redirect:/article-list";
    }

    @GetMapping("/article-edit/{aid}")
    public String articleEdit(@PathVariable int aid, Model model) {
        Article article = articledao.getArticleByID(aid);
        model.addAttribute("article",article);
        return "article-edit";
    }


    @PostMapping("/process-article-edit/{aid}")
    public String processArticleEdit(@ModelAttribute("article") Article article, @PathVariable int aid, Model model, HttpSession session, Principal principal)
    {
        try {

            int n = article.getContent().length();
            if(article.getContent().charAt(0)==',')
                article.setContent(article.getContent().substring(1,n));

            Article prevArticle = articledao.getArticleByID(aid);
            article.setId(aid);
            article.setDate(prevArticle.getDate());
            articledao.update(article);
//            article = articledao.getArticleByID(article.getId());
//            model.addAttribute("article",article);
//            session.setAttribute("message",new Message("Edit successful","alert-success"));
            return "redirect:/article-list";

        } catch(Exception e) {
            e.printStackTrace();
            article.setId(aid);
            article = articledao.getArticleByID(aid);
//            model.addAttribute("article",article);
//            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "redirect:/article-list";
        }
    }



    @GetMapping("/product-delete/{pid}")
    public String productDelete(@PathVariable int pid, Model model) {
        productdao.delete(pid);
        return "redirect:/products";
    }


    @GetMapping("/product-edit/{pid}")
    public String productEdit(@PathVariable int pid, Model model) {
        Product product = productdao.getProductByID(pid);
        List<Category> categories = categorydao.getAllCategories();
        model.addAttribute("product",product);
        model.addAttribute("categories",categories);
        return "product-edit";
    }

    @GetMapping("/product-add")
    public String productAdd(Model model) {
        model.addAttribute("product",new Product());
        List<Category> categories = categorydao.getAllCategories();
        model.addAttribute("categories",categories);
        return "product-add";
    }

    @PostMapping("/process-product-add")
    public String processProductAdd(@ModelAttribute("product") Product product, @RequestParam("productImage") MultipartFile file, Model model, HttpSession session) {

        try {

            if(file.isEmpty())
            {
                product.setProdImage("chocolate.jpg");
            }
            else
            {
                product.setProdImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img/product").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            productdao.save(product);

            model.addAttribute("product",new Product());
            List<Category> categories = categorydao.getAllCategories();
            model.addAttribute("categories",categories);
            session.setAttribute("message",new Message("Added successfully","alert-success"));
            return "product-add";

        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("product",product);
            List<Category> categories = categorydao.getAllCategories();
            model.addAttribute("categories",categories);
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "product-add";
        }
    }

    @PostMapping("/process-product-edit/{pid}")
    public String processProductEdit(@ModelAttribute("product") Product product,@PathVariable int pid, @RequestParam("productImage") MultipartFile file, Model model, HttpSession session) {

        try {
            Product origProduct = productdao.getProductByID(pid);
            product.setId(pid);

            if(file.isEmpty())
            {
                product.setProdImage(origProduct.getProdImage());
            }
            else
            {
                product.setProdImage(file.getOriginalFilename());
                File saveFile = new ClassPathResource("static/img/product").getFile();
                Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            }

            productdao.update(product);
//            product = productdao.getProductByID(pid);

//            model.addAttribute("product",product);
//            List<Category> categories = categorydao.getAllCategories();
//            model.addAttribute("categories",categories);
//            session.setAttribute("message",new Message("Added successfully","alert-success"));
            return "redirect:/product-detail/" + String.valueOf(pid);

        } catch(Exception e) {
            e.printStackTrace();
            product = productdao.getProductByID(pid);
            model.addAttribute("product",product);
            List<Category> categories = categorydao.getAllCategories();
            model.addAttribute("categories",categories);
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "product-edit";
        }
    }


    @GetMapping("/order-list")
    public String orderList(Model model,Principal principal) {

        List<Order> orders = orderdao.getAllOrders();
        List<Order> placedOrders = new ArrayList<>();
        List<Order> unplacedOrders = new ArrayList<>();
        List<User> placedUsers = new ArrayList<>();
        List<User> unplacedUsers = new ArrayList<>();

        for(Order order : orders) {
            User user = userdao.getUserByID(order.getCustomerid());
            System.out.println(order.getStatus());
            if(order.getStatus() == 1)
            {placedOrders.add(order);
                System.out.println("yes");
                placedUsers.add(user);}
            else{
                unplacedOrders.add(order);
                unplacedUsers.add(user); }
        }



        model.addAttribute("placedOrders",placedOrders);
        model.addAttribute("unplacedOrders",unplacedOrders);
        model.addAttribute("placedUsers",placedUsers);
        model.addAttribute("unplacedUsers",unplacedUsers);

        return "order-list";

    }

    @GetMapping("/order-detail/{oid}")
    public String orderDetail(@PathVariable int oid,Model model,Principal principal) {

        Order order = orderdao.getOrderByID(oid);
        List<Integer> finalPrice = new ArrayList<>();
        List<OrderItem> orderItems = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        int totalAmount = 0;
        int netAmount = 0;
        orderItems = orderdao.getAllOrderItems(order.getId());

        for(OrderItem orderItem: orderItems)
        {
            products.add(productdao.getProductByID(orderItem.getProductid()));
        }

        for(Product product: products)
        {
            Category category = categorydao.getCategoryByID(product.getCategoryid());
            categories.add(category);
            totalAmount = totalAmount + product.getPrice();
            float net_discount = product.getDiscount() + category.getDiscount();
            int net_amount = (int) (product.getPrice() - (net_discount/100.0)*product.getPrice());
            finalPrice.add(net_amount);
            netAmount = netAmount + net_amount;
        }

        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        model.addAttribute("orderItems",orderItems);
        model.addAttribute("finalPrice",finalPrice);
        model.addAttribute("order",order);
        model.addAttribute("totalAmount",totalAmount);
        model.addAttribute("netAmount",netAmount);

        return "order-detail";

    }

    @GetMapping("/order-change-delivered/{oid}")
    public String orderChangeDelivery(@PathVariable int oid, Model model,Principal principal) {

        Order order = orderdao.getOrderByID(oid);
        order.setStatus(2);
        long millis=System.currentTimeMillis();
        Date date=new java.sql.Date(millis);
        order.setDelivery_date(date);
        orderdao.update(order);
        return "redirect:/admin/order-detail/" + oid;

    }

    @GetMapping("/order-change-placed/{oid}")
    public String orderChangePlaced(@PathVariable int oid, Model model,Principal principal) {

        Order order = orderdao.getOrderByID(oid);
        order.setStatus(1);
        order.setDelivery_date(null);
        orderdao.update(order);
        return "redirect:/admin/order-detail/" + oid;

    }

    @GetMapping("/customer-list")
    public String customerList(Model model,Principal principal) {

        List<User> customers = userdao.getAllCustomers();
        model.addAttribute("customers",customers);

        return "customer-list";

    }

    @GetMapping("/customer-detail/{uid}")
    public String customerDetail(@PathVariable int uid, Model model,Principal principal) {

        User user = userdao.getUserByID(uid);
        model.addAttribute("user",user);
        return "customer-detail";

    }

    @GetMapping("/customer-delete/{uid}")
    public String customerDelete(@PathVariable int uid, Model model,Principal principal) {

        userdao.delete(uid);
        return "redirect:/admin/customer-list";

    }

    @GetMapping("/out-of-stock")
    public String productsNonAvailable(Model model, Principal principal) {

        List<Product> productsAll = productdao.getAllNonAvailableProducts();
//        if(searchBar==null || searchBar=="")
//            productsAll = productdao.getAllAvailableProducts();
//        else
//            productsAll = productdao.getAllAvailableProductsPatternMulti(searchBar);
        List<Integer> toDisplay = new ArrayList<>();
        User currUser = null;


        if ( principal!=null && principal.getName()!=null) {
            currUser = userdao.getUserByEmail(principal.getName());
            toDisplay = productdao.getProductsInCart(currUser.getId());
        }
        List<Category> categories = new ArrayList<>();
        Set<Integer> foo = new HashSet<Integer>(toDisplay);
        List<Boolean> check = new ArrayList<Boolean>();
        List<Integer> finalPrice = new ArrayList<>();

        for(Product product: productsAll){
            if(foo.contains(product.getId()))
                check.add(false);
            else
                check.add(true);
            Category category = categorydao.getCategoryByID(product.getCategoryid());
            categories.add(category);
            float net_discount = product.getDiscount() + category.getDiscount();
            int net_amount = (int) (product.getPrice() - (net_discount/100.0)*product.getPrice());
            finalPrice.add(net_amount);

            model.addAttribute("products",productsAll);
            model.addAttribute("check",check);
            model.addAttribute("categories",categories);
            model.addAttribute("finalPrice",finalPrice);
            model.addAttribute("currUser",currUser);

        }
        return "products";
    }


}

