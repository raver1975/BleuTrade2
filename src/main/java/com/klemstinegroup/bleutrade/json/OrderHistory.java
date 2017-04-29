package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class OrderHistory {

@SerializedName("TimeStamp")
@Expose
private String TimeStamp;
@SerializedName("OrderType")
@Expose
private String OrderType;
@SerializedName("Quantity")
@Expose
private Double Quantity;
@SerializedName("Price")
@Expose
private Double Price;

/**
* No args constructor for use in serialization
* 
*/
public OrderHistory() {
}

/**
* 
* @param TimeStamp
* @param Quantity
* @param Price
* @param OrderType
*/
public OrderHistory(String TimeStamp, String OrderType, Double Quantity, Double Price) {
this.TimeStamp = TimeStamp;
this.OrderType = OrderType;
this.Quantity = Quantity;
this.Price = Price;
}

/**
* 
* @return
* The TimeStamp
*/
public String getTimeStamp() {
return TimeStamp;
}

/**
* 
* @param TimeStamp
* The TimeStamp
*/
public void setTimeStamp(String TimeStamp) {
this.TimeStamp = TimeStamp;
}

public OrderHistory withTimeStamp(String TimeStamp) {
this.TimeStamp = TimeStamp;
return this;
}

/**
* 
* @return
* The OrderType
*/
public String getOrderType() {
return OrderType;
}

/**
* 
* @param OrderType
* The OrderType
*/
public void setOrderType(String OrderType) {
this.OrderType = OrderType;
}

public OrderHistory withOrderType(String OrderType) {
this.OrderType = OrderType;
return this;
}

/**
* 
* @return
* The Quantity
*/
public Double getQuantity() {
return Quantity;
}

/**
* 
* @param Quantity
* The Quantity
*/
public void setQuantity(Double Quantity) {
this.Quantity = Quantity;
}

public OrderHistory withQuantity(Double Quantity) {
this.Quantity = Quantity;
return this;
}

/**
* 
* @return
* The Price
*/
public Double getPrice() {
return Price;
}

/**
* 
* @param Price
* The Price
*/
public void setPrice(Double Price) {
this.Price = Price;
}

public OrderHistory withPrice(Double Price) {
this.Price = Price;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(TimeStamp).append(OrderType).append(Quantity).append(Price).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof OrderHistory) == false) {
return false;
}
OrderHistory rhs = ((OrderHistory) other);
return new EqualsBuilder().append(TimeStamp, rhs.TimeStamp).append(OrderType, rhs.OrderType).append(Quantity, rhs.Quantity).append(Price, rhs.Price).isEquals();
}

}