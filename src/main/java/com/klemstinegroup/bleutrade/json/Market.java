package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;

@Generated("org.jsonschema2pojo")
public class Market {

@SerializedName("MarketCurrency")
@Expose
private String MarketCurrency;
@SerializedName("BaseCurrency")
@Expose
private String BaseCurrency;
@SerializedName("MarketCurrencyLong")
@Expose
private String MarketCurrencyLong;
@SerializedName("BaseCurrencyLong")
@Expose
private String BaseCurrencyLong;
@SerializedName("MinTradeSize")
@Expose
private Double MinTradeSize;
@SerializedName("MarketName")
@Expose
private String MarketName;
@SerializedName("IsActive")
@Expose
private Boolean IsActive;

/**
* No args constructor for use in serialization
* 
*/
public Market() {
}


    public static Market fromJson(JSONObject json){
        return new Gson().fromJson(json.toString(),Market.class);
    }

/**
* 
* @param IsActive
* @param MarketCurrencyLong
* @param BaseCurrencyLong
* @param MarketName
* @param MinTradeSize
* @param BaseCurrency
* @param MarketCurrency
*/
public Market(String MarketCurrency, String BaseCurrency, String MarketCurrencyLong, String BaseCurrencyLong, Double MinTradeSize, String MarketName, Boolean IsActive) {
this.MarketCurrency = MarketCurrency;
this.BaseCurrency = BaseCurrency;
this.MarketCurrencyLong = MarketCurrencyLong;
this.BaseCurrencyLong = BaseCurrencyLong;
this.MinTradeSize = MinTradeSize;
this.MarketName = MarketName;
this.IsActive = IsActive;
}

/**
* 
* @return
* The MarketCurrency
*/
public String getMarketCurrency() {
return MarketCurrency;
}

/**
* 
* @param MarketCurrency
* The MarketCurrency
*/
public void setMarketCurrency(String MarketCurrency) {
this.MarketCurrency = MarketCurrency;
}

public Market withMarketCurrency(String MarketCurrency) {
this.MarketCurrency = MarketCurrency;
return this;
}

/**
* 
* @return
* The BaseCurrency
*/
public String getBaseCurrency() {
return BaseCurrency;
}

/**
* 
* @param BaseCurrency
* The BaseCurrency
*/
public void setBaseCurrency(String BaseCurrency) {
this.BaseCurrency = BaseCurrency;
}

public Market withBaseCurrency(String BaseCurrency) {
this.BaseCurrency = BaseCurrency;
return this;
}

/**
* 
* @return
* The MarketCurrencyLong
*/
public String getMarketCurrencyLong() {
return MarketCurrencyLong;
}

/**
* 
* @param MarketCurrencyLong
* The MarketCurrencyLong
*/
public void setMarketCurrencyLong(String MarketCurrencyLong) {
this.MarketCurrencyLong = MarketCurrencyLong;
}

public Market withMarketCurrencyLong(String MarketCurrencyLong) {
this.MarketCurrencyLong = MarketCurrencyLong;
return this;
}

/**
* 
* @return
* The BaseCurrencyLong
*/
public String getBaseCurrencyLong() {
return BaseCurrencyLong;
}

/**
* 
* @param BaseCurrencyLong
* The BaseCurrencyLong
*/
public void setBaseCurrencyLong(String BaseCurrencyLong) {
this.BaseCurrencyLong = BaseCurrencyLong;
}

public Market withBaseCurrencyLong(String BaseCurrencyLong) {
this.BaseCurrencyLong = BaseCurrencyLong;
return this;
}

/**
* 
* @return
* The MinTradeSize
*/
public Double getMinTradeSize() {
return MinTradeSize;
}

/**
* 
* @param MinTradeSize
* The MinTradeSize
*/
public void setMinTradeSize(Double MinTradeSize) {
this.MinTradeSize = MinTradeSize;
}

public Market withMinTradeSize(Double MinTradeSize) {
this.MinTradeSize = MinTradeSize;
return this;
}

/**
* 
* @return
* The MarketName
*/
public String getMarketName() {
return MarketName;
}

/**
* 
* @param MarketName
* The MarketName
*/
public void setMarketName(String MarketName) {
this.MarketName = MarketName;
}

public Market withMarketName(String MarketName) {
this.MarketName = MarketName;
return this;
}

/**
* 
* @return
* The IsActive
*/
public Boolean getIsActive() {
return IsActive;
}

/**
* 
* @param IsActive
* The IsActive
*/
public void setIsActive(Boolean IsActive) {
this.IsActive = IsActive;
}

public Market withIsActive(Boolean IsActive) {
this.IsActive = IsActive;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(MarketCurrency).append(BaseCurrency).append(MarketCurrencyLong).append(BaseCurrencyLong).append(MinTradeSize).append(MarketName).append(IsActive).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof Market) == false) {
return false;
}
Market rhs = ((Market) other);
return new EqualsBuilder().append(MarketCurrency, rhs.MarketCurrency).append(BaseCurrency, rhs.BaseCurrency).append(MarketCurrencyLong, rhs.MarketCurrencyLong).append(BaseCurrencyLong, rhs.BaseCurrencyLong).append(MinTradeSize, rhs.MinTradeSize).append(MarketName, rhs.MarketName).append(IsActive, rhs.IsActive).isEquals();
}

}