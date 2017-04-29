package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class WithdrawHistory {

@SerializedName("Id")
@Expose
private String Id;
@SerializedName("TimeStamp")
@Expose
private String TimeStamp;
@SerializedName("Coin")
@Expose
private String Coin;
@SerializedName("Amount")
@Expose
private Double Amount;
@SerializedName("Label")
@Expose
private String Label;
@SerializedName("TransactionId")
@Expose
private String TransactionId;

/**
* No args constructor for use in serialization
* 
*/
public WithdrawHistory() {
}

/**
* 
* @param TransactionId
* @param TimeStamp
* @param Amount
* @param Label
* @param Id
* @param Coin
*/
public WithdrawHistory(String Id, String TimeStamp, String Coin, Double Amount, String Label, String TransactionId) {
this.Id = Id;
this.TimeStamp = TimeStamp;
this.Coin = Coin;
this.Amount = Amount;
this.Label = Label;
this.TransactionId = TransactionId;
}

/**
* 
* @return
* The Id
*/
public String getId() {
return Id;
}

/**
* 
* @param Id
* The Id
*/
public void setId(String Id) {
this.Id = Id;
}

public WithdrawHistory withId(String Id) {
this.Id = Id;
return this;
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

public WithdrawHistory withTimeStamp(String TimeStamp) {
this.TimeStamp = TimeStamp;
return this;
}

/**
* 
* @return
* The Coin
*/
public String getCoin() {
return Coin;
}

/**
* 
* @param Coin
* The Coin
*/
public void setCoin(String Coin) {
this.Coin = Coin;
}

public WithdrawHistory withCoin(String Coin) {
this.Coin = Coin;
return this;
}

/**
* 
* @return
* The Amount
*/
public Double getAmount() {
return Amount;
}

/**
* 
* @param Amount
* The Amount
*/
public void setAmount(Double Amount) {
this.Amount = Amount;
}

public WithdrawHistory withAmount(Double Amount) {
this.Amount = Amount;
return this;
}

/**
* 
* @return
* The Label
*/
public String getLabel() {
return Label;
}

/**
* 
* @param Label
* The Label
*/
public void setLabel(String Label) {
this.Label = Label;
}

public WithdrawHistory withLabel(String Label) {
this.Label = Label;
return this;
}

/**
* 
* @return
* The TransactionId
*/
public String getTransactionId() {
return TransactionId;
}

/**
* 
* @param TransactionId
* The TransactionId
*/
public void setTransactionId(String TransactionId) {
this.TransactionId = TransactionId;
}

public WithdrawHistory withTransactionId(String TransactionId) {
this.TransactionId = TransactionId;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(Id).append(TimeStamp).append(Coin).append(Amount).append(Label).append(TransactionId).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof WithdrawHistory) == false) {
return false;
}
WithdrawHistory rhs = ((WithdrawHistory) other);
return new EqualsBuilder().append(Id, rhs.Id).append(TimeStamp, rhs.TimeStamp).append(Coin, rhs.Coin).append(Amount, rhs.Amount).append(Label, rhs.Label).append(TransactionId, rhs.TransactionId).isEquals();
}

}