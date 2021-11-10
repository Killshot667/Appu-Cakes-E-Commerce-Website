package com.appu.appuscake1.controller;

import com.appu.appuscake1.dao.Userdao;
import com.appu.appuscake1.helper.Message;
import com.appu.appuscake1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Userdao userdao;

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
            user.setProfileImage("default.png");
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



}
