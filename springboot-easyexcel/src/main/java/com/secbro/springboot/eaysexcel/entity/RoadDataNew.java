package com.secbro.springboot.eaysexcel.entity;

/**
 * @ClassName RoadDataNew
 * @Description TODO
 * @Author Gientech
 * @Date 2023/5/18 9:17
 * @Version 1.0
 **/

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 添加此类是处理复杂表头的情况
 * @author qingyun
 * @Date 2021年5月19日 下午4:51:26
 */
public class RoadDataNew implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelIgnore
    private String id;         //id
    @ExcelProperty(value = {"年份","年份"},index = 0)
    private String repYear;         //年份

    @ExcelProperty(value = {"养管单位","养管单位"},index = 1)
    private String unit;         //养管单位

    @ExcelProperty(value = {"路线情况","路线"},index = 2)
    private String roadCode;         //路线编码

    @ExcelProperty(value = {"路线情况","上下行"},index = 3)
    private String roadDirectName;         //行车方向

    @ExcelProperty(value = {"路段起点","路段起点"},index = 4)
    private BigDecimal startStake;         //起点桩号

    @ExcelProperty(value = {"路段终点","路段终点"},index = 5)
    private BigDecimal endStake;         //终点桩号

    @ExcelProperty(value = {"长度km","长度km"},index = 6)
    private BigDecimal roadLength;         //长度

    @ExcelProperty(value = {"路面情况","技术等级"},index = 7)
    private String roadGradeName;         //技术等级

    @ExcelProperty(value = {"路面情况","路面类型"},index = 8)
    private String paveTypeName;         //路面类型

    @ExcelProperty(value = {"PQI","PQI"},index = 9)
    private BigDecimal pqi;         //PQI

    @ExcelProperty(value = {"PCI","PCI"},index = 10)
    private BigDecimal pci;         //PCI

    @ExcelProperty(value = {"RQI","RQI"},index = 11)
    private BigDecimal rqi;         //rqi

    @ExcelProperty(value = {"RDI","RDI"},index = 12)
    private BigDecimal rdi;         //rdi

    @ExcelProperty(value = {"DR","DR"},index = 13)
    private BigDecimal dr;         //dr

    @ExcelProperty(value = {"IRI","IRI"},index = 14)
    private BigDecimal iri;         //iri

    @ExcelProperty(value = {"RD","RD"},index = 15)
    private BigDecimal rd;         //rd

    @ExcelIgnore
    private BigDecimal pci_score;         //PCI得分

    @ExcelIgnore
    private BigDecimal rqi_score;         //rqi得分

    @ExcelIgnore
    private BigDecimal rdi_score;         //rdi得分


    @ExcelProperty(value = {"PQI分级","PQI分级"},index = 16)
    private String pqiGrade;         //PQI分级

    @ExcelProperty(value = {"PCI分级","PCI分级"},index = 17)
    private String pciGrade;         //PCI分级

    @ExcelProperty(value = {"RQI分级","RQI分级"},index = 18)
    private String rqiGrade;         //RQI分级

    @ExcelProperty(value = {"RDI分级","RDI分级"},index = 19)
    private String rdiGrade;         //RDI分级

    @ExcelProperty(value = {"区域","区域"},index = 20)
    private String theAreaName;         //区域

    @ExcelProperty(value = {"抽检性质","抽检性质"},index = 21)
    private String randomNature;         //抽检性质

    @ExcelProperty(value = {"PCI整改后得分","PCI整改后得分"},index = 22)
    private BigDecimal pciAfter;         //PCI整改后得分

    @ExcelProperty(value = {"RQI整改后得分","RQI整改后得分"},index = 23)
    private BigDecimal rqiAfter;         //RQI整改后得分

    @ExcelProperty(value = {"RDI整改后得分","RDI整改后得分"},index = 24)
    private BigDecimal rdiAfter;         //RDI整改后得分

    @ExcelProperty(value = {"整改标识","整改标识"},index = 25)
    private String rectificaFlag;         //整改标识（未完成/已完成）

    @ExcelIgnore
    private String createUserCode;

    @ExcelIgnore
    private String createUserName;

    @ExcelIgnore
    private Date createTime;
}
