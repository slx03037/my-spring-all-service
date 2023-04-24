package com.shenlx.xinwen.springbootcondition;

import com.shenlx.xinwen.springbootcondition.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootConditionApplicationTests {

    @Autowired(required = false)
    @Qualifier(value = "winP")
    private Person winP;

    @Autowired(required = false)
    @Qualifier(value = "LinuxP")
    private Person linP;

    @Test
    void contextLoads() {
        System.out.println(winP);
        System.out.println(linP);
    }


}
