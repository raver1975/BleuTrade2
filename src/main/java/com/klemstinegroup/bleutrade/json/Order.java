package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;

@Generated("org.jsonschema2pojo")
public class Order implements Serializable, Comparable<Order> {
    transient static DecimalFormat dfcoins = new DecimalFormat("+0000.00000000;-0000.00000000");

    @SerializedName("OrderId")
    @Expose
    private String OrderId;
    @SerializedName("Exchange")
    @Expose
    private String Exchange;
    @SerializedName("Type")
    @Expose
    private String Type;
    @SerializedName("Quantity")
    @Expose
    private Double Quantity;
    @SerializedName("QuantityRemaining")
    @Expose
    private Double QuantityRemaining;
    @SerializedName("QuantityBaseTraded")
    @Expose
    private String QuantityBaseTraded;
    @SerializedName("Price")
    @Expose
    private Double Price;
    @SerializedName("Status")
    @Expose
    private String Status;
    @SerializedName("Created")
    @Expose
    private String Created;
    @SerializedName("Comments")
    @Expose
    private String Comments;

    /**
     * No args constructor for use in serialization
     */
    public Order() {
    }

    /**
     * @param Status
     * @param QuantityRemaining
     * @param Comments
     * @param Created
     * @param Quantity
     * @param Exchange
     * @param Type
     * @param Price
     * @param OrderId
     * @param QuantityBaseTraded
     */
    public Order(String OrderId, String Exchange, String Type, Double Quantity, Double QuantityRemaining, String QuantityBaseTraded, Double Price, String Status, String Created, String Comments) {
        this.OrderId = OrderId;
        this.Exchange = Exchange;
        this.Type = Type;
        this.Quantity = Quantity;
        this.QuantityRemaining = QuantityRemaining;
        this.QuantityBaseTraded = QuantityBaseTraded;
        this.Price = Price;
        this.Status = Status;
        this.Created = Created;
        this.Comments = Comments;
    }

    public static Order fromJson(JSONObject json) {
        return new Gson().fromJson(json.toString(), Order.class);
    }


    /**
     * @return The OrderId
     */
    public String getOrderId() {
        return OrderId;
    }

    /**
     * @param OrderId The OrderId
     */
    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public Order withOrderId(String OrderId) {
        this.OrderId = OrderId;
        return this;
    }

    /**
     * @return The Exchange
     */
    public String getExchange() {
        return Exchange;
    }

    /**
     * @param Exchange The Exchange
     */
    public void setExchange(String Exchange) {
        this.Exchange = Exchange;
    }

    public Order withExchange(String Exchange) {
        this.Exchange = Exchange;
        return this;
    }

    /**
     * @return The Type
     */
    public String getType() {
        return Type;
    }

    /**
     * @param Type The Type
     */
    public void setType(String Type) {
        this.Type = Type;
    }

    public Order withType(String Type) {
        this.Type = Type;
        return this;
    }

    /**
     * @return The Quantity
     */
    public Double getQuantity() {
        return Quantity;
    }

    /**
     * @param Quantity The Quantity
     */
    public void setQuantity(Double Quantity) {
        this.Quantity = Quantity;
    }

    public Order withQuantity(Double Quantity) {
        this.Quantity = Quantity;
        return this;
    }

    /**
     * @return The QuantityRemaining
     */
    public Double getQuantityRemaining() {
        return QuantityRemaining;
    }

    /**
     * @param QuantityRemaining The QuantityRemaining
     */
    public void setQuantityRemaining(Double QuantityRemaining) {
        this.QuantityRemaining = QuantityRemaining;
    }

    public Order withQuantityRemaining(Double QuantityRemaining) {
        this.QuantityRemaining = QuantityRemaining;
        return this;
    }

    /**
     * @return The QuantityBaseTraded
     */
    public String getQuantityBaseTraded() {
        return QuantityBaseTraded;
    }

    /**
     * @param QuantityBaseTraded The QuantityBaseTraded
     */
    public void setQuantityBaseTraded(String QuantityBaseTraded) {
        this.QuantityBaseTraded = QuantityBaseTraded;
    }

    public Order withQuantityBaseTraded(String QuantityBaseTraded) {
        this.QuantityBaseTraded = QuantityBaseTraded;
        return this;
    }

    /**
     * @return The Price
     */
    public Double getPrice() {
        return Price;
    }

    /**
     * @param Price The Price
     */
    public void setPrice(Double Price) {
        this.Price = Price;
    }

    public Order withPrice(Double Price) {
        this.Price = Price;
        return this;
    }

    /**
     * @return The Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status The Status
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    public Order withStatus(String Status) {
        this.Status = Status;
        return this;
    }

    /**
     * @return The Created
     */
    public String getCreated() {
        return Created;
    }

    /**
     * @param Created The Created
     */
    public void setCreated(String Created) {
        this.Created = Created;
    }

    public Order withCreated(String Created) {
        this.Created = Created;
        return this;
    }

    /**
     * @return The Comments
     */
    public String getComments() {
        return Comments;
    }

    /**
     * @param Comments The Comments
     */
    public void setComments(String Comments) {
        this.Comments = Comments;
    }

    public Order withComments(String Comments) {
        this.Comments = Comments;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(OrderId).append(Exchange).append(Type).append(Quantity).append(QuantityRemaining).append(QuantityBaseTraded).append(Price).append(Status).append(Created).append(Comments).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Order) == false) {
            return false;
        }
        Order rhs = ((Order) other);
        return new EqualsBuilder().append(OrderId, rhs.OrderId).append(Exchange, rhs.Exchange).append(Type, rhs.Type).append(Quantity, rhs.Quantity).append(QuantityRemaining, rhs.QuantityRemaining).append(QuantityBaseTraded, rhs.QuantityBaseTraded).append(Price, rhs.Price).append(Status, rhs.Status).append(Created, rhs.Created).append(Comments, rhs.Comments).isEquals();
    }

    @Override
    public int compareTo(Order o) {
        return (getExchange() + dfcoins.format(getQuantity())).compareTo(o.getExchange() + dfcoins.format(o.getQuantity()));
    }
}