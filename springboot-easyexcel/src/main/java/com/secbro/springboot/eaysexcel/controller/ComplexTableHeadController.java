package com.secbro.springboot.eaysexcel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.secbro.springboot.eaysexcel.entity.Demo;
import com.secbro.springboot.eaysexcel.service.RoadDataService;
import com.secbro.springboot.eaysexcel.util.CustomizeColumnWidth;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName ComplexTableHeadController
 * @Description TODO
 * @Author Gientech
 * @Date 2023/5/17 16:31
 * @Version 1.0
 **/
@RestController
public class ComplexTableHeadController {

    @Autowired
    private RoadDataService roadDataService;


    @GetMapping("exportData")
    public void exportData(HttpServletResponse response) throws IOException {
        try {
            List<Demo> list = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Demo demo = new Demo();
                demo.setName("姓名" + i);
                demo.setProvince("省" + i);
                demo.setCity("市" + i);
                demo.setPhone("电话" + i);
                list.add(demo);
            }
            //设置返回数据的类型
            response.setContentType("application/msexcel");
            //设置返回数据的字符集编码
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("ParamsConfig", "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), Demo.class)
                    .head(getHeader())
                    .registerWriteHandler(new CustomizeColumnWidth())
                    .registerWriteHandler(getStyleStrategy())
                    .sheet("sheet名称")
                    .doWrite(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //获取自定义表头:1.表头总共占用几行，就要在定义的每个list中添加几条数据 2.表头总共有几列就
    //要定义几个list
    private List<List<String>> getHeader() {
        String first = "报表";
        List<List<String>> list = new ArrayList<List<String>>();
        List<String> head0 = new ArrayList<String>();
        //注意这里是表头的合并上下两行
        head0.add(first);
        head0.add("姓名");
        head0.add("姓名");
        head0.add("姓名");
        //注意这里是合并表头的左右两行
        List<String> head1 = new ArrayList<String>();
        head1.add(first);
        head1.add("地址");
        head1.add("省");
        head1.add("省1");


        List<String> head2 = new ArrayList<String>();
        head2.add(first);
        head2.add("地址");
        head2.add("市");
        head2.add("市1");
        //注意这里是表头的合并上下两行
        List<String> head3 = new ArrayList<String>();
        head3.add(first);
        head3.add("电话");
        head3.add("电话");
        head3.add("电话");

        list.add(head0);
        list.add(head1);
        list.add(head2);
        list.add(head3);

        return list;
    }


    //表格样式策略
    private HorizontalCellStyleStrategy getStyleStrategy() {
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
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);

        // 背景绿色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        // 字体策略
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontHeightInPoints((short) 12);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //设置 自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置 垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置 水平居中
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //设置边框样式
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);

        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现
        HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return horizontalCellStyleStrategy;
    }


    //  excel复杂表头导出 通过easyexcel导出excel 单个sheet
    @RequestMapping(value = { "/easyexcel/download" }, produces = { "text/html;charset=UTF-8" })
    @ResponseBody
    public void download(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        //String id = request.getParameter("id");
        roadDataService.downloadRow(response);
    }

    // Excel单个文件单个sheet导入
    @RequestMapping(value = { "/easyexcel/upload" }, produces = { "text/html;charset=UTF-8" })
    @ResponseBody
    public void upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        roadDataService.uploadRow(multipartFile,request,response);
    }

    // 单个文件多sheet导入
    @RequestMapping(value = { "/easyexcel/uploadManySheet" }, produces = { "text/html;charset=UTF-8" })
    @ResponseBody
    public void uploadManySheet(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        roadDataService.uploadManySheet(multipartFile,request,response);
    }


}
