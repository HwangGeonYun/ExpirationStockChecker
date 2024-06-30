package org.example;

import javafx.application.Platform;
import javafx.stage.Stage;
import org.example.view.StockTable;

import java.sql.SQLException;

public class Main{

//    public static void main(String[] args) {
//        StockModel model = new StockModel();
//        try {
//            System.out.print(model.getNextCode());
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public static void main(String[] args) throws SQLException {
        StockTable stockTable = new StockTable();

        Platform.startup(()->{
            try {
                stockTable.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    }
}

