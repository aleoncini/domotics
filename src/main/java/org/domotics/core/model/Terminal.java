package org.domotics.core.model;

import java.util.UUID;

public class Terminal {
    private String uuid;
    private String controllerUuid;
    private int pin;
    private int type;
    private String description;
    private Status status;

    public final static int MOCK_TYPE = 0;
    public final static int SENSOR_TYPE = 1;
    public final static int RELAY_TYPE = 2;

    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    public Terminal(){
        this.uuid = uuid();
        this.description = "null";
    }

    public String getUuid() {
        return uuid;
    }

    public Terminal setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getControllerUuid() {
        return controllerUuid;
    }

    public Terminal setControllerUuid(String controllerUuid) {
        this.controllerUuid = controllerUuid;
        return this;
    }

    public int getPin() {
        return pin;
    }

    public Terminal setPin(int pin) {
        this.pin = pin;
        return this;
    }

    public int getType() {
        return type;
    }

    public Terminal setType(int type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Terminal setDescription(String description) {
        this.description = description;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}