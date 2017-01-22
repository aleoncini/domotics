package org.domotics.core.model;

import java.util.UUID;

public class Controller {
    private String description;
    private String uuid;
    private String ipAddress;
    private String type;
    private final static String RASPBERRY_TYPE = "Raspberry3-model_B";
    private final static String ARDUINO_TYPE = "Arduino";

    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    public Controller(){
        uuid = uuid();
    }

    public String getDescription() {
        return description;
    }

    public String getUuid() {
        return uuid;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getType() {
        return type;
    }

    public Controller setDescription(String description){
        this.description = description;
        return this;
    }

    public Controller setUUID(String uuid){
        this.uuid = uuid;
        return this;
    }

    public Controller setAddress(String ipAddress){
        this.ipAddress = ipAddress;
        return this;
    }

    public Controller setType(String type){
        this.type = type;
        return this;
    }

}
