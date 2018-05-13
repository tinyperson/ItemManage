package com.x.ms.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class User {
    private String jobNum;
    private int userId;
    private String username;
    private String password;
    private int userSign = 0;
}
