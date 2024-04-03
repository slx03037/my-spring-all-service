package netty.xml;


import java.util.ArrayList;
import java.util.List;

/**
 * @author shenlx
 * @description
 * @date 2024/2/28 16:56
 */
public class OrderFactory {
    public static Order create(int number){
        Order order=new Order();
        //orderNumber
        order.setOrderNumber(number);
        //total
        order.setTotal(9999.999f);
        //customer
        Customer customer=new Customer();
        customer.setFirstName("echo");
        customer.setLastName("tong");
        List<String>list=new ArrayList<>();
        list.add("666");
        // list.add("555");
        customer.setCustomerNumber(123);
        //customer.setMiddleNames(list);
        order.setCustomer(customer);

        //billTo
        Address billTo=new Address();
        billTo.setStreet1("西乡大道");
        billTo.setCity("深圳市");
        billTo.setState("广东省");
        billTo.setPostCode("123321");
        billTo.setCountry("中国");
        order.setBilTo(billTo);
        //shipping
        order.setShipping(Shipping.INTERNATIONAL_MAIL);
        //shipTo
        Address shipTo=new Address();
        shipTo.setStreet1("西乡大道");
        shipTo.setCity("深圳市");
        shipTo.setState("广东省");
        shipTo.setPostCode("123321");
        shipTo.setCountry("中国");
        order.setShipTo(shipTo);
        return order;
    }
}
