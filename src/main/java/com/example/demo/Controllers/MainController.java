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
import org.springframework.web.bind.annotation.*;

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
            newUser.addCategory(categoryRepository.findOne(new Long(1)));
            newUser.addCategory(categoryRepository.findOne(new Long(2)));
            newUser.addCategory(categoryRepository.findOne(new Long(3)));
            newUser.addCategory(categoryRepository.findOne(new Long(4)));
            newUser.addCategory(categoryRepository.findOne(new Long(5)));
            newUser.addCategory(categoryRepository.findOne(new Long(6)));
            newUser.addCategory(categoryRepository.findOne(new Long(7)));
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
        return "profilepage";
    }

    @PostMapping("/addtopic")
    public String processAddCategory(@Valid @ModelAttribute("addtopic")Category category, BindingResult result,
            Authentication authentication, Model model){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        if (result.hasErrors()) {
            System.out.println(result.toString());
            model.addAttribute("profile",appUser);
            return "profilepage";
        } else {
            category.setFavStatus(true);
            categoryRepository.save(category);
            appUser.addCategory(category);
            appUserRepository.save(appUser);
            return "redirect:/addtopic";
        }
    }

    @RequestMapping("/remove/{id}")
    public String removeACatagory(@PathVariable("id")long id){
        Category category=categoryRepository.findOne(id);
        category.setFavStatus(false);
        categoryRepository.save(category);
        return "redirect:/addtopic";
    }

    @RequestMapping("/enable/{id}")
    public String enableACatagory(@PathVariable("id")long id){
        Category category=categoryRepository.findOne(id);
        category.setFavStatus(true);
        categoryRepository.save(category);
        return "redirect:/addtopic";
    }

    @RequestMapping("/personalnews")
    public String showPersonalNews(Model model, Authentication authentication){
        AppUser appUser = appUserRepository.findAppUserByAppUsername(authentication.getName());
        model.addAttribute("message","Your Personal News");
        model.addAttribute("newslist", appUser.getCategoryList());
        return "personallist";
    }

    @RequestMapping("/newscategory/{cattypename}")
    public String showNewsCategory(@PathVariable("cattypename")String cattypename, Model model){
        model.addAttribute("message",cattypename.toUpperCase());
        model.addAttribute("newslist",newsService.findNewsAPICategory(cattypename));
        return "headlineslist";
    }

}
