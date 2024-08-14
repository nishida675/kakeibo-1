package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Balance;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {
	
//	mysql
//	  @Query(value="SELECT * FROM balance_table WHERE user_id = :userId AND YEAR(date) = :year", nativeQuery=true)
//	  List<Balance> findByYearData(@Param("userId") Integer userId, @Param("year") Integer year);
//	  
//	  @Query(value="SELECT * FROM balance_table WHERE user_id = :userId AND YEAR(date) = :year AND MONTH(date) = :month", nativeQuery=true)
//	  List<Balance> findByYearAndMonthData(@Param("userId") Integer userId, @Param("year") Integer year, @Param("month") Integer month);
//
//	  @Modifying
//	  @Transactional
//	  @Query(value = "DELETE FROM balance_table WHERE user_id = :userId AND balance_id = :balanceId", nativeQuery = true)
//	  void deleteByUserIdAndBalanceId(@Param("userId") int userId, @Param("balanceId") int balanceId);
//	  
//	  @Modifying
//	    @Transactional
//	    @Query(value = "UPDATE balance_table SET balance_name = :balanceName, date = :date, price = :price, category = :category WHERE user_id = :userId AND balance_id = :balanceId", nativeQuery = true)
//	    void editBalance(@Param("userId") Integer userId, @Param("balanceId") Integer balanceId, @Param("balanceName") String balanceName, @Param("date") LocalDate date, @Param("price") Integer price, @Param("category") String category);
	
//	posgre
	
	 @Query(value="SELECT * FROM balance_table WHERE user_id = :userId AND EXTRACT(YEAR FROM date) = :year", nativeQuery=true)
	  List<Balance> findByYearData(@Param("userId") Integer userId, @Param("year") Integer year);
	  
	  @Query(value="SELECT * FROM balance_table WHERE user_id = :userId AND EXTRACT(YEAR FROM date) = :year AND EXTRACT(MONTH FROM date) = :month", nativeQuery=true)
	  List<Balance> findByYearAndMonthData(@Param("userId") Integer userId, @Param("year") Integer year, @Param("month") Integer month);

	  @Modifying
	  @Transactional
	  @Query(value = "DELETE FROM balance_table WHERE user_id = :userId AND balance_id = :balanceId", nativeQuery = true)
	  void deleteByUserIdAndBalanceId(@Param("userId") int userId, @Param("balanceId") int balanceId);
	  
	  @Modifying
	  @Transactional
	  @Query(value = "UPDATE balance_table SET balance_name = :balanceName, date = :date, price = :price, category = :category WHERE user_id = :userId AND balance_id = :balanceId", nativeQuery = true)
	  void editBalance(@Param("userId") Integer userId, @Param("balanceId") Integer balanceId, @Param("balanceName") String balanceName, @Param("date") LocalDate date, @Param("price") Integer price, @Param("category") String category);
	
}
