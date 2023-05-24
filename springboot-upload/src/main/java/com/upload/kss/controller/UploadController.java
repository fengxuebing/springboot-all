package com.upload.kss.controller;


import com.upload.kss.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload/file")
    @ResponseBody
    public Map<String, Object> upload(@RequestParam("file")MultipartFile multipartFile, HttpServletRequest request){
         if (multipartFile.isEmpty()){
              return  Collections.emptyMap();
         }
        long size = multipartFile.getSize();
        String dir = request.getParameter("dir");
        return uploadService.uploadIMG(multipartFile, dir);

    }


}
