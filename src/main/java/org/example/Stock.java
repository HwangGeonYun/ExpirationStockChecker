package org.example;

import java.sql.Timestamp;

public class Stock {

    private String code;
    private String name;

    Timestamp shortestexpirydate;
    boolean exist;
    public Stock(String CODE, String NAME, Timestamp SHORTESTEXPIRYDATE, boolean EXIST){
        code = CODE;
        name = NAME;
        shortestexpirydate = SHORTESTEXPIRYDATE;
        exist = EXIST;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", shortestexpirydate=" + shortestexpirydate +
                ", exist=" + exist +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getShortestexpirydate() {
        return shortestexpirydate+"";
    }

    public boolean isExist() {
        return exist;
    }
}
