package com.secbro.springboot.eaysexcel.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.google.common.collect.Lists;
import com.secbro.springboot.eaysexcel.entity.Message;
import com.secbro.springboot.eaysexcel.entity.RoadDataNew;
import com.secbro.springboot.eaysexcel.service.RoadDataService;
import com.secbro.springboot.eaysexcel.util.EasyExcelUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoadDataServiceImpl implements RoadDataService {


    @Override
    public void downloadRow(HttpServletResponse response) {
        String fileName;
        try {
            List<RoadDataNew> list = Lists.newArrayList();
            RoadDataNew roadDataNew = new RoadDataNew();
             roadDataNew.setRepYear("2023");
             roadDataNew.setRoadCode("路段编码");

            list.add(roadDataNew);
            fileName = "文件名称.xlsx";
            fileName = new String(URLEncoder.encode(fileName, "UTF-8").getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", " attachment;filename=" + fileName);
            response.setContentType("application/octet-stream");
            ServletOutputStream out = response.getOutputStream();
            EasyExcel.write(out, RoadDataNew.class).sheet("学生列表")
                    .registerWriteHandler(getStyleStrategy())
                    .doWrite(list);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void uploadRow(MultipartFile file,HttpServletRequest request,HttpServletResponse response) {
        Message message = new Message();
        EasyExcelUtils easyExcelUtils = new EasyExcelUtils(RoadDataNew.class);  //创建工具类时传递class，用于后面比对表头使用
        try {
            request.setCharacterEncoding("utf-8");
            response.setCharacterEncoding("utf-8");
            InputStream fileInput = file.getInputStream(); // 输入流
            EasyExcel.read(fileInput,RoadDataNew.class,easyExcelUtils).sheet().doRead();
            message = easyExcelUtils.getMessage();
            if(Message.OK == "ok") {   //解析完成没有错误
                List<Object> list = easyExcelUtils.getList();   //使用Object类型存放集合
                if (null != list && list.size() > 0) {
                     //获取到所有的政区集合
                    Date nowDate = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowDateStr = sdf.format(nowDate);
                    List<RoadDataNew> roadDataList = new ArrayList<RoadDataNew>();
                    for (int i = 0;i < list.size();i++) { //设置其他非excel字段的值
                        RoadDataNew roadData = (RoadDataNew) list.get(i);
                        roadData.setId("1");
                        roadData.setCreateTime(nowDate);
                        roadData.setCreateUserCode("zhangsan");
                        roadData.setCreateUserName("张三");
                        roadData.setUnit("样管单位");
                        roadData.setPci_score(new BigDecimal(1.0));
                        roadData.setRqi_score(new BigDecimal(2.0));
                        roadData.setRdi_score(new BigDecimal(3.0));
                        roadDataList.add(roadData);
                    }
                    String isClearFlag = request.getParameter("isClearFlag");
                    if("true".equals(isClearFlag)) {   //先清除数据再插入,只清楚在这次之前添加的数据
                        String hql = "delete FROM score.road_data WHERE  DATE_FORMAT(create_time, '%Y-%m-%d %H:%i:%s' ) < '"+nowDateStr+"' ";
                        // this.getDao().saveOrUpdateBySql(hql, null);
                    }
                   // roadDataRepository.save(roadDataList);
                    message.setType(Message.OK);
                    message.setMsg("文件上传解析成功");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message messageListener = easyExcelUtils.getMessage();
            if(null == messageListener || null == messageListener.getType()) {
                message.setType(Message.ERROR);
                message.setMsg("上传解析文件错误");
            }else {
                message = messageListener;
            }
        }

    }

    private WriteHandler getStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 设置对齐（左对齐）
        //headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        // 设置对齐（居中对齐）
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 背景色, 设置为白色，也是默认颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 12);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 设置居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        // 设置内容字体不加粗
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setBold(false);
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;

    }
}
