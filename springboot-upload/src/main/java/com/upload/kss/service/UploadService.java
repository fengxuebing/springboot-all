package com.upload.kss.service;


import freemarker.template.SimpleDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UploadService {

    @Value("${file.uploadFolder}")
    private String uploadPath;
    @Value("${file.staticPath}")
    private String staticPath;
    /**
     *   multipartFile 接受文件对象
     */

    public Map<String, Object> uploadIMG(MultipartFile multipartFile, String dir){
        // 1 指定文件上传的目录
        // File targetFile = new File("D://mysoft//temp/"+dir);
        try {

            // 2 截取文件名
            String filename = multipartFile.getOriginalFilename();
            String imgsuffix = filename.substring(filename.lastIndexOf("."));
            // 3 生成唯一文件名  不使用中文
            String newFileName = UUID.randomUUID().toString() + imgsuffix;
            // 4 生成日期目录
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = simpleDateFormat.format(new Date());
            // 5 拼接日期目录
            String serverPath=uploadPath;
            File targetPath = new File(serverPath+dir, datePath);
            if (!targetPath.exists())targetPath.mkdirs();
            // 6 指定文件上传的服务器的完整文件名
            File targerFileName = new File(targetPath, newFileName);
            // 7、文件上传到指定的目录
            multipartFile.transferTo(targerFileName);
            // 可访问路径返回到页面
            String finalName = dir+"/"+datePath+"/"+newFileName;
            HashMap<String, Object> map = new HashMap<>();
            map.put("url",staticPath+"/upimage"+finalName);
            map.put("size",multipartFile.getSize());

            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



}
