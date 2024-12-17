package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.repository.RoleRepository;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Set<Role> getRolesByName(List<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = roleRepository.findByRole(roleName);
            if (role != null) {
                roles.add(role);
            }
        }
        return roles;
    }

    // В RoleServiceImpl
    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findByRole(roleName);
    }

    @Override
    public Set<Role> getRolesByIds(List<Long> roleIds) {
        return new HashSet<>(roleRepository.findAllById(roleIds));
    }
}