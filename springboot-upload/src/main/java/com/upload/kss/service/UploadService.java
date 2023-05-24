package com.upload.kss.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class UploadService {

    /**
     *   multipartFile 接受文件对象
     */

    public String uploadIMG(MultipartFile multipartFile,String dir){
        // 指定文件上传的目录
        File targetFile = new File("D://mysoft//temp/"+dir);
        try {
            if (!targetFile.exists())targetFile.mkdir();
            // 截取文件名
            String filename = multipartFile.getOriginalFilename();
            String imgsuffix = filename.substring(filename.lastIndexOf("."));
            String s = UUID.randomUUID().toString() + imgsuffix;

            // 指定上传以后得目录
            File file = new File(targetFile, imgsuffix);
            // 2、文件上传到指定的目录
            multipartFile.transferTo(file);
            return "ok";

        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

    }



}
