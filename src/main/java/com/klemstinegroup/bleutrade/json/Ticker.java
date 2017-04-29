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
public class Ticker {

    @SerializedName("Bid")
    @Expose
    private Double Bid;
    @SerializedName("Ask")
    @Expose
    private Double Ask;
    @SerializedName("Last")
    @Expose
    private Double Last;

    /**
     * No args constructor for use in serialization
     */
    public Ticker() {
    }

    public static Ticker fromJson(JSONObject json) {
        return new Gson().fromJson(json.toString(), Ticker.class);
    }

    /**
     * @param Last
     * @param Bid
     * @param Ask
     */
    public Ticker(Double Bid, Double Ask, Double Last) {
        this.Bid = Bid;
        this.Ask = Ask;
        this.Last = Last;
    }

    /**
     * @return The Bid
     */
    public Double getBid() {
        return Bid;
    }

    /**
     * @param Bid The Bid
     */
    public void setBid(Double Bid) {
        this.Bid = Bid;
    }

    public Ticker withBid(Double Bid) {
        this.Bid = Bid;
        return this;
    }

    /**
     * @return The Ask
     */
    public Double getAsk() {
        return Ask;
    }

    /**
     * @param Ask The Ask
     */
    public void setAsk(Double Ask) {
        this.Ask = Ask;
    }

    public Ticker withAsk(Double Ask) {
        this.Ask = Ask;
        return this;
    }

    /**
     * @return The Last
     */
    public Double getLast() {
        return Last;
    }

    /**
     * @param Last The Last
     */
    public void setLast(Double Last) {
        this.Last = Last;
    }

    public Ticker withLast(Double Last) {
        this.Last = Last;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(Bid).append(Ask).append(Last).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Ticker) == false) {
            return false;
        }
        Ticker rhs = ((Ticker) other);
        return new EqualsBuilder().append(Bid, rhs.Bid).append(Ask, rhs.Ask).append(Last, rhs.Last).isEquals();
    }

}