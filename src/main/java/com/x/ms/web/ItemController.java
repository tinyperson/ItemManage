package com.x.ms.web;

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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/admin_It_rec")
    public String admin_It_rec(){
        return "admin_It_rec";
    }

    @RequestMapping("/admin_It_sto")
    public String admin_It_sto(){
        return  "admin_It_sto";
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
//
//    @PostMapping(value = "/show_item")
//    public ResponseEntity<Response> admin_show_item(){
//
//    }
}
