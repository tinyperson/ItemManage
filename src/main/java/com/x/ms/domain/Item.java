package com.x.ms.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;


@Getter
@Setter
public class Item {
    private int id;
    private int num;
    private String name;
    private String type;
    private String brand;
    private String specification;
    private int count;
    private double price;
    private String reporter;
    private String location;
    private String shelf_life;
    private Date production_date;
}
