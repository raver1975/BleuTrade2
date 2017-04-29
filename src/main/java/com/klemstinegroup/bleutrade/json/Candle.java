package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Generated("org.jsonschema2pojo")
public class Candle {

@SerializedName("TimeStamp")
@Expose
private String TimeStamp;
@SerializedName("Open")
@Expose
private String Open;
@SerializedName("High")
@Expose
private String High;
@SerializedName("Low")
@Expose
private String Low;
@SerializedName("Close")
@Expose
private String Close;
@SerializedName("Volume")
@Expose
private String Volume;
@SerializedName("BaseVolume")
@Expose
private String BaseVolume;

/**
* No args constructor for use in serialization
* 
*/
public Candle() {
}

/**
* 
* @param TimeStamp
* @param Low
* @param Open
* @param Close
* @param Volume
* @param BaseVolume
* @param High
*/
public Candle(String TimeStamp, String Open, String High, String Low, String Close, String Volume, String BaseVolume) {
this.TimeStamp = TimeStamp;
this.Open = Open;
this.High = High;
this.Low = Low;
this.Close = Close;
this.Volume = Volume;
this.BaseVolume = BaseVolume;
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

public Candle withTimeStamp(String TimeStamp) {
this.TimeStamp = TimeStamp;
return this;
}

/**
* 
* @return
* The Open
*/
public String getOpen() {
return Open;
}

/**
* 
* @param Open
* The Open
*/
public void setOpen(String Open) {
this.Open = Open;
}

public Candle withOpen(String Open) {
this.Open = Open;
return this;
}

/**
* 
* @return
* The High
*/
public String getHigh() {
return High;
}

/**
* 
* @param High
* The High
*/
public void setHigh(String High) {
this.High = High;
}

public Candle withHigh(String High) {
this.High = High;
return this;
}

/**
* 
* @return
* The Low
*/
public String getLow() {
return Low;
}

/**
* 
* @param Low
* The Low
*/
public void setLow(String Low) {
this.Low = Low;
}

public Candle withLow(String Low) {
this.Low = Low;
return this;
}

/**
* 
* @return
* The Close
*/
public String getClose() {
return Close;
}

/**
* 
* @param Close
* The Close
*/
public void setClose(String Close) {
this.Close = Close;
}

public Candle withClose(String Close) {
this.Close = Close;
return this;
}

/**
* 
* @return
* The Volume
*/
public String getVolume() {
return Volume;
}

/**
* 
* @param Volume
* The Volume
*/
public void setVolume(String Volume) {
this.Volume = Volume;
}

public Candle withVolume(String Volume) {
this.Volume = Volume;
return this;
}

/**
* 
* @return
* The BaseVolume
*/
public String getBaseVolume() {
return BaseVolume;
}

/**
* 
* @param BaseVolume
* The BaseVolume
*/
public void setBaseVolume(String BaseVolume) {
this.BaseVolume = BaseVolume;
}

public Candle withBaseVolume(String BaseVolume) {
this.BaseVolume = BaseVolume;
return this;
}

@Override
public String toString() {
return ToStringBuilder.reflectionToString(this);
}

@Override
public int hashCode() {
return new HashCodeBuilder().append(TimeStamp).append(Open).append(High).append(Low).append(Close).append(Volume).append(BaseVolume).toHashCode();
}

@Override
public boolean equals(Object other) {
if (other == this) {
return true;
}
if ((other instanceof Candle) == false) {
return false;
}
Candle rhs = ((Candle) other);
return new EqualsBuilder().append(TimeStamp, rhs.TimeStamp).append(Open, rhs.Open).append(High, rhs.High).append(Low, rhs.Low).append(Close, rhs.Close).append(Volume, rhs.Volume).append(BaseVolume, rhs.BaseVolume).isEquals();
}

}