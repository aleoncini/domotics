package org.domotics.core.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Zone {
    private String uuid;
    private String description;
    List<String> terminalUuids;

    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    public Zone() {
        uuid = uuid();
        this.description = "null";
        terminalUuids = new ArrayList<String>();
    }

    public Zone setDescription(String description){
        this.description = description;
        return this;
    }

    public Zone setUUID(String uuid){
        this.uuid = uuid;
        return this;
    }

    public String getUuid() {
        return uuid;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTerminalUuids() {
        return terminalUuids;
    }

    public void setTerminalUuids(List<String> list){
        this.terminalUuids = list;
    }

    public void associateTerminal(String terminalUuid){

    }
}
