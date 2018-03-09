package com.example.demo.Config;

import com.example.demo.Models.AppRole;
import com.example.demo.Models.AppUser;
import com.example.demo.Models.Category;
import com.example.demo.Repositories.AppRoleRepository;
import com.example.demo.Repositories.AppUserRepository;
import com.example.demo.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    AppRoleRepository appRoleRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading data ...");
        AppRole role = new AppRole();
        role.setRoleName("USER");
        appRoleRepository.save(role);

        role = new AppRole();
        role.setRoleName("ADMIN");
        appRoleRepository.save(role);

        // Users
        // User 1
        AppUser appUser = new AppUser();
        appUser.setFirstName("John");
        appUser.setLastName("Doe");
        appUser.setAppUsername("john");
        appUser.setAppPassword("password1");
        appUserRepository.save(appUser);
        appUser.addRole(appRoleRepository.findAppRoleByRoleName("USER"));
        appUserRepository.save(appUser);
        // User 2
        appUser = new AppUser();
        appUser.setFirstName("Jacob");
        appUser.setLastName("Smith");
        appUser.setAppUsername("jacob");
        appUser.setAppPassword("password2");
        appUserRepository.save(appUser);
        appUser.addRole(appRoleRepository.findAppRoleByRoleName("USER"));
        appUserRepository.save(appUser);
        // User 3
        appUser = new AppUser();
        appUser.setFirstName("Jane");
        appUser.setLastName("Pane");
        appUser.setAppUsername("jane");
        appUser.setAppPassword("password3");
        appUserRepository.save(appUser);
        appUser.addRole(appRoleRepository.findAppRoleByRoleName("USER"));
        appUserRepository.save(appUser);

        // Category
        Category category=new Category();
        category = new Category();
        category.setName("China");
        category.setFavStatus(true);
        categoryRepository.save(category);
        category = new Category();
        category.setFavStatus(true);
        category.setName("Science");
        categoryRepository.save(category);
        appUser=appUserRepository.findOne(new Long(3));
        appUser.addCategory(categoryRepository.findByName("China"));
        appUser.addCategory(categoryRepository.findByName("Science"));
        appUserRepository.save(appUser);
        /*appUser=appUserRepository.findOne(new Long(3));
        appUser.subtractCategory(categoryRepository.findByName("Trump"));
        appUserRepository.save(appUser);*/

    }
}