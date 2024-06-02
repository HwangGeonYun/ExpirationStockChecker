package org.example;

import java.sql.Timestamp;

public class Stock {

    private String code;
    private String name;
    Timestamp shortestexpirydate;

    String year;
    String month;
    String day;
    String hour;
    String minute;
    String second;
    boolean exist;
    public Stock(String CODE, String NAME, Timestamp SHORTESTEXPIRYDATE, boolean EXIST){
        code = CODE;
        name = NAME;
        shortestexpirydate = SHORTESTEXPIRYDATE;
        exist = EXIST;
        setTimeValues(shortestexpirydate);
    }

    private void setTimeValues(Timestamp shortestexpirydate) {
        String timeString = shortestexpirydate + "";
        String[] timeValues = timeString.split("[- :]");
        year = timeValues[0];
        month = timeValues[1];
        day = timeValues[2];
        hour = timeValues[3];
        minute = timeValues[4];
        second = timeValues[5];
    }

    public String getCode() {
        return code;
    }

    public Timestamp getShortestexpirydate() {
        return shortestexpirydate;
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

    public String getYear() {
        return year;
    }

    public String getMonth() {
        return month;
    }

    public String getDay() {
        return day;
    }

    public String getHour() {
        return hour;
    }

    public String getMinute() {
        return minute;
    }

    public String getSecond() {
        return second;
    }

    public boolean isExist() {
        return exist;
    }
}
