package org.example;

import javafx.application.Application;
import org.example.view.StockInformation;
import org.example.view.StockTable;

import java.sql.SQLException;

public class Main{
    StockInformation stockInformation = new StockInformation();

    public static void main(String[] args) throws SQLException {
        StockTable stockTable = new StockTable();
        //StockController stockController = new StockController();



        Application.launch(StockTable.class, args);
        //StockModel stockModel = new StockModel();//stockController = new StockController();

        //stockController.seachDatasByName("");
        //stockController.addData("8801725002056", "서주)탱글탱글골드키위40g", Timestamp.valueOf("2024-05-17 11:45:32"), true);

        //stockController.updateData("8801725002056", "서주)탱글탱글골드키위40g", Date.valueOf("2024-10-24"), false);
        //stockController.deleteData("8801725002056");
        //stockController.searchExpiredDatas();
        //stockController.printData(stockController.searchExpiredDatas());
//        try {
            //stockController.search_all();
//        }catch(SQLException e){
//            System.out.println("재고가 없습니다.");
//        }

        //System.out.println("Hello world!");
    }
}

