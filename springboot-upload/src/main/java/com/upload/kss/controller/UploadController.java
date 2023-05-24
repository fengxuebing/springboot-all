package com.upload.kss.controller;


import com.upload.kss.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload/file")
    @ResponseBody
    public String upload(@RequestParam("file")MultipartFile multipartFile, HttpServletRequest request){
         if (multipartFile.isEmpty()){
              return "文件有误";
         }
        long size = multipartFile.getSize();
        String dir = request.getParameter("dir");
        return uploadService.uploadIMG(multipartFile, dir);

    }


}
