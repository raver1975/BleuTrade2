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
public class MarketSummary {

@SerializedName("MarketName")
@Expose
private String MarketName;
@SerializedName("PrevDay")
@Expose
private Double PrevDay;
@SerializedName("High")
@Expose
private Double High;
@SerializedName("Low")
@Expose
private Double Low;
@SerializedName("Last")
@Expose
private Double Last;
@SerializedName("Average")
@Expose
private Double Average;
@SerializedName("Volume")
@Expose
private Double Volume;
@SerializedName("BaseVolume")
@Expose
private Double BaseVolume;
@SerializedName("TimeStamp")
@Expose
private String TimeStamp;
@SerializedName("Bid")
@Expose
private Double Bid;
@SerializedName("Ask")
@Expose
private Double Ask;
@SerializedName("IsActive")
@Expose
private Boolean IsActive;

/**
* No args constructor for use in serialization
* 
*/
public MarketSummary() {
}

    public static MarketSummary fromJson(JSONObject json){
        return new Gson().fromJson(json.toString(),MarketSummary.class);
    }

/**
* 
* @param TimeStamp
* @param Last
* @param Low
* @param PrevDay
* @param IsActive
* @param Volume
* @param BaseVolume
* @param Bid
* @param Ask
* @param MarketName
* @param Average
* @param High
*/
public MarketSummary(String MarketName, Double PrevDay, Double High, Double Low, Double Last, Double Average, Double Volume, Double BaseVolume, String TimeStamp, Double Bid, Double Ask, Boolean IsActive) {
this.MarketName = MarketName;
this.PrevDay = PrevDay;
this.High = High;
this.Low = Low;
this.Last = Last;
this.Average = Average;
this.Volume = Volume;
this.BaseVolume = BaseVolume;
this.TimeStamp = TimeStamp;
this.Bid = Bid;
this.Ask = Ask;
this.IsActive = IsActive;
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

public MarketSummary withMarketName(String MarketName) {
this.MarketName = MarketName;
return this;
}

/**
* 
* @return
* The PrevDay
*/
public Double getPrevDay() {
return PrevDay;
}

/**
* 
* @param PrevDay
* The PrevDay
*/
public void setPrevDay(Double PrevDay) {
this.PrevDay = PrevDay;
}

public MarketSummary withPrevDay(Double PrevDay) {
this.PrevDay = PrevDay;
return this;
}

/**
* 
* @return
* The High
*/
public Double getHigh() {
return High;
}

/**
* 
* @param High
* The High
*/
public void setHigh(Double High) {
this.High = High;
}

public MarketSummary withHigh(Double High) {
this.High = High;
return this;
}

/**
* 
* @return
* The Low
*/
public Double getLow() {
return Low;
}

/**
* 
* @param Low
* The Low
*/
public void setLow(Double Low) {
this.Low = Low;
}

public MarketSummary withLow(Double Low) {
this.Low = Low;
return this;
}

/**
* 
* @return
* The Last
*/
public Double getLast() {
return Last;
}

/**
* 
* @param Last
* The Last
*/
public void setLast(Double Last) {
this.Last = Last;
}

public MarketSummary withLast(Double Last) {
this.Last = Last;
return this;
}

/**
* 
* @return
* The Average
*/
public Double getAverage() {
return Average;
}

/**
* 
* @param Average
* The Average
*/
public void setAverage(Double Average) {
this.Average = Average;
}

public MarketSummary withAverage(Double Average) {
this.Average = Average;
return this;
}

/**
* 
* @return
* The Volume
*/
public Double getVolume() {
return Volume;
}

/**
* 
* @param Volume
* The Volume
*/
public void setVolume(Double Volume) {
this.Volume = Volume;
}

public MarketSummary withVolume(Double Volume) {
this.Volume = Volume;
return this;
}

/**
* 
* @return
* The BaseVolume
*/
public Double getBaseVolume() {
return BaseVolume;
}

/**
* 
* @param BaseVolume
* The BaseVolume
*/
public void setBaseVolume(Double BaseVolume) {
this.BaseVolume = BaseVolume;
}

public MarketSummary withBaseVolume(Double BaseVolume) {
this.BaseVolume = BaseVolume;
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

public MarketSummary withTimeStamp(String TimeStamp) {
this.TimeStamp = TimeStamp;
return this;
}

/**
* 
* @return
* The Bid
*/
public Double getBid() {
return Bid;
}

/**
* 
* @param Bid
* The Bid
*/
public void setBid(Double Bid) {
this.Bid = Bid;
}

public MarketSummary withBid(Double Bid) {
this.Bid = Bid;
return this;
}

/**
* 
* @return
* The Ask
*/
public Double getAsk() {
return Ask;
}

/**
* 
* @param Ask
* The Ask
*/
public void setAsk(Double Ask) {
this.Ask = Ask;
}

public MarketSummary withAsk(Double Ask) {
this.Ask = Ask;
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

public MarketSummary withIsActive(Boolean IsActive) {
this.IsActive = IsActive;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(MarketName).append(PrevDay).append(High).append(Low).append(Last).append(Average).append(Volume).append(BaseVolume).append(TimeStamp).append(Bid).append(Ask).append(IsActive).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof MarketSummary) == false) {
return false;
}
MarketSummary rhs = ((MarketSummary) other);
return new EqualsBuilder().append(MarketName, rhs.MarketName).append(PrevDay, rhs.PrevDay).append(High, rhs.High).append(Low, rhs.Low).append(Last, rhs.Last).append(Average, rhs.Average).append(Volume, rhs.Volume).append(BaseVolume, rhs.BaseVolume).append(TimeStamp, rhs.TimeStamp).append(Bid, rhs.Bid).append(Ask, rhs.Ask).append(IsActive, rhs.IsActive).isEquals();
}

}