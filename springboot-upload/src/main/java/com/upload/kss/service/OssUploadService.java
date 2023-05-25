package com.upload.kss.service;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName OssUploadService
 * @Description TODO
 * @Author Gientech
 * @Date 2023/5/25 15:06
 * @Version 1.0
 **/

@Service
public class OssUploadService {


    @Value("${endpoint}")
    private String endpoint1;

    public String uploadFile(MultipartFile multipartFile,String dir){
        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint =endpoint1;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "LTAI5tKe9Qqs6uMdVcHp9xTb";

        String accessKeySecret =  "AM40yP3i4oetUo45Pd21lZ3oX0Rnyo";
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "dabing-doc";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            // 创建存储空间。
            ossClient.createBucket(bucketName);
            // 获取上传文件的输入流。
            InputStream inputStream = null;
            try {
                inputStream = multipartFile.getInputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //  构建日期目录
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = simpleDateFormat.format(new Date());
            // 获取文件名
            String originame = multipartFile.getOriginalFilename();
            String fileName = UUID.randomUUID().toString();
            // 获取文件后缀名
            String suffix = originame.substring(originame.lastIndexOf("."));
            // 拼接文件名
            String newName = fileName + suffix;
            String finalName= datePath + "/" + newName;
            // 上传文件流。
            ossClient.putObject(bucketName, finalName, inputStream);
            // 返回url地址
            return "https://"+bucketName+"."+endpoint+"/"+finalName;

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }

        }
        //失败返回空串
        return "";
    }


}
