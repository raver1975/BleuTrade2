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
public class Balance  implements Comparable<Balance> {

    @SerializedName("Currency")
    @Expose
    private String Currency;
    @SerializedName("Balance")
    @Expose
    private Double Balance;
    @SerializedName("Available")
    @Expose
    private Double Available;
    @SerializedName("Pending")
    @Expose
    private Double Pending;
    @SerializedName("CryptoAddress")
    @Expose
    private String CryptoAddress;
    @SerializedName("IsActive")
    @Expose
    private Boolean IsActive;

    /**
     * No args constructor for use in serialization
     */
    public Balance(){
    }
    public static Balance fromJson(JSONObject json) {
        return new Gson().fromJson(json.toString(), Balance.class);
    }


    /**
     * @param Available
     * @param IsActive
     * @param Pending
     * @param Balance
     * @param Currency
     * @param CryptoAddress
     */
    public Balance(String Currency, Double Balance, Double Available, Double Pending, String CryptoAddress, Boolean IsActive) {
        this.Currency = Currency;
        this.Balance = Balance;
        this.Available = Available;
        this.Pending = Pending;
        this.CryptoAddress = CryptoAddress;
        this.IsActive = IsActive;
    }

    /**
     * @return The Currency
     */
    public String getCurrency() {
        return Currency;
    }

    /**
     * @param Currency The Currency
     */
    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }

    public Balance withCurrency(String Currency) {
        this.Currency = Currency;
        return this;
    }

    /**
     * @return The Balance
     */
    public Double getBalance() {
        return Balance;
    }

    /**
     * @param Balance The Balance
     */
    public void setBalance(Double Balance) {
        this.Balance = Balance;
    }

    public Balance withBalance(Double Balance) {
        this.Balance = Balance;
        return this;
    }

    /**
     * @return The Available
     */
    public Double getAvailable() {
        return Available;
    }

    /**
     * @param Available The Available
     */
    public void setAvailable(Double Available) {
        this.Available = Available;
    }

    public Balance withAvailable(Double Available) {
        this.Available = Available;
        return this;
    }

    /**
     * @return The Pending
     */
    public Double getPending() {
        return Pending;
    }

    /**
     * @param Pending The Pending
     */
    public void setPending(Double Pending) {
        this.Pending = Pending;
    }

    public Balance withPending(Double Pending) {
        this.Pending = Pending;
        return this;
    }

    /**
     * @return The CryptoAddress
     */
    public String getCryptoAddress() {
        return CryptoAddress;
    }

    /**
     * @param CryptoAddress The CryptoAddress
     */
    public void setCryptoAddress(String CryptoAddress) {
        this.CryptoAddress = CryptoAddress;
    }

    public Balance withCryptoAddress(String CryptoAddress) {
        this.CryptoAddress = CryptoAddress;
        return this;
    }

    /**
     * @return The IsActive
     */
    public Boolean getIsActive() {
        return IsActive;
    }

    /**
     * @param IsActive The IsActive
     */
    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }

    public Balance withIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(Currency).append(Balance).append(Available).append(Pending).append(CryptoAddress).append(IsActive).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Balance) == false) {
            return false;
        }
        Balance rhs = ((Balance) other);
        return new EqualsBuilder().append(Currency, rhs.Currency).append(Balance, rhs.Balance).append(Available, rhs.Available).append(Pending, rhs.Pending).append(CryptoAddress, rhs.CryptoAddress).append(IsActive, rhs.IsActive).isEquals();
    }

    @Override
    public int compareTo(Balance o) {
        return 0;
    }
}