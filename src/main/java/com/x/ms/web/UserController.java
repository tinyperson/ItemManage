package com.x.ms.web;

import com.x.ms.domain.User;
import com.x.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String admin_index(Model model){
        return "admin_index";
    }

    @RequestMapping("/admin_Us_man")
    public String admin_Us_man(){
        return "admin_Us_man";
    }

    @GetMapping(value = "/admin_login")
    public String admin_login_Get(){
        return "admin_login";
    }

    @PostMapping(value = "/admin_login")
    public String admin_login_Post(Model model,
                            @ModelAttribute(value = "user")User user,
                            HttpServletResponse response,
                            HttpSession session){
        String result = userService.login(user);
        if (result.equals("登陆成功")){
            session.setAttribute("user",user);
        }
        model.addAttribute("user_result",result);
        return response.encodeRedirectURL("/admin_index");

    }

    @GetMapping(value = "/admin_Us_add")
    public String admin_Us_add_Get(){
        return "admin_Us_add";
    }

    @PostMapping(value = "/admin_Us_add")
    public String admin_Us_add_Post(Model model,
                                    HttpServletResponse response,
                                    @ModelAttribute(value = "user")User user){
        String result = userService.register(user);
        model.addAttribute("user_result", result);
        return response.encodeRedirectURL("/admin_index");
    }
}
