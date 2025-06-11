package com.saif.vuquizapp;

public class model {

    // this name is same as name in the firebase
String name;
int sets;
String url;

public model(){
    //Empty constructor for firebase
}

    public model(String name, int sets, String url) {
        this.name = name;
        this.sets = sets;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
