package com.secbro.springboot.eaysexcel.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RoadDataService {


    void downloadRow(HttpServletResponse response);

    void uploadRow(MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response);
}
