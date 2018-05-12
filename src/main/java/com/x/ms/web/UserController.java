package com.x.ms.web;

import com.x.ms.domain.User;
import com.x.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/admin_index")
    public String admin_index(){
        return "admin_index";
    }

    @RequestMapping("/user_index")
    public String user_index(){
        return "user_index";
    }

    @RequestMapping("")
    public String system_index(){
        return "login";
    }

    @RequestMapping("/admin_Us_man")
    public String admin_Us_man(){
        return "admin_Us_man";
    }

    @GetMapping(value = "/login")
    public String login_Get(){
        return "login";
    }

    @PostMapping(value = "/login")
    public void login_Post(Model model,
                            @ModelAttribute(value = "user")User user,
                            HttpServletResponse response,
                            HttpSession session) throws IOException {
        String result = userService.login(user);
        if (result.equals("登陆成功")){
            session.setAttribute("user",user);

            response.setContentType("text/html;charset=utf-8");
            String location = null;

            if (user.getUserSign() == 1){
                location = "window.location='/admin_index' ; ";
            }else if (user.getUserSign() == 0){
                location = "window.location='/user_index' ;";
            }

            PrintWriter out = response.getWriter();
            out.write("<script>alert('登陆成功！');" +
                    location +
                    "window.close()" +
                    "</script>");

            out.close();
        }else if (result.equals("用户不存在")){
            response.setContentType("text/html;charset=utf-8");


            PrintWriter out = response.getWriter();
            out.write("<script>alert('用户不存在！');" +
                    "window.location='/' ;" +
                    "window.close()" +
                    "</script>");

            out.close();
        }else if (result.equals("密码错误")){
            response.setContentType("text/html;charset=utf-8");


            PrintWriter out = response.getWriter();
            out.write("<script>alert('密码错误！');" +
                    "window.location='/' ;" +
                    "window.close()" +
                    "</script>");

            out.close();
        }
//        model.addAttribute("user_result",result);
//        return response.encodeRedirectURL("/admin_index");

    }

    @RequestMapping(value = "/loginOut")
    public void loginOut(HttpSession session,
                         HttpServletResponse response) throws IOException {
        session.removeAttribute("user");
        response.sendRedirect("/");
    }

    @GetMapping(value = "/admin_Us_add")
    public String admin_Us_add_Get(){
        return "admin_Us_add";
    }

    @PostMapping(value = "/admin_Us_add")
    public void admin_Us_add_Post(Model model,
                                    HttpServletResponse response,
                                    @ModelAttribute(value = "user")User user) throws IOException {
        String result = userService.register(user);
        model.addAttribute("user_result", result);

//        response.setContentType("text/html;charset=utf-8");
//        PrintWriter out = response.getWriter();
//        out.write("<script>alert('该数据已经同步完成');</script>");
//        out.flush();
//
//        response.sendRedirect("/admin_Us_man");
        response.setContentType("text/html;charset=utf-8");


        PrintWriter out = response.getWriter();
        out.write("<script>alert('添加成功！');" +
                "window.location='/admin_Us_man' ;" +
                "window.close()" +
                "</script>");

        out.close();
    }
}
