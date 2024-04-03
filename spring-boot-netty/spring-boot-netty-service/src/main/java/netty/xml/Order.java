package netty.xml;

/**
 * @author shenlx
 * @description
 * @date 2024/2/28 10:38
 */
public class Order {
    private long orderNumber;
    private Customer customer;

    /**
     * Billing address information
     */
    private Address bilTo;

    private String shipping;

    /**
     * Shipping address information. If missing ,the billing address is also
     * used as the shipping address.
     */
    private Address shipTo;

    private Float total;

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getBilTo() {
        return bilTo;
    }

    public void setBilTo(Address bilTo) {
        this.bilTo = bilTo;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public Address getShipTo() {
        return shipTo;
    }

    public void setShipTo(Address shipTo) {
        this.shipTo = shipTo;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber=" + orderNumber +
                ", customer=" + customer +
                ", bilTo=" + bilTo +
                ", shipping=" + shipping +
                ", shipTo=" + shipTo +
                ", total=" + total +
                '}';
    }
}
