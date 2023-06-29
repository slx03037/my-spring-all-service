package spring.boot.easy.excel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;
import spring.boot.easy.excel.assmbler.GenderConverter;

import java.util.Date;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-29 14:17
 **/

/**
 * @ExcelProperty： 核心注解，value属性可用来设置表头名称，converter属性可以用来设置类型转换器；
 *
 * @ColumnWidth： 用于设置表格列的宽度；
 *
 * @DateTimeFormat： 用于设置日期转换格式；
 *
 * @NumberFormat： 用于设置数字转换格式。
 */
@Data
public class UserDO {
    @ExcelProperty("用户编号")
    @ColumnWidth(20)
    private Long id;

    @ExcelProperty("用户名")
    @ColumnWidth(20)
    private String username;

    @ExcelIgnore
    private String password;

    @ExcelProperty("昵称")
    @ColumnWidth(20)
    private String nickname;

    @ExcelProperty("生日")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd")
    private Date birthday;

    @ExcelProperty("手机号")
    @ColumnWidth(20)
    private String phone;

    @ExcelProperty("身高（米）")
    @NumberFormat("#.##")
    @ColumnWidth(20)
    private Double height;

    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    @ColumnWidth(10)
    private Integer gender;
}
