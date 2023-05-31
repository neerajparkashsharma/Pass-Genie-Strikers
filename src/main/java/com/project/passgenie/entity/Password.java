package com.project.passgenie.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "Passwords")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Password {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "website")
    @NotEmpty(message = "Website must not be empty")
    private String website;

    @Column(name = "user_name")
    @NotEmpty(message = "User name must not be empty")
    private  String userName;

    @Column(name = "password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one number")
    private String password;

    @Column(name = "notes")
    private String notes;

    @Column(name = "user_id")
    @NotNull
    private Long userId;

    @Column(name = "is_active")
    @NotNull
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
}
