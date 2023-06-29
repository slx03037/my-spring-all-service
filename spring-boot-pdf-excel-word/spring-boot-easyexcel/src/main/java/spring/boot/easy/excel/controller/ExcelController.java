package spring.boot.easy.excel.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.boot.easy.excel.entity.OrderBO;
import spring.boot.easy.excel.entity.OrderDO;
import spring.boot.easy.excel.entity.UserDO;
import spring.boot.easy.excel.handler.ExcelMergeStrategy;
import spring.boot.easy.excel.mapper.OrderMapper;
import spring.boot.easy.excel.mapper.UserMapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-29 14:21
 **/

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private OrderMapper orderMapper;

    @GetMapping("/export/user")
    public void exportUserExcel(HttpServletResponse response) {
        System.out.println("------------------1");
        try {
            this.setExcelResponseProp(response, "用户列表");
            List<UserDO> userList = this.getUserList1();
            EasyExcel.write(response.getOutputStream())
                    .head(UserDO.class)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("用户列表")
                    .doWrite(userList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置响应结果
     *
     * @param response    响应结果对象
     * @param rawFileName 文件名
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    private void setExcelResponseProp(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }

    /**
     * 读取用户列表数据
     *
     * @return 用户列表数据
     * @throws IOException IO异常
     */
    private List<UserDO> getUserList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource classPathResource = new ClassPathResource("mock/users.json");
        InputStream inputStream = classPathResource.getInputStream();
        return objectMapper.readValue(inputStream, new TypeReference<List<UserDO>>() {
        });
    }

    private List<UserDO> getUserList1() throws IOException {
        System.out.println("-------------------");
        return userMapper.excelUserList();
    }

    @GetMapping("/export/order")
    public void exportOrderExcel(HttpServletResponse response) {
        try {
            this.setExcelResponsePropOrder(response, "订单列表");
//            List<OrderDO> orderList = this.getOrderList();
//            List<OrderBO> exportData = this.convert(orderList);
            List<OrderBO> exportData=orderMapper.getOrderlist();
            EasyExcel.write(response.getOutputStream())
                    .head(OrderBO.class)
                    .registerWriteHandler(new ExcelMergeStrategy(OrderBO.class))
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet("订单列表")
                    .doWrite(exportData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 设置响应结果
     *
     * @param response    响应结果对象
     * @param rawFileName 文件名
     * @throws UnsupportedEncodingException 不支持编码异常
     */
    private void setExcelResponsePropOrder(HttpServletResponse response, String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }

//    private List<OrderDO>  getOrderList() throws IOException {
//        return orderMapper.getOrderlist();
//    }


}
