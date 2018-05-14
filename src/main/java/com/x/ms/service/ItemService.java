package com.x.ms.service;

import com.github.pagehelper.PageHelper;
import com.x.ms.domain.Borrow;
import com.x.ms.domain.Item;
import com.x.ms.domain.User;
import com.x.ms.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemMapper itemMapper;

    public String add(Item item) {
        itemMapper.add_one_item(item);
        return "成功插入";
    }

    public List<Item> get_all(){
//        PageHelper.startPage(1,10);
        List<Item> list_item = itemMapper.get_all();
        return  list_item;
    }

    public Item get_One_by_id(int id) {
        Item item = itemMapper.get_One_by_id(id);
        return item;
    }


    public void change_item_by_id(Item item) {
        itemMapper.change_item_by_id(item);
    }

    public String borrow(Item item, User user, int count) {
        Date now = new Date(new java.util.Date().getTime());

//        System.out.print(user.getUsername());
//        System.out.print(user.getJobNum());


        Borrow borrow = new Borrow();
//        Borrow borrow = null;

        borrow.setUsername(user.getUsername());
        borrow.setJobNum(user.getJobNum());
        borrow.setItemId(item.getId());
        borrow.setItemNum(item.getNum());
        borrow.setItemName(item.getName());
        borrow.setCount(count);
        borrow.setState("申请中");
        borrow.setBorrowDate(now);

        itemMapper.add_borrow_apply(borrow);

        item.setIn(item.getIn() - count);
        item.setTemp(count);

        itemMapper.update_item_borrow(item);

        return "申请成功";
    }

    public List<Borrow> get_borrow_all() {
        List<Borrow>  borrowList = itemMapper.get_borrow_all();
        return borrowList;
    }

    public void admin_borrow_decide(int borrow_id, String decision) {
        itemMapper.change_state_by_id(decision,borrow_id);
    }

    public List<Borrow> get_my_borrow_all(String jobNum) {
        List<Borrow> borrowList = itemMapper.get_all_by_jobNum(jobNum);
        return borrowList;
    }
}
