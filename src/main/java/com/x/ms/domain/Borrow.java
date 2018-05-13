package com.x.ms.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Data
public class Borrow {
    private int borrowId;
    private String jobNum;
    private String username;
    private int itemId;
    private int itemNum;
    private String itemName;
    private int count;
    private String state;
    private Date borrowDate;
}
