package com.x.ms.mapper;

import com.x.ms.domain.Borrow;
import com.x.ms.domain.Item;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    void add_one_item(Item item);

    List<Item> get_all();

    Item get_One_by_id(int id);

    void change_item_by_id(Item item);

    void add_borrow_apply(Borrow borrow);

    void update_item_borrow(Item item);

    List<Borrow> get_borrow_all();

    void change_state_by_id(String decision, int borrow_id);

    List<Borrow> get_all_by_jobNum(String jobNum);

    void lend_success(int temp, int id);

    void lend_failed(int temp, int id);

    void user_item_return(int temp, int id);
}
