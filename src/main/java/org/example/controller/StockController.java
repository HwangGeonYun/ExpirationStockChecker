package org.example.controller;

import org.example.Stock;
import org.example.model.*;

import java.sql.*;
import java.sql.SQLException;
import java.util.LinkedList;

public class StockController {

    private StockModel stockModel = new StockModel();

    //재고 추가하고
    public void addData(String CODE, String NAME, Timestamp SHORTESTEXPIRYDATE, boolean EXIST) throws SQLException {
        stockModel.addData(CODE, NAME, SHORTESTEXPIRYDATE, EXIST);
    }
    //전체 재고 가져오기(재고 표시용)
    public LinkedList<Stock> search_all() throws SQLException {
        LinkedList<Stock> stocks = stockModel.search_all();

        for (Stock stock : stocks) {
            System.out.println(stock.toString());
        }
        //test용

        return stockModel.search_all();
    }
    //재고 하나만 가져오기(특정 재고를 클릭햇을 때 실행 되며, 코드를 통해서 찾는다.)

    //폐기를 출력하기 위한 함수. 이번 프젝의 꽃
    public LinkedList<Stock> searchExpiredDatas() throws SQLException {
        return stockModel.searchExpiredDatas();
}
    //부분 이름을 가진 재고들 출력
    public LinkedList<Stock> searchDatasByName(String NAME) throws SQLException {
        return stockModel.searchDatasByName(NAME);
    }

    public LinkedList<Stock> searchDatasByCode(String CODE) throws SQLException {
        return stockModel.searchDatasByCode(CODE);
    }
    //code말고 모든 것을바꿀 수 있음. 코드를 바꾸려면 delete한다음 새로 코드를 파라
    public void updateData(String CODE, String NAME, Timestamp SHORTESTEXPIRYDATE, boolean EXIST) throws SQLException {
        stockModel.updateData(CODE, NAME, SHORTESTEXPIRYDATE, EXIST);
    }
    //코드 없애고 싶을 때
    public void deleteData(String CODE) throws SQLException {
        stockModel.deleteData(CODE);
    }
    //test용
    public void printData(LinkedList<Stock> stockList){
        for(Stock stock: stockList){
            System.out.println(stock.toString());
        }
    }


}
