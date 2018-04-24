package com.x.ms.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private int userId;
    private String username;
    private String password;
    private int userSign = 0;

}
