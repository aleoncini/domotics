package org.domotics.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    //{"result":"success","GPIO":"13","status":"0"}
    private String result;
    private String GPIO;
    private String status;
    private String value;

    public String getStatus() {
        return status;
    }
    public String getResult() {
        return result;
    }
    public String getGPIO() {
        return GPIO;
    }
    public String getValue() {
        return value;
    }

    public Status setStatus(String status) {
        this.status = status;
        return this;
    }

    /*
    public Status setStatus(int status) {
        this.status = "" + status;
        return this;
    }

    public Status setStatus(double status) {
        this.status = "" + status;
        return this;
    }
    */

    public Status setValue(String value) {
        this.value = value;
        return this;
    }

    /*
    public Status setValue(int value) {
        this.value = "" + value;
        return this;
    }

    public Status setValue(double value) {
        this.value = "" + value;
        return this;
    }
    */

    public Status setResult(String result) {
        this.result = result;
        return this;
    }

    @JsonProperty("GPIO")
    public Status setGPIO(String GPIO) {
        this.GPIO = GPIO;
        return this;
    }

    /*
    public Status setGPIO(int GPIO) {
        this.GPIO = "" + GPIO;
        return this;
    }
    */
}
