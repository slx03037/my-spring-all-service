package com.shenlx.xinwen.xml;

import org.jibx.runtime.*;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * @author shenlx
 * @description
 * @date 2024/2/28 11:59
 */
public class TestOrder {
    private IBindingFactory factory=null;

    private StringWriter writer=null;

    private StringReader reader=null;

    private final static String CHARSET_NAME="UTF-8";

    private String encodeXml(Order order) throws JiBXException, IOException {
        factory= BindingDirectory.getFactory(Order.class);
        writer =new StringWriter();
        IMarshallingContext mctx = factory.createMarshallingContext();
        mctx.setIndent(2);
        mctx.marshalDocument(order,CHARSET_NAME,null,writer);
        String xmlStr = writer.toString();
        writer.close();
        System.out.println(xmlStr);
        return xmlStr;
    }

    private Order decode2Order(String xmlBody) throws JiBXException {
         reader = new StringReader(xmlBody);
        IUnmarshallingContext uctx = factory.createUnmarshallingContext();
        return (Order) uctx.unmarshalDocument(reader);
    }

    public static void main(String[]args) throws JiBXException, IOException {
        TestOrder testOrder=new TestOrder();
        Order order = OrderFactory.create(123);
        String body = testOrder.encodeXml(order);
        Order order1 = testOrder.decode2Order(body);
        System.out.println(order1);
    }

    //at org.jibx.binding.def.StringConversion.<init>(StringConversion.java:163)
    //StringConversion
   // at org.jibx.binding.classes.ClassCache.getClassFileImpl(ClassCache.java:155)
   // ClassCache.ClassCacheLocator;

   // Caused by: java.lang.NoClassDefFoundError: org/apache/commons/lang3/ArrayUtils
    //ArrayUtils
}
