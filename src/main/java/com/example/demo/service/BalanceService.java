package com.example.demo.service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Balance;
import com.example.demo.repository.BalanceRepository;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;

    public Balance saveBalance(Balance balance) {
        return balanceRepository.saveAndFlush(balance);
    }
    
    public Map<String, Object> getYear(String UserId, String dataYear) {
        Map<String, Object> result = new HashMap<>();
        
        Integer userId = Integer.parseInt(UserId);
        Integer year = Integer.parseInt(dataYear);
        
        List<Balance> dbYearData = balanceRepository.findByYearData(userId, year);
        
        // 月ごとの合計を保持するためのマップ
        Map<Integer, Integer> monthlyTotal = new HashMap<>();
        
        // 各月の初期化
        for (int month = 1; month <= 12; month++) {
            monthlyTotal.put(month, 0);
        }
        
        // データをループして月ごとの合計を計算
        for (Balance balance : dbYearData) {
            // balance.getDate() から月を取得（例: LocalDateを使用している場合）
            int month = balance.getDate().getMonthValue(); // 月を取得 (1から12の範囲)
            int price = balance.getPrice(); // int 型の price を取得
            if (!balance.getCategory().equals("収入")){
            	price = - price;
            }
            		
            // 既存の合計に追加
            monthlyTotal.put(month, monthlyTotal.get(month) + price);
        }
        
        // 結果をresultマップに格納
        for (Map.Entry<Integer, Integer> entry : monthlyTotal.entrySet()) {
            result.put("Month " + entry.getKey(), entry.getValue());
        }
        
        
        return result;
    }
    
    public Map<String, Object> getMonth(String UserId, String dataYear, String dataMonth) {
    	Map<String, Object> result = new HashMap<>();

    	// 引数を適切な型に変換
    	Integer userId = Integer.parseInt(UserId);
    	Integer year = Integer.parseInt(dataYear);
    	Integer month = Integer.parseInt(dataMonth);

    	// 指定されたユーザーID、年、月でデータを取得
    	List<Balance> dbYearData = balanceRepository.findByYearAndMonthData(userId, year, month);
    	dbYearData.sort(Comparator.comparing(Balance::getDate));

    	// カテゴリごとに合計を計算
    	Map<String, Integer> categorySum = dbYearData.stream()
    	    .collect(Collectors.groupingBy(
    	        Balance::getCategory, 
    	        Collectors.summingInt(Balance::getPrice)
    	    ));

    	// 合計されたデータを価格の大きい順にソート
    	List<Map.Entry<String, Integer>> sortedEntries = categorySum.entrySet().stream()
    	    .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
    	    .collect(Collectors.toList());

    	// ランクを追加して新しいマップを作成
    	Map<String, Object> rankedCategorySum = new LinkedHashMap<>();
    	int rank = 1;
    	for (Map.Entry<String, Integer> entry : sortedEntries) {
    	    Map<String, Integer> categoryData = new HashMap<>();
    	    categoryData.put(entry.getKey(), entry.getValue());
    	    rankedCategorySum.put(String.valueOf(rank), categoryData);
    	    rank++;
    	}
    	result.put("categorySums", rankedCategorySum);
        result.put("monthData", dbYearData);

        return result;
    }
    
    public void deleteBalance(String userId, String balanceId) {
    	   int userIdInt = Integer.parseInt(userId);
           int balanceIdInt = Integer.parseInt(balanceId);
           balanceRepository.deleteByUserIdAndBalanceId(userIdInt, balanceIdInt);
    }
    
    public void editBalance(Balance balance) {
    	  Integer userId = balance.getUserId();
          Integer balanceId = balance.getBalanceId();
          String balanceName = balance.getBalanceName();
          LocalDate date = balance.getDate();
          Integer price = balance.getPrice();
          String category = balance.getCategory();
        balanceRepository.editBalance(userId, balanceId, balanceName, date, price, category);
 }

}