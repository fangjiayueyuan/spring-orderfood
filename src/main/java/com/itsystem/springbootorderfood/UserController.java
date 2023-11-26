package com.itsystem.springbootorderfood;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: springboot-orderfood
 * @package: com.itsystem.springbootorderfood
 * @className: UserController
 * @author: fangjiayueyuan
 * @description: TODO
 * @date: 2023/10/30 10:55
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @RequestMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}
