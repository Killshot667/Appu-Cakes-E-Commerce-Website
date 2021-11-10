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
@RequestMapping("user")
public class UserController {

    @Autowired
    private Userdao userdao;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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
            user.setProfileImage(currUser.getProfileImage());
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


}
