package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Sell {

@SerializedName("Quantity")
@Expose
private Double Quantity;
@SerializedName("Rate")
@Expose
private Double Rate;

/**
* No args constructor for use in serialization
* 
*/
public Sell() {
}

/**
* 
* @param Quantity
* @param Rate
*/
public Sell(Double Quantity, Double Rate) {
this.Quantity = Quantity;
this.Rate = Rate;
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

public Sell withQuantity(Double Quantity) {
this.Quantity = Quantity;
return this;
}

/**
* 
* @return
* The Rate
*/
public Double getRate() {
return Rate;
}

/**
* 
* @param Rate
* The Rate
*/
public void setRate(Double Rate) {
this.Rate = Rate;
}

public Sell withRate(Double Rate) {
this.Rate = Rate;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(Quantity).append(Rate).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof Sell) == false) {
return false;
}
Sell rhs = ((Sell) other);
return new EqualsBuilder().append(Quantity, rhs.Quantity).append(Rate, rhs.Rate).isEquals();
}

}