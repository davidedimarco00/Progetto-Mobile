package com.app.mypresence.model;

public class StatisticsContainer {
    private String info;
    private float value;

    public StatisticsContainer(final String info, final float value){
        this.info = info;
        this.value = value;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
