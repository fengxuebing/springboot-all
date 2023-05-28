package com.secbro.springboot.eaysexcel.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.util.StringUtils;
import com.secbro.springboot.eaysexcel.entity.Message;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @Description 使用easyexcel方式解析数据的工具类
 * @author qingyun
 * @Date 2021年5月19日 上午10:35:58
 */
@Component
public class EasyExcelUtils extends AnalysisEventListener<Object> {

    List<Object> list = new ArrayList<Object>();

    Message message = new Message();


    Class clazz;


    public EasyExcelUtils() {
        super();
        // TODO Auto-generated constructor stub
    }

    public EasyExcelUtils(Class clazz) {
        super();
        this.clazz = clazz;
    }

    /**
     * @Description invoke方法为一行一行读取excel内容
     * @author qingyun
     * @Date 2021年5月19日 上午10:43:17
     */
    @Override
    public void invoke(Object data, AnalysisContext context) {
        list.add(data);
    }

    /**
     * @Description invokeHeadMap读取excel表头，校验表头是否正确
     * @author qingyun
     * @Date 2021年5月19日 上午10:44:43
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Map<Integer, String> head = new HashMap<>();
        try {
            head = getIndexNameMap(clazz);   //通过class获取到使用@ExcelProperty注解配置的字段
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        Set<Integer> keySet = head.keySet();  //解析到的excel表头和实体配置的进行比对
        for (Integer key : keySet) {
            if (StringUtils.isEmpty(headMap.get(key))) {
                message.setType(Message.ERROR);
                message.setMsg("表头第"+key+1+"列为空，请参照模板填写");
                throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel");
            }
            if (!headMap.get(key).equals(head.get(key))) {
                message.setType(Message.ERROR);
                message.setMsg("表头第"+key+1+"列【"+headMap.get(key)+"】与模板【"+head.get(key)+"】不一致，请参照模板填写");
                throw new ExcelAnalysisException("解析excel出错，请传入正确格式的excel");
            }
        }
    }

    /**
     * @Description 读取完成之后进行的处理
     * @author qingyun
     * @Date 2021年5月19日 上午10:45:13
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        if (null == message || message.getType() == null) {
            message.setMsg("数据解析完成");
            message.setType(Message.OK);
        }
    }

    /**
     * @Description 通过class获取类字段信息
     * @author qingyun
     * @Date 2021年5月19日 下午1:41:47
     */
    public Map<Integer, String> getIndexNameMap(Class clazz) throws NoSuchFieldException {
        Map<Integer, String> result = new HashMap<>();
        Field field;
        Field[] fields = clazz.getDeclaredFields();     //获取类中所有的属性
        for (int i = 0; i < fields.length; i++) {
            field = clazz.getDeclaredField(fields[i].getName());
            field.setAccessible(true);
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);//获取根据注解的方式获取ExcelProperty修饰的字段
            if (excelProperty != null) {
                int index = excelProperty.index();         //索引值
                String[] values = excelProperty.value();   //字段值
                StringBuilder value = new StringBuilder();
                for (String v : values) {
                    value.append(v);
                }
                result.put(index, value.toString());
            }
        }
        return result;
    }

    /**
     * @Description 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     * @author qingyun
     * @Date 2021年5月19日 下午3:02:49
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            message.setType(Message.ERROR);
            message.setMsg("第"+(excelDataConvertException.getRowIndex()+1)+"行，第"+(excelDataConvertException.getColumnIndex()+1)+"列解析异常，请参照模板填写");
        }
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }


}
