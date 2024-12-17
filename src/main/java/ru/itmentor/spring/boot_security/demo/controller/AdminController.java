package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Set;

@Controller

@PreAuthorize("hasRole('ADMIN')") // Гарантирует, что все методы требуют роль ROLE_ADMIN
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/index";
    }

    // Форма для создания нового пользователя
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        User user=new User();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("user", user);
        model.addAttribute("allRoles", roles);
        return "admin/create";
    }

    @PostMapping("/create")
    //@ModelAttribute("user"): Указывает, что данные из формы следует привязать к объекту User, который в модели был под именем "user".
    //@Valid: Запускает валидацию объекта User на основе аннотаций валидации в классе User.
    //@RequestParam("roles") List<Long> rolesIds   Получение списка выбраных ролей
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @RequestParam("roles") List<Long> rolesIds,
                             Model model) {
        if (userService.existsByUserName(user.getUserName())) {
            bindingResult.rejectValue("userName", "error.user", "Пользователь с таким именем уже существует");
        }
        //Проверка наличия ошибок валидации
        if (bindingResult.hasErrors()) {
            //Обеспечиваем наличие всех ролей для отображения в форме при повторном рендеринге страницы.
            model.addAttribute("allRoles", roleService.getAllRoles());
            // Логирование всех ошибок
            bindingResult.getAllErrors().forEach(error -> {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    logger.info("Ошибка в поле '{}': {}", fieldError.getField(), fieldError.getDefaultMessage());
                } else {
                    logger.info("Глобальная ошибка: {}", error.getDefaultMessage());
                }
            });

            return "admin/create";
        }
        if (rolesIds != null && !rolesIds.isEmpty()) {
            Set<Role> userRoles = roleService.getRolesByIds(rolesIds);
            user.setRoles(userRoles);
        }

        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.getAllRoles());
        logger.info("--->>   @GetMapping(/edit/{id}) user={}", userService.getUserById(id));
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable Long id,
                            @ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult,
                             @RequestParam("roles") List<Long> rolesIds,
                             Model model) {
        if (userService.existsByUserNameAndIdNot(user.getUserName(), id)) {
            bindingResult.rejectValue("userName", "error.user", "Пользователь с таким именем уже существует");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.getAllRoles());
            // Логирование всех ошибок
            bindingResult.getAllErrors().forEach(error -> {
                if (error instanceof FieldError) {
                    FieldError fieldError = (FieldError) error;
                    logger.info("Ошибка в поле '{}': {}", fieldError.getField(), fieldError.getDefaultMessage());
                } else {
                    logger.info("Глобальная ошибка: {}", error.getDefaultMessage());
                }
            });

            return "admin/edit";
        }
        if (rolesIds != null && !rolesIds.isEmpty()) {
            Set<Role> userRoles = roleService.getRolesByIds(rolesIds);
            user.setRoles(userRoles);
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/";
    }
}
