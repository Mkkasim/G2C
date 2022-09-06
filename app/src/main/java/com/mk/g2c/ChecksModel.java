package com.mk.g2c;

public class ChecksModel {

    String checksName,key;

    public ChecksModel() {
    }

    public ChecksModel(String checksName,String key) {
        this.checksName = checksName;
        this.key = key;
    }

    public String getChecksName() {
        return checksName;
    }

    public void setChecksName(String checksName) {
        this.checksName = checksName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
