package com.abodsy.abodsy;

public class Booking {

    private String SERVICE_PROVIDER_NAME;
    private String TYPE_OF_SERVICE;
    private String SERVICE_PROVIDER_MOBILE;
    private String DATE;
    private String TIME;
    private String ADDRESS;
    private String MOBILE;
    private String SERVICE_PROVIDER_RATING;

    public Booking() {
    }

    public Booking(String SERVICE_PROVIDER_NAME, String TYPE_OF_SERVICE, String SERVICE_PROVIDER_MOBILE,
                   String DATE, String TIME, String ADDRESS, String MOBILE, String SERVICE_PROVIDER_RATING) {

        this.SERVICE_PROVIDER_NAME = SERVICE_PROVIDER_NAME;
        this.TYPE_OF_SERVICE = TYPE_OF_SERVICE;
        this.SERVICE_PROVIDER_MOBILE = SERVICE_PROVIDER_MOBILE;
        this.DATE = DATE;
        this.TIME = TIME;
        this.ADDRESS = ADDRESS;
        this.MOBILE = MOBILE;
        this.SERVICE_PROVIDER_RATING = SERVICE_PROVIDER_RATING;

    }

    public String getSERVICE_PROVIDER_NAME() {
        return SERVICE_PROVIDER_NAME;
    }

    public void setSERVICE_PROVIDER_NAME(String SERVICE_PROVIDER_NAME) {
        this.SERVICE_PROVIDER_NAME = SERVICE_PROVIDER_NAME;
    }

    public String getTYPE_OF_SERVICE() {
        return TYPE_OF_SERVICE;
    }

    public void setTYPE_OF_SERVICE(String TYPE_OF_SERVICE) {
        this.TYPE_OF_SERVICE = TYPE_OF_SERVICE;
    }

    public String getSERVICE_PROVIDER_MOBILE() {
        return SERVICE_PROVIDER_MOBILE;
    }

    public void setSERVICE_PROVIDER_MOBILE(String SERVICE_PROVIDER_MOBILE) {
        this.SERVICE_PROVIDER_MOBILE = SERVICE_PROVIDER_MOBILE;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getSERVICE_PROVIDER_RATING() {
        return SERVICE_PROVIDER_RATING;
    }

    public void setSERVICE_PROVIDER_RATING(String SERVICE_PROVIDER_RATING) {
        this.SERVICE_PROVIDER_RATING = SERVICE_PROVIDER_RATING;
    }
}
