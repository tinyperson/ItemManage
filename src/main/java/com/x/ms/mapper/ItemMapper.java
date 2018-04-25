package com.x.ms.mapper;

import com.x.ms.domain.Item;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemMapper {
    void add_one_item(Item item);

}
