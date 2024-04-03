package demo;

import lombok.extern.slf4j.Slf4j;

import java.net.*;
import java.util.Enumeration;

/**
 * @author shenlx
 * @description
 * @date 2024/1/12 9:51
 */
public class InetAddressExample {

    public static void main(String[]args){
        //get the network interfaces and associated addresses for this host
        try{
            /**NetworkInterface的静态方法getNetworkInterfaces()返回一个列表，其中包含了该主机每一个接口所有对应的NetworkInterface类实例*/
            Enumeration<NetworkInterface> interfacelist=NetworkInterface.getNetworkInterfaces();
            /**通常情况下，即使主机没有任何其他网络连接，回环接口（loopback）也总是存在的
             * ，因此，只有当一个主机根本没有网络子系统时，列表监测为空*/
            if(interfacelist==null){
                System.out.println("--NO interfaces found--");
            }else {
                /**获取并打印出列表中每个接口的地址*/
                while (interfacelist.hasMoreElements()){
                    NetworkInterface networkInterface = interfacelist.nextElement();
                    /**打印接口名称
                     * getName()方法为接口返回一个本地名称，接口的本地名称通常由字母与数字的联合组成，代表了接口的类型和具体实例，如“lo0”或“eth0”
                     * */
                    System.out.println("Interfaces "+networkInterface.getName()+";");
                    /**获取与接口相关的地址
                     * InetAddress类的实例，即该接口所有关联的每一个地址，根据主机的不同配置
                     * ，这个地址列表可能包含IPV4或IPV6地址，或者包含了两种类型地址的混合列表
                     * */
                    Enumeration<InetAddress> addrList=networkInterface.getInetAddresses();
                    /**空列表监测*/
                    if(!addrList.hasMoreElements()){
                        System.out.println("\t(NO ADDRESSES FOR THIS INTERFACE)");
                    }
                    while(addrList.hasMoreElements()){
                        /**列表的迭代，打印出每个地址*/
                        InetAddress inetAddress = addrList.nextElement();
                        /**
                         * 对每个地址实例进行检测以判断其属于哪个IP地址子类（目前InetAddress的子类只有上面列出的那些，但可以想象到，将会也许还会有其他的子类）
                         * InetAddress类的getHostAddress()方法返回一个字符串来代表主机的数字类型地址，不同类型的地址对应不同的格式，IPV4是点分形式，IPV6是
                         * 冒号分隔的16进制形式
                         *
                         * */
                        System.out.println("\tAddress" +
                                ((inetAddress instanceof Inet4Address ?"(v4)":(inetAddress instanceof Inet6Address?"(V6)":"(?)"))));
                        System.out.println(":"+inetAddress.getHostAddress());
                    }
                }
            }

        }catch (SocketException e){
            /**捕获异常
             * 对 getNetworkInterfaces()方法的调用将会抛出SocketException异常
             */
            System.out.println("Error getting network interfaces:"+e.getMessage());
        }
        //GET name(s)/address(es) of hosts given on command line
        /**获取从命令行输入的每个参数所对应的主机名称和地址
         * 迭代列表打印出列表的每一项
         * 对于列表的每个主机，我们通过调用getHostName()方法来打印主机名，并把调用getHostAddress()方法所获得数字类型地址打印在主机名后面
         * */
        for (String host:args){
            try{
                System.out.println(host+":");
                InetAddress[] allByName = InetAddress.getAllByName(host);
                for(InetAddress address:allByName){
                    System.out.println("\t"+address.getHostName()+"/"+address.getHostAddress());
                }
            } catch (UnknownHostException e) {
                System.out.println("\tUnable to find address for "+host);
                e.printStackTrace();
            }
        }
    }
}
