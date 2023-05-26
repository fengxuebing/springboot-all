package com.upload.kss.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {


    @GetMapping("/upload")
    public String upload(){
        return "upload";
    }


    @GetMapping("/editor")
    public String editor(){
        return "wangeditor";
    }

}
