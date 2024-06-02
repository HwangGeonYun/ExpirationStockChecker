package org.example.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Stock;
import org.example.controller.StockController;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;

public class StockTable extends Application {
    private StockController stockController = new StockController();
    private StockAdd stockAdd;
    private StockInformation stockInformation;

    TableView stockTable;
    @Override
    public void start(Stage primaryStage) throws SQLException {

        VBox showStockBox = new VBox();
        HBox inputBox = new HBox();

        TextField inputForSearchStock = new TextField();
        Button searchStockButton = new Button("search");



        inputBox.setHgrow(inputForSearchStock, Priority.ALWAYS);
        inputBox.setHgrow(searchStockButton, Priority.NEVER);
        inputBox.getChildren().addAll(inputForSearchStock, searchStockButton);

        stockTable = new TableView();

        TableColumn<Stock, String> codeCol = new TableColumn<>("코드번호");
        TableColumn<Stock, String> nameCol = new TableColumn<>("이름");
        TableColumn<Stock, String> shortestexpiryCol = new TableColumn<>("근접유통기한");
        TableColumn<Stock, String> existCol = new TableColumn<>("재고 유무");

        //각 속성들에 있는 변수들을 문자열로 변경
        codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        //Timestamp는 propertyValueFactory로 속성 변형 불가능. 적절한 셀 값 팩토리를 직접 지정해야함
        shortestexpiryCol.setCellValueFactory(new PropertyValueFactory<>("shortestexpirydate"));
        existCol.setCellValueFactory(new PropertyValueFactory<>("exist"));


        stockTable.getColumns().addAll(codeCol, nameCol, shortestexpiryCol, existCol);


        //처음, 전체 재고리스트 표시
        inputStocksToTable(stockTable, searchStock(""));


        showStockBox.getChildren().addAll(inputBox, stockTable);

        HBox changeStockBox = new HBox();

        Button createStock = new Button("create");
        Button updateStock = new Button("update");
        Button deleteStock = new Button("delete");


        createStock.setMaxWidth(Double.MAX_VALUE);
        updateStock.setMaxWidth(Double.MAX_VALUE);
        deleteStock.setMaxWidth(Double.MAX_VALUE);

        changeStockBox.setHgrow(createStock, Priority.ALWAYS);
        changeStockBox.setHgrow(updateStock, Priority.ALWAYS);
        changeStockBox.setHgrow(deleteStock, Priority.ALWAYS);
        changeStockBox.getChildren().addAll(createStock, updateStock, deleteStock);

        BorderPane expiraryPane = new BorderPane();

        Button searchExpiredStock = new Button("폐기확인");
        expiraryPane.setRight(searchExpiredStock);

        //event 모음
        createStock.setOnAction(event ->{
            try {
                openStockInfo("create");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        updateStock.setOnAction(event->{
            try {
                openStockInfo("update");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        deleteStock.setOnAction(event->{
            Stock stock = (Stock)stockTable.getSelectionModel().getSelectedItem();
            if(stock != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("상품 삭제");
                alert.setHeaderText("현재 상품을 목록에서 없애시겠습니까?");
                alert.setContentText("상품의 삭제는 부디 신중하게 결정해주시길 부탁드립니다.");

                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK){
                    try {
                        stockController.deleteData(stock.getCode());
                        inputStocksToTable(stockTable, searchStock(""));

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        searchStockButton.setOnAction(event->{
            try {
                inputStocksToTable(stockTable, searchStock(inputForSearchStock.getText()));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        searchExpiredStock.setOnAction(event ->{
            try {
                inputStocksToTable(stockTable, searchExpiredStock());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


        // Main layout (VBox) to hold all examples
        VBox mainLayout = new VBox(10);
        mainLayout.getChildren().addAll(showStockBox, changeStockBox, expiraryPane);
        Scene scene = new Scene(mainLayout);

        primaryStage.setTitle("재고 근접 유통기한 확인표");
        primaryStage.setScene(scene);
        //크기 조정을 좀더 깔끔하게 하고 싶은데 어떻게 해야 하나: 해결이 필요
        primaryStage.setWidth(475);
        primaryStage.setResizable(false);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            // 終了処理を行う
            System.exit(0); // プログラムを終了させる
        });
    }
    private LinkedList<Stock> searchStock(String name) throws SQLException {
        return (name.isEmpty()) ? stockController.search_all() : stockController.searchDatasByName(name);
    }

    private LinkedList<Stock> searchExpiredStock() throws SQLException {
        return stockController.searchExpiredDatas();
    }

    //LinkedList는 ObservableList로 형변환 불과->FXCollections.observableList를 사용하자!
    private void inputStocksToTable(TableView<Stock> stockTable, LinkedList<Stock> stockList){

        stockTable.setItems(FXCollections.observableList(stockList));
    }

     public void openStockInfo(String which) throws Exception {
        switch(which) {
            case "create": {
                stockAdd = new StockAdd(stockController, stockTable);
                stockAdd.start(new Stage());
                break;
            }
            case "update": {
                Stock stock = (Stock)stockTable.getSelectionModel().getSelectedItem();
                if(stock != null) {
                    stockInformation = new StockInformation(stock, stockController, stockTable);
                    stockInformation.start(new Stage());
                }
            }
        }
     }


}
