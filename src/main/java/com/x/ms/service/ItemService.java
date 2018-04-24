package com.x.ms.service;

import com.x.ms.domain.Item;
import com.x.ms.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;

    public String add(Item item) {
        itemMapper.add_one_item(item);
        return "成功插入";
    }
}
