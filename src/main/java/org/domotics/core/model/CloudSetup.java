package org.domotics.core.model;

import java.util.UUID;

public class CloudSetup {
    private String uuid;
    private String token;

    public CloudSetup(){
        this.uuid = UUID.randomUUID().toString();
        this.token = "none";
    }

    public String getUuid() {
        return uuid;
    }

    public CloudSetup setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getToken() {
        return token;
    }

    public CloudSetup setToken(String token) {
        this.token = token;
        return this;
    }
}
