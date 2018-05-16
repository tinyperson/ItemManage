package com.x.ms.web;

import com.github.pagehelper.PageInfo;
import com.x.ms.domain.Borrow;
import com.x.ms.domain.Item;
import com.x.ms.domain.User;
import com.x.ms.service.ItemService;
import com.x.ms.service.UserService;
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
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

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
    public void admin_It_add_Post(@ModelAttribute(value = "item")Item item,
                                    HttpServletResponse response) throws IOException {
        String result = itemService.add(item) ;
        if (result.equals("成功插入")){
//            session.setAttribute("item",item);
            String itemName = item.getName();
            String location = "window.location='/admin_It_sto' ; ";
            response.setContentType("text/html;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("<script>alert('"+itemName+result+"');" +
                    location +
                    "window.close()" +
                    "</script>");

            out.close();
        }
//        model.addAttribute("item_result" , result);
//        return  response.encodeRedirectURL("/admin_It_sto");
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
        String result = "修改成功";
        String itemName = item.getName();
        String location = "window.location='/admin_Wh_man' ; ";

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write("<script>alert('"+itemName+result+"');" +
                location +
                "window.close()" +
                "</script>");

        out.close();
//        response.sendRedirect("/admin_Wh_man");
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

    @RequestMapping("/admin_It_rec")
    public String admin_show_borrow(Model model){
        List<Borrow> borrowList = itemService.get_borrow_all();
        model.addAttribute("borrowList",borrowList);
        return "admin_It_rec";
    }

    @RequestMapping("/user_It_ret")
    public String user_It_ret_show(Model model,
                                   HttpSession session){
        User tempUser = (User) session.getAttribute("user");
        User user = userService.getOneUser(tempUser);

        List<Borrow> borrowList = itemService.get_my_borrow_all(user.getJobNum());
        model.addAttribute("borrowList",borrowList);
        return "user_It_ret";
    }

    @RequestMapping("/user_It_rec")
    public String user_It_rec_show(Model model,
                                   HttpSession session){
        User tempUser = (User) session.getAttribute("user");
        User user = userService.getOneUser(tempUser);

        List<Borrow> borrowList = itemService.get_my_borrow_all(user.getJobNum());
        model.addAttribute("borrowList",borrowList);
        return "user_It_rec";
    }

    @GetMapping(value = "/admin_It_rec_decide")
    public String admin_It_rec_decide_Get(){
        return "/admin_It_rec_decide";
    }

    @PostMapping(value = "/admin_It_rec_decide")
    public void admin_It_rec_decide_Post(HttpServletRequest request,
                                         HttpServletResponse response) throws IOException {
        int Borrow_id = Integer.parseInt(request.getParameter("decide_borrow_id"));
        int Borrow_count = Integer.parseInt(request.getParameter("decide_borrow_count"));
        int itemId = Integer.parseInt(request.getParameter("decide_borrow_itemId"));
        String decision = null;
        if (request.getParameter("确认") != null && request.getParameter("确认").equals("确认")){
            decision = "已借出";
        }else if (request.getParameter("取消") != null && request.getParameter("取消").equals("取消")){
            decision = "已取消";
        }
        itemService.admin_borrow_decide(Borrow_id,decision , Borrow_count , itemId);
        response.sendRedirect("/admin_It_rec");
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
    public void user_It_app_borrow_Post(HttpServletRequest request,
                                        HttpSession session,
                                        HttpServletResponse response) throws IOException {
        String Borrow_id = request.getParameter("borrow_id");
        int item_in = Integer.parseInt(request.getParameter("item_in"));
        int count = Integer.parseInt(request.getParameter("count"));
        Item item = itemService.get_One_by_id(Integer.parseInt(Borrow_id));

        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String location = "window.location='/user_It_app';";
        String borrowResult;

        if ((0 < count) && (count <= item_in)){
            User tempUser = (User) session.getAttribute("user");
            User user = userService.getOneUser(tempUser);
//        Date now = new Date(new java.util.Date().getTime());
//
//
//        Borrow borrow = null;
//
//        borrow.setUsername(user.getUsername());
//        borrow.setJobNum(user.getJobNum());
//        borrow.setItemId(item.getId());
//        borrow.setItemNum(item.getNum());
//        borrow.setItemName(item.getName());
//        borrow.setCount(count);
//        borrow.setState("申请中");
//        borrow.setBorrowDate(now);

            borrowResult = itemService.borrow(item,user,count);

            out.write("<script>alert('"+borrowResult+"');" +
                    location +
                    "window.close()" +
                    "</script>");

        }else{
            borrowResult = "数量填写不正确";
            out.write("<script>alert('"+borrowResult+"');" +
                    location +
                    "window.close()" +
                    "</script>");
        }
        out.close();
    }

    @GetMapping(value = "/user_It_ret_handle")
    public String user_It_ret_handle_Get(){
        return "user_It_ret_handle";
    }

    @PostMapping(value = "/user_It_ret_handle")
    public void user_It_ret_handle_Post(HttpServletResponse response,
                                        HttpServletRequest request) throws IOException {
        int Borrow_id = Integer.parseInt(request.getParameter("decide_borrow_id"));
        int Borrow_count = Integer.parseInt(request.getParameter("decide_borrow_count"));
        int itemId = Integer.parseInt(request.getParameter("decide_borrow_itemId"));
        String state = "已归还";
        itemService.user_return_item(Borrow_id, state , Borrow_count , itemId);

        response.sendRedirect("user_It_ret");
    }
}
