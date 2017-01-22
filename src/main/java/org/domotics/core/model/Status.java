package org.domotics.core.model;

public class Status {
    public final static String STATUS_ON = "on";
    public final static String STATUS_OFF = "off";

    private String status;
    private double value;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
