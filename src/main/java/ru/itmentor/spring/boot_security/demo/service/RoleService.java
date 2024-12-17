package ru.itmentor.spring.boot_security.demo.service;

import ru.itmentor.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> getAllRoles();
    Set<Role> getRolesByName(List<String> roleNames);
    Set<Role> getRolesByIds(List<Long> roleIds);
    void saveRole(Role role);
    Role findByRoleName(String roleName);
}