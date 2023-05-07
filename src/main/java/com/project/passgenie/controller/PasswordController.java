package com.project.passgenie.controller;


import com.project.passgenie.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base.url}/passwords")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;



}
