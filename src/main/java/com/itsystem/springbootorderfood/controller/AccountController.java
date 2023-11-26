package com.itsystem.springbootorderfood.controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itsystem.springbootorderfood.pojo.Customer;
import com.itsystem.springbootorderfood.pojo.User;
import com.itsystem.springbootorderfood.service.AccountService;
import com.itsystem.springbootorderfood.service.CustomerService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood.controller
 * @className: AccountController
 * @author: fangjiayueyuan
 * @description: 管理员账户登录或普通用户账户登录
 * @date: 2023/10/30 12:45
 * @version: 1.0
 */
@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CustomerService customerService;
    /**
     * 用户登录方法
     * @param userName 用户名
     * @param userPwd 用户密码
     * @param model Model对象，用于数据传递
     * @param session HttpSession对象，用于保存用户登录状态【所有请求共享】
     * @return 返回视图名称
 */
    @RequestMapping("login")
    private String login(String userName, String userPwd, Model model, HttpSession session){
//        model.addAttribute("msg",null);
        // 方法实现部分
        if(userName != null && userName.equals("admin")){
            boolean b=accountService.login(userName,userPwd);
            if(b){
                QueryWrapper<User>qw=new QueryWrapper<>();
                qw.eq("username",userName);
                User one = accountService.getOne(qw);
                session.setAttribute("currentUser",one.getUsername());
                session.setAttribute("password",userPwd);
                session.setAttribute("email",one.getEmail());
                session.setAttribute("image",one.getImage());
                return "foodMainMenu";
            }else{
                model.addAttribute("msg","用户名或密码错误");
                return "index";
            }
        }else{
            boolean b = customerService.login(userName, userPwd);
            if(b){
                QueryWrapper<Customer> qw = new QueryWrapper<>();
                qw.eq("customer_name", userName);
                Customer one = customerService.getOne(qw);
                session.setAttribute("currentUser", one.getCustomerName());
                session.setAttribute("password", userPwd);
                System.out.println("att");
                System.out.println(userPwd);
                session.setAttribute("email", one.getEmail());
                session.setAttribute("image", one.getCimage());
                session.setAttribute("userId", one.getId());
                return "foodMainMenu1";
            }else{
                model.addAttribute("msg","用户名或密码错误");
                return "index";
            }
        }
    }

    @RequestMapping("count")
    public String chartCount() {
        return "chart_count";
    }

    @RequestMapping("total")
    public String chartTotal() {
        return "chart_total";
    }

    @RequestMapping("pwd")
    public String pwd() {
        return "modify";
    }

    @PostMapping("pwdUser")
    public String pwdUser(String userPwd,String newPwd,HttpSession session,Model model) {
        String currentUser = (String) session.getAttribute("currentUser");
        System.out.println(currentUser);
        boolean login = accountService.login(currentUser, userPwd);
        if(login){
            User user = new User();
            user.setUsername(currentUser);
            user.setPassword(DigestUtil.md5Hex(newPwd));
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("username",currentUser);
            boolean b = accountService.update(user, qw);
            if(b){
                model.addAttribute("successMsg", "Password updated successfully");
                return "index";
            }else{
                model.addAttribute("loginFail", "Failed to update password");
            }
        }else{
            model.addAttribute("loginFail", "Incorrect password");
        }

        return "modify";
    }

    @RequestMapping("toRegister")
    public String toRegister() {
        return "register";
    }


    /**
     * @param userName:
     * @param userPwd:
     * @param confirmPwd:
     * @param model:
     * @return String
     * @author jiayueyuanfang
     * @description 注册功能
     * @date 2023/11/10 07:57
     */

    @RequestMapping("register")
    public String register(
            @RequestParam("userName") String userName,
            @RequestParam("userPwd") String userPwd,
            @RequestParam("confirmPwd") String confirmPwd,
            Model model
    ){
        if(!userPwd.equals(confirmPwd)){
            model.addAttribute("errorMsg", "两次密码不一致，请重新输入！");
            return "register";
        }
        Customer customer = new Customer();
        customer.setCustomerName(userName);
        customer.setPassword(DigestUtil.md5Hex(userPwd));
        customerService.save(customer);
        return "index";
    }

    /**
     * @param session:
     * @param model:
     * @return String
     * @author jiayueyuanfang
     * @description 个人信息
     * @date 2023/11/12 17:24
     */

    @RequestMapping("profile")
    public String profile(HttpSession session, Model model)
    {
//        System.out.println("attention");
//        System.out.println(session.getAttribute("currentUser"));
//        Enumeration<String> attributeNames = session.getAttributeNames();
//        while(attributeNames.hasMoreElements()){
//            String attributeName = attributeNames.nextElement();
//            Object attributeValue = session.getAttribute(attributeName);
//            System.out.println(attributeName + ": " + attributeValue);
//
//            // attributeName 是属性名称，attributeValue 是属性值
//        }

        String currentUser = (String) session.getAttribute("currentUser");
        String password = (String) session.getAttribute("password");
        if(currentUser.equals("admin")){
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("username",currentUser);
            User user = accountService.getOne(qw);
            user.setPassword(password);
            model.addAttribute("user", user);
            return "profile-admin";
        }else{
            QueryWrapper<Customer> qw = new QueryWrapper<>();
            qw.eq("customer_name",currentUser);
            Customer user = customerService.getOne(qw);
            user.setPassword(password);
            model.addAttribute("user", user);
            return "profile-customer";
        }

    }
    
    
    @RequestMapping("updateAdminProfile")
    public String updateAdminProfile(User user){
        String password = DigestUtil.md5Hex(user.getPassword());
        user.setPassword(password);
        accountService.updateById(user);
        return "index";
    }

    @RequestMapping("updateCustomerProfile")
    public String updateCustomerProfile(Customer user){
        String password = DigestUtil.md5Hex(user.getPassword());
        user.setPassword(password);
        customerService.updateById(user);
        return "index";
    }


    @RequestMapping("toLogin")
    public String register(){
        return "index";
    }


    /**
     * @param : null
     * @return String
     * @author jiayueyuanfang
     * @description 退出功能：直接跳转到登陆页面
     * @date 2023/11/10 07:55
     */
    @RequestMapping("logout")
    public String logout(){
        return "index";
    }
}
