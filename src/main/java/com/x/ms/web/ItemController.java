package com.x.ms.web;

import com.github.pagehelper.PageInfo;
import com.x.ms.domain.Item;
import com.x.ms.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.io.IOException;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

//    @RequestMapping("/admin_It_rec")
//    public String admin_It_rec(){
//        return "admin_It_rec";
//    }



    @RequestMapping("/admin_It_sto")
    public String admin_It_sto(){
        return  "admin_It_sto";
    }

    @GetMapping(value = "/admin_Wh_man_change")
    public String admin_Wh_man_change_Get(){
        return "/admin_Wh_man_change";
    }

    @PostMapping(value = "/admin_Wh_man_change")
    public String admin_Wh_man_change_Post(HttpServletRequest request,
                                           Model model){
        String Item_id = request.getParameter("change_id");
        Item item = itemService.get_One_by_id(Integer.parseInt(Item_id));
        model.addAttribute("Item",item);
        return "admin_Wh_man_change";
    }

    @GetMapping(value = "/admin_It_add")
    public String admin_It_add_Get(){
        return "admin_It_add";
    }

    @PostMapping(value = "/admin_It_add")
    public String admin_It_add_Post(Model model,
                                    @ModelAttribute(value = "item")Item item,
                                    HttpServletResponse response,
                                    HttpSession session){
        String result = itemService.add(item) ;
        if (result.equals("成功插入")){
            session.setAttribute("item",item);
        }
        model.addAttribute("item_result" , result);
        return  response.encodeRedirectURL("/admin_It_sto");
    }

    @GetMapping(value = "/admin_It_change")
    public String admin_It_change_Get(){
        return "admin_It_change";
    }

    @PostMapping(value = "/admin_It_change")
    public void admin_It_change_Post(@ModelAttribute(value = "item")Item item,
//                                    Model model,
//                                    HttpSession session,
                                    HttpServletResponse response
                                    ) throws IOException {
        itemService.change_item_by_id(item);
        response.sendRedirect("/admin_Wh_man");
//                .encodeRedirectURL("/admin_Wh_man");
    }


    @RequestMapping("/admin_Wh_man")
    public String admin_show_item(Model model){
        List<Item> itemList = itemService.get_all();
//        PageInfo<Item> page = new PageInfo<Item>(itemList);
//        model.addAttribute("page",page);
        model.addAttribute("itemList",itemList);
        return "admin_Wh_man";
    }

    @RequestMapping("/user_It_app")
    public String user_show_item(Model model){
        List<Item> itemList = itemService.get_all();
        model.addAttribute("itemList",itemList);
        return "user_It_app";
    }

    @GetMapping(value = "/user_It_app_borrow")
    public String user_It_app_borrow_Get(){
        return "user_It_app_borrow";
    }

    @PostMapping(value = "/user_It_app_borrow")
    public void user_It_app_borrow_Post(){

    }

}
