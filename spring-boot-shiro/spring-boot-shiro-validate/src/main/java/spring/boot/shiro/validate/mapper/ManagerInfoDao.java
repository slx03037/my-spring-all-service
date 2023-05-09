package spring.boot.shiro.validate.mapper;

import com.shenlx.xinwen.model.enetity.ManagerInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-08 15:56
 **/
public interface ManagerInfoDao{
    ManagerInfo findByUsername(String username);
}
