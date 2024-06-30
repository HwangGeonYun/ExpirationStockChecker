package org.example.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.Stock;
import org.example.controller.StockController;
import javafx.util.converter.IntegerStringConverter;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class StockAdd extends Application {
    private StockController stockController;
    private TableView stockTable;
    private String CODE;
    public StockAdd(StockController stockController, TableView stockTable) {
        this.stockController = stockController;
        this.stockTable = stockTable;
        try {
            //System.out.print(stockController.getNextCode());
            this.CODE = stockController.getNextCode();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    LocalDateTime now;
    Label code;
    Label name;
    Label shortestexpirydate;
    Label year;
    Label month;
    Label day;
    Label hour;
    Label minute;
    Label second;
    Label exist;

    TextField codeField;
    TextField nameField;

    TextField yearField;
    TextField monthField;
    TextField dayField;
    TextField hourField;
    TextField minuteField;
    TextField secondField;


    @Override
    public void start(Stage primaryStage) throws Exception {
        now = LocalDateTime.now();

        code = new Label("코드");
        name = new Label("이름");
        shortestexpirydate = new Label("근접유통기한");
        year = new Label("년");
        month = new Label("월");
        day = new Label("일");
        hour = new Label("시");
        minute = new Label("분");
        second = new Label("초");
        exist = new Label("재고 유무");
        codeField = new TextField(this.CODE);
        codeField.setDisable(true);
        nameField = new TextField();

        yearField = new TextField(now.getYear() + "");
        monthField = new TextField(now.getMonthValue() + "");
        dayField = new TextField(now.getDayOfMonth() + "");
        hourField = new TextField(now.getHour() + "");
        minuteField = new TextField(now.getMinute() + "");
        secondField = new TextField(now.getSecond() + "");

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
        expiraryDatePane.add(second, 11, 0);

        //setNumericInputOnly(codeField);
//        setNumericInputOnly(yearField);
//        setNumericInputOnly(monthField);
//        setNumericInputOnly(dayField);
//        setNumericInputOnly(hourField);
//        setNumericInputOnly(minuteField);
//        setNumericInputOnly(secondField);

        ChoiceBox exitBox = new ChoiceBox();
        exitBox.getItems().addAll("재고있음", "재고없음");
        exitBox.setValue("재고있음");

        GridPane mainLayout = new GridPane();

        mainLayout.add(code, 1, 1);
        mainLayout.add(codeField, 2, 1);
        mainLayout.add(name, 1, 3);
        mainLayout.add(nameField, 2, 3);
        mainLayout.add(shortestexpirydate, 1, 5);
        mainLayout.add(expiraryDatePane, 2, 5);
        //mainLayout.add(exist, 1, 7);
        //mainLayout.add(exitBox, 2, 7);


        Button doButton = new Button("확인");
        Button cancleButton = new Button("취소");
        mainLayout.add(doButton, 3, 9);
        mainLayout.add(cancleButton, 4, 9);

        doButton.setOnAction(event ->{
            Timestamp expiraryDate = Timestamp.valueOf(now.getYear() + "-" + (now.getMonthValue())+"-" + now.getDayOfMonth()
                + " " + now.getHour() + ":" + now.getMinute() + ":" + now.getSecond());



            try {
                stockController.addData(codeField.getText(), nameField.getText() , expiraryDate, exitBox.getValue().equals("재고있음"));
                inputStocksToTable(stockTable, searchStock(""));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            primaryStage.close();
        });

        cancleButton.setOnAction(event->{
            primaryStage.close();
        });

        Scene scene = new Scene(mainLayout);

        primaryStage.setTitle("재고 추가");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private LinkedList<Stock> searchStock(String name) throws SQLException {
        return (name.isEmpty()) ? stockController.search_all() : stockController.searchDatasByName(name);
    }

    private void inputStocksToTable(TableView<Stock> stockTable, LinkedList<Stock> stockList){

        stockTable.setItems(FXCollections.observableList(stockList));
    }

    private void setNumericInputOnly(TextField textField) {
        TextFormatter<Integer> textFormatter = new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        });
        textField.setTextFormatter(textFormatter);
    }

//    private boolean isDuplicate(){
//        searchStock(String )
//    }

//    private boolean isExactNum(){
//        if(codeField.getText())
//    }

}
