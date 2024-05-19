package org.example.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class StockTable extends Application {
    private StockController stockController = new StockController();

    @Override
    public void start(Stage primaryStage) throws SQLException {

        VBox showStockBox = new VBox();
        HBox inputBox = new HBox();

        TextField inputForSearchStock = new TextField();
        Button searchStockButton = new Button("search");

        inputBox.setHgrow(inputForSearchStock, Priority.ALWAYS);
        inputBox.setHgrow(searchStockButton, Priority.NEVER);
        inputBox.getChildren().addAll(inputForSearchStock, searchStockButton);

        TableView stockTable = new TableView();

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
        expiraryPane.setRight(new Button("폐기 확인"));

        //vBox.getChildren().add(inputBox);
        //hBox.getChildren().add(new Button("Button 2"));

        // VBox Example
//        VBox vBox = new VBox();
//        vBox.getChildren().add(new Button("Button 3"));
//        vBox.getChildren().add(new Button("Button 4"));

        // BorderPane Example


//        BorderPane borderPane = new BorderPane();
//        borderPane.setTop(new Button("Top"));
//        borderPane.setBottom(new Button("Bottom"));
//        borderPane.setLeft(new Button("Left"));
//        borderPane.setRight(new Button("Right"));
//        borderPane.setCenter(new Button("Center"));
//
//        // GridPane Example
//        GridPane gridPane = new GridPane();
//        gridPane.add(new Button("Button 5"), 0, 0);
//        gridPane.add(new Button("Button 6"), 1, 0);
//        gridPane.add(new Button("Button 7"), 0, 1);
//        gridPane.add(new Button("Button 8"), 1, 1);
//
//        // FlowPane Example
//        FlowPane flowPane = new FlowPane();
//        flowPane.getChildren().add(new Button("Button 9"));
//        flowPane.getChildren().add(new Button("Button 10"));
//
//        // TilePane Example
//        TilePane tilePane = new TilePane();
//        tilePane.getChildren().add(new Button("Button 11"));
//        tilePane.getChildren().add(new Button("Button 12"));
//
//        // AnchorPane Example
//        AnchorPane anchorPane = new AnchorPane();
//        Button button = new Button("Button 13");
//        AnchorPane.setTopAnchor(button, 10.0);
//        AnchorPane.setLeftAnchor(button, 10.0);
//        anchorPane.getChildren().add(button);
//
//        // StackPane Example
//        StackPane stackPane = new StackPane();
//        stackPane.getChildren().add(new Button("Button 14"));

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
    }
    //LinkedList는 ObservableList로 형변환 불과->FXCollections.observableList를 사용하자!
    private LinkedList<Stock> searchStock(String name) throws SQLException {
        return (name.isEmpty()) ? stockController.search_all() : stockController.searchDatasByName(name);
    }
    private void inputStocksToTable(TableView<Stock> stockTable, LinkedList<Stock> stockList){

        stockTable.setItems(FXCollections.observableList(stockList));
    }
}
