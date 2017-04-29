package com.klemstinegroup.bleutrade.json;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class OrderBook {

@SerializedName("buy")
@Expose
private List<Buy> buy = new ArrayList<Buy>();
@SerializedName("sell")
@Expose
private List<Sell> sell = new ArrayList<Sell>();

/**
* No args constructor for use in serialization
* 
*/
public OrderBook() {
}

/**
* 
* @param sell
* @param buy
*/
public OrderBook(List<Buy> buy, List<Sell> sell) {
this.buy = buy;
this.sell = sell;
}

/**
* 
* @return
* The buy
*/
public List<Buy> getBuy() {
return buy;
}

/**
* 
* @param buy
* The buy
*/
public void setBuy(List<Buy> buy) {
this.buy = buy;
}

public OrderBook withBuy(List<Buy> buy) {
this.buy = buy;
return this;
}

/**
* 
* @return
* The sell
*/
public List<Sell> getSell() {
return sell;
}

/**
* 
* @param sell
* The sell
*/
public void setSell(List<Sell> sell) {
this.sell = sell;
}

public OrderBook withSell(List<Sell> sell) {
this.sell = sell;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(buy).append(sell).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof OrderBook) == false) {
return false;
}
OrderBook rhs = ((OrderBook) other);
return new EqualsBuilder().append(buy, rhs.buy).append(sell, rhs.sell).isEquals();
}

}