package com.zyx.admin.domain;


import java.math.BigDecimal;
import java.util.Date;

public class MerchantInfo {

    private Integer id;
    private String merchantId;
    private String name;
    private String contacts;
    private String phone;
    private String email;
    private Integer busType;
    private Integer accessType;
    private Integer isSigned;
    private Date contractDueDate;
    private BigDecimal mPercentage;
    private BigDecimal sPercentage;
    private Integer creator;
    private Integer updator;

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBusType() {
        return busType;
    }

    public void setBusType(Integer busType) {
        this.busType = busType;
    }

    public Integer getAccessType() {
        return accessType;
    }

    public void setAccessType(Integer accessType) {
        this.accessType = accessType;
    }

    public Integer getIsSigned() {
        return isSigned;
    }

    public void setIsSigned(Integer isSigned) {
        this.isSigned = isSigned;
    }

    public Date getContractDueDate() {
        return contractDueDate;
    }

    public void setContractDueDate(Date contractDueDate) {
        this.contractDueDate = contractDueDate;
    }

    public BigDecimal getmPercentage() {
        return mPercentage;
    }

    public void setmPercentage(BigDecimal mPercentage) {
        this.mPercentage = mPercentage;
    }

    public BigDecimal getsPercentage() {
        return sPercentage;
    }

    public void setsPercentage(BigDecimal sPercentage) {
        this.sPercentage = sPercentage;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getUpdator() {
        return updator;
    }

    public void setUpdator(Integer updator) {
        this.updator = updator;
    }


}
