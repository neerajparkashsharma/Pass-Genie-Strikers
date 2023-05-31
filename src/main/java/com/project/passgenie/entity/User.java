package com.project.passgenie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "Users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    @NotBlank(message = "User name must not be empty")
    private String userName;

    @Column(name = "password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one number")
    private String password;

    @Column(name = "email_address")
    @NotBlank(message = "Email address must not be empty")
    private String emailAddress;

    @Column(name = "phone_number")
    @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$", message = "Invalid phone number")
    private String phoneNumber;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_by")
    @NotNull("Created by must not be null")
    private Long createdBy;

    @Column(name = "created_date")
    private Date createdOn;

    @Column(name = "updated_by")
    @NotNull("Updated by must not be null")
    private Long updatedBy;

    @Column(name = "updated_date")
    private Date updatedOn;

    @Column(name = "activation_date")
    private  Date activationDate;

    @Column(name = "deactivation_date")
    private  Date deactivationDate;
}
