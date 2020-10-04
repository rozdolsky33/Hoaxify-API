package com.hoaxify.enteties;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private long id;

    private String username;
    private String displayName;
    private String password;


}
