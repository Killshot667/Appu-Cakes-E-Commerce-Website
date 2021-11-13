package com.appu.appuscake1.controller;

import com.appu.appuscake1.dao.Articledao;
import com.appu.appuscake1.dao.Categorydao;
import com.appu.appuscake1.dao.Productdao;
import com.appu.appuscake1.dao.Userdao;
import com.appu.appuscake1.helper.Message;
import com.appu.appuscake1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

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





}
