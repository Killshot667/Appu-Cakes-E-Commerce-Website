package com.appu.appuscake1.controller;

import com.appu.appuscake1.dao.Userdao;
import com.appu.appuscake1.helper.Message;
import com.appu.appuscake1.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private Userdao userdao;


    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user",new User());
        return "signup";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/do_register")
    public String do_register(@ModelAttribute("user") User user, Model model, HttpSession session)
    {
        try {
            user.setRole("USER_ROLE");
            user.setProfileImage("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userdao.save(user);
            model.addAttribute("user",new User());
            session.setAttribute("message",new Message("Registered successfully","alert-success"));
            return "signup";

        } catch(Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user);
            session.setAttribute("message", new Message("Something went Wrong!! " + e.getMessage(), "alert-danger"));
            return "signup";
        }
    }
}
