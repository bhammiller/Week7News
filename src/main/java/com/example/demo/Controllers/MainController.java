package com.example.demo.Controllers;

import com.example.demo.Models.AppRole;
import com.example.demo.Models.AppUser;
import com.example.demo.Repositories.AppRoleRepository;
import com.example.demo.Repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class MainController {
    // VARIABLES ////////////
    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    NewsService newsService;

    // METHODS //////////////
    // Homepage
    @RequestMapping("/")
    public String goToHome(Model model){
        model.addAttribute("newslist",newsService.findTopHeadlines());
        return "headlineslist";
    }

    // Login and Registraion
    @RequestMapping("/login")
    public String login() {
        return "loginpage";
    }

    // Security and User Methods

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("newUser", new AppUser());
        return "registrationpage";
    }

    @PostMapping("/register")
    public String addNewUser(@Valid @ModelAttribute("newUser") AppUser newUser, BindingResult result, Model model) {

        if (result.hasErrors()) {
            System.out.println(result.toString());
            return "registrationpage";
        } else {

            model.addAttribute(newUser.getAppUsername() + " created");
            appUserRepository.save(newUser);
            AppRole r = appRoleRepository.findAppRoleByRoleName("USER");
            newUser.addRole(r);
            appUserRepository.save(newUser);
            return "redirect:/login";
        }
    }
}
