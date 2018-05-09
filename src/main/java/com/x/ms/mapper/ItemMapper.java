package com.x.ms.mapper;

import com.x.ms.domain.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    void add_one_item(Item item);

    List<Item> get_all();

    Item get_One_by_id(int id);

    void change_item_by_id(Item item);
}
