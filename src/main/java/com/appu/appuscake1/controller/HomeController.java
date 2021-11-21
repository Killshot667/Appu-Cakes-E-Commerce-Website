package com.appu.appuscake1.controller;

import com.appu.appuscake1.dao.Articledao;
import com.appu.appuscake1.dao.Categorydao;
import com.appu.appuscake1.dao.Productdao;
import com.appu.appuscake1.dao.Userdao;
import com.appu.appuscake1.helper.Message;
import com.appu.appuscake1.model.Article;
import com.appu.appuscake1.model.Category;
import com.appu.appuscake1.model.Product;
import com.appu.appuscake1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {

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


    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user",new User());
        return "signup";
    }

    @RequestMapping("/login")
    public String login() {
        return "signin";
    }

    @RequestMapping("/signin")
    public String customSignin()
    {
        return "signin";
    }

    @PostMapping("/dologin")
    public String loginProcess() {
        return "home";
    }

    @PostMapping("/do_register")
    public String do_register(@ModelAttribute("user") User user, Model model, HttpSession session)
    {
        try {
            user.setRole("ROLE_USER");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userdao.save(user);
            model.addAttribute("user",new User());
            session.setAttribute("message",new Message("Registered successfully","alert-success"));
            return "redirect:/signup";

        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "redirect:/signup";
        }
    }



    @GetMapping("/products")
    public String products(Model model, @RequestParam(value = "searchBar",required = false) String searchBar, Principal principal) {

        List<Product> productsAll;
        if(searchBar==null || searchBar=="")
        productsAll = productdao.getAllAvailableProducts();
        else
            productsAll = productdao.getAllAvailableProductsPatternMulti(searchBar);
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


    @GetMapping("/product-detail/{pid}")
    public String productDetail(@PathVariable int pid, Model model, Principal principal) {

        Product product = productdao.getProductByID(pid);
        Category category = categorydao.getCategoryByID(product.getCategoryid());

        model.addAttribute("product",product);
        model.addAttribute("category",category);

        float net_discount = product.getDiscount() + category.getDiscount();
        int net_amount = (int) (product.getPrice() - (net_discount/100.0)*product.getPrice());

        model.addAttribute("net_amount",net_amount);

        if ( principal!=null && principal.getName()!=null) {
            User currUser = userdao.getUserByEmail(principal.getName());
            Set<Integer> foo = new HashSet<Integer>(productdao.getProductsInCart(currUser.getId()));
            boolean check;
            if(foo.contains(product.getId()))
                check = false;
            else
                check = true;

            model.addAttribute("check",check);
        }

        return "product-detail";
    }

    @GetMapping("/article-list")
    public String articleList(Model model) {
        List<Article> articles = articledao.getAllArticles();
        model.addAttribute("articles",articles);
        return "article-list";
    }


}
