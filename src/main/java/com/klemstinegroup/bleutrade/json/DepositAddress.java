package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class DepositAddress {

@SerializedName("Currency")
@Expose
private String Currency;
@SerializedName("Address")
@Expose
private String Address;

/**
* No args constructor for use in serialization
* 
*/
public DepositAddress() {
}

/**
* 
* @param Address
* @param Currency
*/
public DepositAddress(String Currency, String Address) {
this.Currency = Currency;
this.Address = Address;
}

/**
* 
* @return
* The Currency
*/
public String getCurrency() {
return Currency;
}

/**
* 
* @param Currency
* The Currency
*/
public void setCurrency(String Currency) {
this.Currency = Currency;
}

public DepositAddress withCurrency(String Currency) {
this.Currency = Currency;
return this;
}

/**
* 
* @return
* The Address
*/
public String getAddress() {
return Address;
}

/**
* 
* @param Address
* The Address
*/
public void setAddress(String Address) {
this.Address = Address;
}

public DepositAddress withAddress(String Address) {
this.Address = Address;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(Currency).append(Address).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof DepositAddress) == false) {
return false;
}
DepositAddress rhs = ((DepositAddress) other);
return new EqualsBuilder().append(Currency, rhs.Currency).append(Address, rhs.Address).isEquals();
}

}