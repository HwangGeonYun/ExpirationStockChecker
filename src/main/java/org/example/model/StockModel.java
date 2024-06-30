package org.example.model;

import org.example.Stock;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.Properties;

public class StockModel {

    static final String JDBC_URL;
    static final String USERNAME;
    static final String PASSWORD;

    static {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("db.properties")) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load database properties.", e);
        }
        JDBC_URL = properties.getProperty("jdbc.url");
        USERNAME = properties.getProperty("jdbc.username");
        PASSWORD = properties.getProperty("jdbc.password");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    Connection conn;

    public StockModel() {
        try {
            conn = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    public void addData(String CODE, String NAME, Timestamp SHORTESTEXPIRYDATE, boolean EXIST) throws SQLException {
        String sql = "Insert INTO stock (CODE, NAME, SHORTESTEXPIRYDATE, EXIST) values (?, ?, ?, ?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, CODE);
        statement.setString(2, NAME);
        statement.setTimestamp(3, SHORTESTEXPIRYDATE);
        statement.setBoolean(4, EXIST);
        statement.executeUpdate();
        statement.close();
    }
    public LinkedList<Stock> search_all() throws SQLException {
        String sql = "Select * from stock";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        LinkedList<Stock> stockList = new LinkedList<Stock>();
        while(resultSet.next()){
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            Timestamp shortestexpirydate = resultSet.getTimestamp(3);
            boolean exist = resultSet.getBoolean(4);

            Stock stock = new Stock(code, name, shortestexpirydate, exist);
            stockList.add(stock);
        }

        statement.close();

        return stockList;
    }

    public int getLastCode() throws SQLException{


        LinkedList<Stock> lists = search_all();

        if(search_all().isEmpty()){
            System.out.print("empty");
            return 0;
        }

        System.out.print("hi");

        return lists.stream()
             .map(list->Integer
             .valueOf(list.getCode()))
             .sorted((o1, o2)-> o2 - o1)
             .findFirst()
             .orElse(0);
    }

//    public LinkedList<Stock> searchDatas(){
//
//    }
    public Stock searchData(String CODE) throws SQLException {
        String sql = "Select * FROM stock WHERE CODE = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        String code = resultSet.getString(1);
        String name = resultSet.getString(2);
        Timestamp shortestexpirydate = resultSet.getTimestamp(3);
        boolean exist = resultSet.getBoolean(4);

        Stock stock = new Stock(code, name, shortestexpirydate, exist);
        statement.close();

        return stock;
    }

    public LinkedList<Stock> searchDatasByName(String NAME) throws SQLException {
        String sql = "select * from stock where NAME like ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, "%" + NAME + "%");
        ResultSet resultSet = statement.executeQuery();

        LinkedList<Stock> stockList = new LinkedList<Stock>();

        while(resultSet.next()){
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            Timestamp shortestexpirydate = resultSet.getTimestamp(3);
            boolean exist = resultSet.getBoolean(4);

            Stock stock = new Stock(code, name, shortestexpirydate, exist);
            stockList.add(stock);
        }

        for(Stock stocks : stockList){
            System.out.println(stocks.toString());
        }

        return stockList;
    }

    public LinkedList<Stock> searchDatasByCode(String CODE) throws SQLException {
        String sql = "select * from stock where CODE like ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, "%" + CODE + "%");
        ResultSet resultSet = statement.executeQuery();

        LinkedList<Stock> stockList = new LinkedList<Stock>();

        while(resultSet.next()){
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            Timestamp shortestexpirydate = resultSet.getTimestamp(3);
            boolean exist = resultSet.getBoolean(4);

            Stock stock = new Stock(code, name, shortestexpirydate, exist);
            stockList.add(stock);
        }

        for(Stock stocks : stockList){
            System.out.println(stocks.toString());
        }

        return stockList;
    }

    public LinkedList<Stock> searchExpiredDatas() throws SQLException {
        String sql = "select * from stock where SHORTESTEXPIRYDATE < NOW()";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        LinkedList<Stock> stockList = new LinkedList<Stock>();

        while(resultSet.next()){
            String code = resultSet.getString(1);
            String name = resultSet.getString(2);
            Timestamp shortestexpirydate = resultSet.getTimestamp(3);
            boolean exist = resultSet.getBoolean(4);

            Stock stock = new Stock(code, name, shortestexpirydate, exist);
            stockList.add(stock);
        }

        return stockList;
    }
    public void updateData(String CODE, String newNAME, Timestamp newSHORTESTEXPIRYDATE, boolean newEXIST) throws SQLException{
        String sql = "Update stock Set NAME = ?, SHORTESTEXPIRYDATE = ?, EXIST = ? WHERE CODE = ?";
        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, newNAME);
        statement.setTimestamp(2, newSHORTESTEXPIRYDATE);
        statement.setBoolean(3, newEXIST);
        statement.setString(4, CODE);
        statement.executeUpdate();
        statement.close();

    }

    public void deleteData(String CODE) throws SQLException {
        String sql = "delete from stock where CODE = ?";
        PreparedStatement statement = conn.prepareStatement(sql);

        statement.setString(1, CODE);
        statement.executeUpdate();
        statement.close();
    }

}
