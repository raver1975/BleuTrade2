package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class MarketHistory {

@SerializedName("TimeStamp")
@Expose
private String TimeStamp;
@SerializedName("Quantity")
@Expose
private Double Quantity;
@SerializedName("Price")
@Expose
private Double Price;
@SerializedName("Total")
@Expose
private Double Total;
@SerializedName("OrderType")
@Expose
private String OrderType;

/**
* No args constructor for use in serialization
* 
*/
public MarketHistory() {
}

/**
* 
* @param TimeStamp
* @param Quantity
* @param OrderType
* @param Price
* @param Total
*/
public MarketHistory(String TimeStamp, Double Quantity, Double Price, Double Total, String OrderType) {
this.TimeStamp = TimeStamp;
this.Quantity = Quantity;
this.Price = Price;
this.Total = Total;
this.OrderType = OrderType;
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

public MarketHistory withTimeStamp(String TimeStamp) {
this.TimeStamp = TimeStamp;
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

public MarketHistory withQuantity(Double Quantity) {
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

public MarketHistory withPrice(Double Price) {
this.Price = Price;
return this;
}

/**
* 
* @return
* The Total
*/
public Double getTotal() {
return Total;
}

/**
* 
* @param Total
* The Total
*/
public void setTotal(Double Total) {
this.Total = Total;
}

public MarketHistory withTotal(Double Total) {
this.Total = Total;
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

public MarketHistory withOrderType(String OrderType) {
this.OrderType = OrderType;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(TimeStamp).append(Quantity).append(Price).append(Total).append(OrderType).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof MarketHistory) == false) {
return false;
}
MarketHistory rhs = ((MarketHistory) other);
return new EqualsBuilder().append(TimeStamp, rhs.TimeStamp).append(Quantity, rhs.Quantity).append(Price, rhs.Price).append(Total, rhs.Total).append(OrderType, rhs.OrderType).isEquals();
}

}