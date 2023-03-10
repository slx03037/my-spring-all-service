package com.shenlx.xinwen.springbootjdbctemplatemultidatasource.service;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<Map<String, Object>> getUsersMysql01();
    List<Map<String, Object>> getUsersMysql02();
}
