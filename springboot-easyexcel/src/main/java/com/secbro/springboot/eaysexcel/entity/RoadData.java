package com.secbro.springboot.eaysexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName RoadData
 * @Description TODO
 * @Author Gientech
 * @Date 2023/5/30 9:35
 * @Version 1.0
 **/
public class RoadData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;         //id
    @ExcelProperty(value = "年份",index = 0)
    private String repYear;         //年份

    @ExcelProperty(value = "养管单位",index = 1)
    private String unit;         //养管单位

    @ExcelProperty(value = "路线",index = 2)
    private String roadCode;         //路线编码

    @ExcelProperty(value = "上下行",index = 3)
    private String roadDirectName;         //行车方向

    @ExcelProperty(value = "路段起点",index = 4)
    private BigDecimal startStake;         //起点桩号

    @ExcelProperty(value = "路段终点",index = 5)
    private BigDecimal endStake;         //终点桩号

    @ExcelProperty(value = "长度km",index = 6)
    private BigDecimal roadLength;         //长度

    @ExcelProperty(value = "技术等级",index = 7)
    private String roadGradeName;         //技术等级

    @ExcelProperty(value = "路面类型",index = 8)
    private String paveTypeName;         //路面类型

    @ExcelProperty(value = "PQI",index = 9)
    private BigDecimal pqi;         //PQI

    @ExcelProperty(value = "PCI",index = 10)
    private BigDecimal pci;         //PCI

    @ExcelProperty(value = "RQI",index = 11)
    private BigDecimal rqi;         //rqi

    @ExcelProperty(value = "RDI",index = 12)
    private BigDecimal rdi;         //rdi

    @ExcelProperty(value = "DR",index = 13)
    private BigDecimal dr;         //dr

    @ExcelProperty(value = "IRI",index = 14)
    private BigDecimal iri;         //iri

    @ExcelProperty(value = "RD",index = 15)
    private BigDecimal rd;         //rd

    private BigDecimal pci_score;         //PCI得分

    private BigDecimal rqi_score;         //rqi得分

    private BigDecimal rdi_score;         //rdi得分


    @ExcelProperty(value = "PQI分级",index = 16)
    private String pqiGrade;         //PQI分级

    @ExcelProperty(value = "PCI分级",index = 17)
    private String pciGrade;         //PCI分级

    @ExcelProperty(value = "RQI分级",index = 18)
    private String rqiGrade;         //RQI分级

    @ExcelProperty(value = "RDI分级",index = 19)
    private String rdiGrade;         //RDI分级

    @ExcelProperty(value = "区域",index = 20)
    private String theAreaName;         //区域

    @ExcelProperty(value = "抽检性质",index = 21)
    private String randomNature;         //抽检性质

    @ExcelProperty(value = "PCI整改后得分",index = 22)
    private BigDecimal pciAfter;         //PCI整改后得分

    @ExcelProperty(value = "RQI整改后得分",index = 23)
    private BigDecimal rqiAfter;         //RQI整改后得分

    @ExcelProperty(value = "RDI整改后得分",index = 24)
    private BigDecimal rdiAfter;         //RDI整改后得分

    @ExcelProperty(value = "整改标识",index = 25)
    private String rectificaFlag;         //整改标识（未完成/已完成）

    private String createUserCode;
    private String createUserName;
    private Date createTime;
}