import org.apache.commons.lang3.StringUtils;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-06-27 14:25
 **/

public class Application {
    public static void main(String[]agrs){
        String s="";
        if (StringUtils.isNotEmpty(s) && !"0".equals(s)) {
            s="-".startsWith(s)?s.replace("-",""):"-"+s;
            System.out.println(s);
        }


    }
}
