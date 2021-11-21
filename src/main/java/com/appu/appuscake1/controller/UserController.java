package com.appu.appuscake1.controller;

import com.appu.appuscake1.dao.*;
import com.appu.appuscake1.helper.Message;
import com.appu.appuscake1.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.net.Inet4Address;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

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
    public void addCommonData(Model model,Principal principal) {
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

    @GetMapping("/profile_view")
    public String profile_view() {
        return "profile_view";
    }

    @GetMapping("/profile_edit")
    public String profile_edit() {
        return "profile_edit";
    }

    @PostMapping("/process_edit_profile")
    public String process_edit_profile(@ModelAttribute("currUser") User user, Model model, HttpSession session, Principal principal)
    {
        try {
            System.out.println("USERNAME ---- ");
            User currUser = userdao.getUserByEmail(principal.getName());


            user.setId(currUser.getId());
            user.setRole(currUser.getRole());
            user.setPassword(currUser.getPassword());

            userdao.update(user);

            currUser = userdao.getUserByID(user.getId());

            model.addAttribute("currUser",currUser);
            session.setAttribute("message",new Message("Edit successful","alert-success"));
            return "redirect:/user/profile_view";

        } catch(Exception e) {
            e.printStackTrace();
            User currUser = userdao.getUserByEmail(principal.getName());
            model.addAttribute("currUser",currUser);
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "redirect:/user/profile_edit";
        }
    }

    @GetMapping("/add-cart/{pid}")
    public String addCart(@PathVariable int pid, Model model,Principal principal) {
        User currUser = userdao.getUserByEmail(principal.getName());
        productdao.addToCart(currUser.getId(), pid);

        return "redirect:/products";
    }

    @GetMapping("/delete-cart/{pid}")
    public String deleteCart(@PathVariable int pid, Model model,Principal principal) {
        User currUser = userdao.getUserByEmail(principal.getName());
        productdao.deleteFromCart(currUser.getId(), pid);
        return "redirect:/user/cart";
    }

    @GetMapping("/cart")
    public String Cart(Model model,Principal principal) {
        User currUser = userdao.getUserByEmail(principal.getName());
//        List<Product> productsAll = productdao.getAllAvailableProducts();
        List<Cart> carts = productdao.getAllAvailableCarts(currUser.getId());
        List<Product> products = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        List<Integer> finalPrice = new ArrayList<>();
        for(Cart cart: carts) {

            products.add(productdao.getProductByID(cart.getProductid()));
            System.out.println(cart.getQuantity());
        }

        for(Product product: products)
        {
            Category category = categorydao.getCategoryByID(product.getCategoryid());
            categories.add(category);
            float net_discount = product.getDiscount() + category.getDiscount();
            int net_amount = (int) (product.getPrice() - (net_discount/100.0)*product.getPrice());
            finalPrice.add(net_amount);

        }
        model.addAttribute("products",products);
        model.addAttribute("categories",categories);
        model.addAttribute("carts",carts);
        model.addAttribute("finalPrice",finalPrice);

        return "cart";
    }

    @GetMapping("/paymentView")
    public String paymentView(Model model,Principal principal) {
        User currUser = userdao.getUserByEmail(principal.getName());

        List<Cart> carts = productdao.getAllAvailableCarts(currUser.getId());
        List<Product> products = new ArrayList<>();
        for(Cart cart: carts) {
            products.add(productdao.getProductByID(cart.getProductid()));
        }

        List<Integer> finalPrice = new ArrayList<>();
        List<Category> categories = new ArrayList<>();
        int totalAmount = 0;
        int netAmount = 0;

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
        model.addAttribute("carts",carts);
        model.addAttribute("finalPrice",finalPrice);
        model.addAttribute("order",new Order());
        model.addAttribute("totalAmount",totalAmount);
        model.addAttribute("netAmount",netAmount);


        return "paymentView";

    }

    @PostMapping("/payment-process")
    public String paymentProcess(@ModelAttribute("order") Order order, Model model, HttpSession session, Principal principal)
    {
        try {

            User currUser = userdao.getUserByEmail(principal.getName());
            long millis=System.currentTimeMillis();
            Date date=new java.sql.Date(millis);
            order.setOrder_date(date);
            order.setPayment_date(date);
            order.setDelivery_date(null);
            order.setCustomerid(currUser.getId());
            order.setStatus(1);

            List<Cart> carts = productdao.getAllAvailableCarts(currUser.getId());
            List<Product> products = new ArrayList<>();
            for(Cart cart: carts) {
                products.add(productdao.getProductByID(cart.getProductid()));
            }

            List<Integer> finalPrice = new ArrayList<>();
            List<Category> categories = new ArrayList<>();
            int totalAmount = 0;
            int netAmount = 0;

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

            order.setCost(netAmount);
            order = orderdao.save(order);

            for(Cart cart: carts) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderid(order.getId());
                orderItem.setProductid(cart.getProductid());
                orderItem.setQuantity(cart.getQuantity());
                orderdao.saveOrderItem(orderItem);
                productdao.updateQuantity(cart.getProductid(),cart.getQuantity());
            }

            productdao.deleteEntireCart(currUser.getId());
//            session.setAttribute("message",new Message("Edit successful","alert-success"));
            return "redirect:/user/past-orders";

        } catch(Exception e) {
            e.printStackTrace();
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "redirect:/user/paymentView";
        }

    }

    @GetMapping("/past-orders")
    public String pastOrders(Model model,Principal principal) {
        User currUser = userdao.getUserByEmail(principal.getName());
        List<Order> orders = orderdao.getAllCurrentOrders(currUser.getId());
        List<Integer> totalAmountAll = new ArrayList<>();
        List<Integer> netAmountAll = new ArrayList<>();
        ArrayList<ArrayList<Product>> productsAll = new ArrayList<ArrayList<Product>>();
        ArrayList<ArrayList<Category>> categoriesAll = new ArrayList<ArrayList<Category>>();
        ArrayList<ArrayList<Integer>> finalPriceAll = new ArrayList<ArrayList<Integer>>();
        ArrayList<ArrayList<OrderItem>> orderItemsAll = new ArrayList<ArrayList<OrderItem>>();


        for(Order order : orders) {
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

            ArrayList<OrderItem> orderItemsfoo = new ArrayList<>();
            ArrayList<Product> productsfoo = new ArrayList<>();
            ArrayList<Category> categoriesfoo = new ArrayList<>();
            ArrayList<Integer> finalPricefoo = new ArrayList<>();

            for(Product product:products)
                productsfoo.add(product);

            for(Category category: categories)
                categoriesfoo.add(category);

            for(OrderItem orderItem: orderItems)
                orderItemsfoo.add(orderItem);

            for(int x:finalPrice)
                finalPricefoo.add(x);

            orderItemsAll.add(orderItemsfoo);
            productsAll.add(productsfoo);
            categoriesAll.add(categoriesfoo);
            finalPriceAll.add(finalPricefoo);
            netAmountAll.add(netAmount);
            totalAmountAll.add(totalAmount);

        }

        model.addAttribute("orders",orders);
        model.addAttribute("productsAll",productsAll);
        model.addAttribute("categoriesAll",categoriesAll);
        model.addAttribute("orderItemsAll",orderItemsAll);
        model.addAttribute("finalPriceAll",finalPriceAll);
        model.addAttribute("totalAmountAll",totalAmountAll);
        model.addAttribute("netAmountAll",netAmountAll);

        return "past-orders";

    }

    @PostMapping("/cart-quantity-update/{pid}")
    public String processQuantityUpdate(@PathVariable int pid, Model model, HttpSession session, Principal principal, @RequestParam("i") int i)
    {
        User currUser = userdao.getUserByEmail(principal.getName());
        productdao.updateCart(currUser.getId(), pid, i);

        return "redirect:/user/cart";
    }
}
