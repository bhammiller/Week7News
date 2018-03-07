package com.example.demo.Repositories;

import com.example.demo.Models.AppRole;
import org.springframework.data.repository.CrudRepository;

public interface AppRoleRepository extends CrudRepository<AppRole,Long> {

    AppRole findAppRoleByRoleName(String roleName);
}
