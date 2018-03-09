package com.example.demo.Controllers;

import com.example.demo.Models.AppRole;
import com.example.demo.Models.AppUser;
import com.example.demo.Models.Category;
import com.example.demo.Repositories.AppRoleRepository;
import com.example.demo.Repositories.AppUserRepository;
import com.example.demo.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    CategoryRepository categoryRepository;

    @Autowired
    NewsService newsService;

    // METHODS //////////////
    // Homepage
    @RequestMapping("/")
    public String goToHome(Model model){
        model.addAttribute("message","Top News Headlines");
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

    // User Methods
    @GetMapping("/addtopic") // shows users profile
    public String addCategory(Model model, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        model.addAttribute("addtopic",new Category());
        model.addAttribute("profile",appUser);
        return "userprofilepage";
    }

    @PostMapping("/addtopic")
    public String processAddCategory(@Valid @ModelAttribute("addtopic")Category category, BindingResult result,
            Authentication authentication, Model model){
        if (result.hasErrors()) {
            System.out.println(result.toString());
            return "userprofilepage";
        } else {
            AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
            appUser.addCategory(category);
            appUserRepository.save(appUser);
            return "redirect:/addtopic";
        }
    }

    @RequestMapping("/personalnews")
    public String showPersonalNews(Model model, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        model.addAttribute("message","Your Personal News");
        model.addAttribute("newslist", appUser.getCategoryList());
        /*model.addAttribute("personal",newsService);*/
        return "personallist";
    }

}
