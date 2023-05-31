package com.project.passgenie.controller;

import com.project.passgenie.entity.Password;
import com.project.passgenie.service.PasswordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("${api.base.url}/passwords")
@Api(tags = "Password Management")
public class PasswordController {

    private final PasswordService passwordService;

    @Autowired
    public PasswordController(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @PostMapping
    public Password createPassword(@Valid @RequestBody Password password) {
        return passwordService.createPassword(password);
    }

    @GetMapping("/user/{userId}")
    public List<Password> getAllPasswordsByUserId(@PathVariable Long userId) {
        return passwordService.getAllPasswordsByUserId(userId);
    }

    @GetMapping("/{passwordId}")
    public Password getPasswordById(@PathVariable Long passwordId) {
        return passwordService.getPasswordById(passwordId);
    }

    @PutMapping("/{passwordId}")
    public Password updatePassword(@PathVariable Long passwordId, @RequestBody Password password) {
        return passwordService.updatePassword(passwordId, password);
    }

    @DeleteMapping("/{userId}/{passwordId}")
    public void deletePassword(@PathVariable Long userId, @PathVariable Long passwordId) {
        passwordService.deletePassword(passwordId, userId);
    }

}
