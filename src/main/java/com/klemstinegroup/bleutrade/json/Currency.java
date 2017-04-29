package com.klemstinegroup.bleutrade.json;

import javax.annotation.Generated;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.json.JSONObject;

import static org.apache.commons.lang3.builder.ToStringBuilder.*;

@Generated("org.jsonschema2pojo")
public class Currency {

    @SerializedName("Currency")
    @Expose
    private String Currency;
    @SerializedName("CurrencyLong")
    @Expose
    private String CurrencyLong;
    @SerializedName("MinConfirmation")
    @Expose
    private Long MinConfirmation;
    @SerializedName("TxFee")
    @Expose
    private Double TxFee;
    @SerializedName("IsActive")
    @Expose
    private Boolean IsActive;
    @SerializedName("CoinType")
    @Expose
    private String CoinType;
    @SerializedName("MaintenanceMode")
    @Expose
    private Boolean MaintenanceMode;

    /**
     * No args constructor for use in serialization
     *
     */
    public Currency() {
    }


    public static Currency fromJson(JSONObject json){
        return new Gson().fromJson(json.toString(),Currency.class);
    }
    /**
     *
     * @param MaintenanceMode
     * @param IsActive
     * @param CurrencyLong
     * @param TxFee
     * @param MinConfirmation
     * @param Currency
     * @param CoinType
     */
    public Currency(String Currency, String CurrencyLong, Long MinConfirmation, Double TxFee, Boolean IsActive, String CoinType, Boolean MaintenanceMode) {
        this.Currency = Currency;
        this.CurrencyLong = CurrencyLong;
        this.MinConfirmation = MinConfirmation;
        this.TxFee = TxFee;
        this.IsActive = IsActive;
        this.CoinType = CoinType;
        this.MaintenanceMode = MaintenanceMode;
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

    public Currency withCurrency(String Currency) {
        this.Currency = Currency;
        return this;
    }

    /**
     *
     * @return
     * The CurrencyLong
     */
    public String getCurrencyLong() {
        return CurrencyLong;
    }

    /**
     *
     * @param CurrencyLong
     * The CurrencyLong
     */
    public void setCurrencyLong(String CurrencyLong) {
        this.CurrencyLong = CurrencyLong;
    }

    public Currency withCurrencyLong(String CurrencyLong) {
        this.CurrencyLong = CurrencyLong;
        return this;
    }

    /**
     *
     * @return
     * The MinConfirmation
     */
    public Long getMinConfirmation() {
        return MinConfirmation;
    }

    /**
     *
     * @param MinConfirmation
     * The MinConfirmation
     */
    public void setMinConfirmation(Long MinConfirmation) {
        this.MinConfirmation = MinConfirmation;
    }

    public Currency withMinConfirmation(Long MinConfirmation) {
        this.MinConfirmation = MinConfirmation;
        return this;
    }

    /**
     *
     * @return
     * The TxFee
     */
    public Double getTxFee() {
        return TxFee;
    }

    /**
     *
     * @param TxFee
     * The TxFee
     */
    public void setTxFee(Double TxFee) {
        this.TxFee = TxFee;
    }

    public Currency withTxFee(Double TxFee) {
        this.TxFee = TxFee;
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

    public Currency withIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
        return this;
    }

    /**
     *
     * @return
     * The CoinType
     */
    public String getCoinType() {
        return CoinType;
    }

    /**
     *
     * @param CoinType
     * The CoinType
     */
    public void setCoinType(String CoinType) {
        this.CoinType = CoinType;
    }

    public Currency withCoinType(String CoinType) {
        this.CoinType = CoinType;
        return this;
    }

    /**
     *
     * @return
     * The MaintenanceMode
     */
    public Boolean getMaintenanceMode() {
        return MaintenanceMode;
    }

    /**
     *
     * @param MaintenanceMode
     * The MaintenanceMode
     */
    public void setMaintenanceMode(Boolean MaintenanceMode) {
        this.MaintenanceMode = MaintenanceMode;
    }

    public Currency withMaintenanceMode(Boolean MaintenanceMode) {
        this.MaintenanceMode = MaintenanceMode;
        return this;
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(Currency).append(CurrencyLong).append(MinConfirmation).append(TxFee).append(IsActive).append(CoinType).append(MaintenanceMode).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Currency) == false) {
            return false;
        }
        Currency rhs = ((Currency) other);
        return new EqualsBuilder().append(Currency, rhs.Currency).append(CurrencyLong, rhs.CurrencyLong).append(MinConfirmation, rhs.MinConfirmation).append(TxFee, rhs.TxFee).append(IsActive, rhs.IsActive).append(CoinType, rhs.CoinType).append(MaintenanceMode, rhs.MaintenanceMode).isEquals();
    }

}