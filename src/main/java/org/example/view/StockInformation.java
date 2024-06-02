package org.example.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.Stock;
import org.example.controller.StockController;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;


public class StockInformation extends Application {
    Stock _stock;

    StockController _stockController;

    TableView _stockTable;

    StockInformation(Stock stock, StockController stockController, TableView stockTable){
        _stock = stock;
        _stockController = stockController;
        _stockTable = stockTable;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");



        //primaryStage = primaryStage;
        Label code = new Label("코드");
        Label name = new Label("이름");
        Label shortestexpirydate = new Label("근접유통기한 ");
        Label year = new Label("년");
        Label month = new Label("월");
        Label day = new Label("일");
        Label hour = new Label("시");
        Label minute = new Label("분");
        Label seconde = new Label("초");
        Label exist= new Label("재고 유무");



        TextField codeField = new TextField(_stock.getCode());
        codeField.setDisable(true);
        TextField nameField = new TextField(_stock.getName());

        TextField yearField = new TextField(_stock.getYear() + "");
        TextField monthField = new TextField(_stock.getMonth() + "");
        TextField dayField = new TextField(_stock.getDay() + "");
        TextField hourField = new TextField(_stock.getHour() + "");
        TextField minuteField = new TextField(_stock.getMinute() + "");
        TextField secondField = new TextField(_stock.getSecond() + "");

        yearField.setMaxWidth(60);
        monthField.setMaxWidth(40);
        dayField.setMaxWidth(40);
        hourField.setMaxWidth(40);
        minuteField.setMaxWidth(40);
        secondField.setMaxWidth(40);
        GridPane expiraryDatePane = new GridPane();

        expiraryDatePane.add(yearField, 0, 0);
        expiraryDatePane.add(year, 1, 0);
        expiraryDatePane.add(monthField, 2, 0);
        expiraryDatePane.add(month, 3, 0);
        expiraryDatePane.add(dayField, 4, 0);
        expiraryDatePane.add(day, 5, 0);
        expiraryDatePane.add(hourField, 6, 0);
        expiraryDatePane.add(hour, 7, 0);
        expiraryDatePane.add(minuteField, 8, 0);
        expiraryDatePane.add(minute, 9, 0);
        expiraryDatePane.add(secondField, 10, 0);
        expiraryDatePane.add(seconde, 11, 0);

        ChoiceBox exitBox = new ChoiceBox();
        exitBox.getItems().addAll("재고있음", "재고없음");
        exitBox.setValue("재고있음");

        //SEDField.setText(_stock.getShortestexpirydate());

        if(_stock.isExist() == true) exitBox.setValue("재고있음"); else exitBox.setValue("재고없음");

        GridPane mainLayout = new GridPane();

        mainLayout.add(code, 1, 1);
        mainLayout.add(codeField, 2, 1);
        mainLayout.add(name, 1, 3);
        mainLayout.add(nameField, 2, 3);
        mainLayout.add(shortestexpirydate, 1, 5);
        mainLayout.add(expiraryDatePane, 2, 5);
        mainLayout.add(exist, 1, 8);
        mainLayout.add(exitBox, 2, 8);


        Button doButton = new Button("확인");
        Button cancleButton = new Button("취소");
        mainLayout.add(doButton, 3, 9);
        mainLayout.add(cancleButton, 4, 9);

        //event들

        doButton.setOnAction(event ->{
            Timestamp expiraryDate = Timestamp.valueOf(_stock.getYear() + "-" + _stock.getMonth() + "-" +
                    _stock.getDay() + " " + _stock.getHour() + ":" + _stock.getMinute() + ":" + _stock.getSecond());

            try {
                _stockController.addData(codeField.getText(), nameField.getText() , expiraryDate, exitBox.getValue().equals("재고있음"));
                inputStocksToTable(_stockTable, searchStock(""));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            primaryStage.close();
        });

        cancleButton.setOnAction(event->{
            primaryStage.close();
        });

        Scene scene = new Scene(mainLayout);

        primaryStage.setTitle("재고 정보");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private LinkedList<Stock> searchStock(String name) throws SQLException {
        return (name.isEmpty()) ? _stockController.search_all() : _stockController.searchDatasByName(name);
    }

    private void inputStocksToTable(TableView<Stock> stockTable, LinkedList<Stock> stockList){

        stockTable.setItems(FXCollections.observableList(stockList));
    }

}
