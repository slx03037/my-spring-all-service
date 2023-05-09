package spring.boot.shiro.validate.service;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-05-08 15:55
 **/

import com.shenlx.xinwen.model.enetity.ManagerInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.stereotype.Service;
import spring.boot.shiro.validate.exception.ForbiddenUserException;
import spring.boot.shiro.validate.mapper.ManagerInfoDao;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * 后台用户管理
 */

@Service
public class ManagerInfoService {

    @Resource
    private ManagerInfoDao managerInfoDao;

    /**
     * 通过名称查找用户
     * @param username
     * @return
     */
    public ManagerInfo findByUsername(String username) {
        ManagerInfo managerInfo =  managerInfoDao.findByUsername(username);
        if (managerInfo == null) {
            throw new UnknownAccountException();
        }
        if (managerInfo.getState() == 2) {
            throw new ForbiddenUserException();
        }
        if (managerInfo.getPidsList() == null) {
            managerInfo.setPidsList(Collections.singletonList(0));
        } else if (managerInfo.getPidsList().size() == 0) {
            managerInfo.getPidsList().add(0);
        }
        return managerInfo;
    }
}

