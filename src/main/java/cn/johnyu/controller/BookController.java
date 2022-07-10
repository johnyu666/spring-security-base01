package cn.johnyu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    @GetMapping("/books/find")
    public String findAllBooks(){
        return "返回所有的图书(USER可见)";
    }
    @GetMapping("/books/del")
    public String deleteBooks(){
        return "删除图书（ADMIN可见）";
    }
}
