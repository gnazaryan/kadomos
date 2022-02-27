package com.kadomos.apigw.database;

import com.kadomos.apigw.database.beans.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    /**
     * The repository accessor finds the last known transaction
     * @return
     */
    @Query(nativeQuery = true, value="SELECT * FROM transaction_b tr ORDER BY tr.created_date DESC LIMIT 1")
    List<Transaction> findLastCreated();

    /**
     * The api is responsible for inserting a row of transaction into the transaction table
     * @param description
     * @param value
     * @param balance
     * @param modified_date
     * @param created_date
     * @return
     */
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO transaction_b (description,value,balance,modified_date,created_date, type) VALUES (:description,:value,:balance,:modified_date,:created_date, :type)")
    @Transactional
    Integer insert(@Param("description") String description, @Param("value") Float value, @Param("balance") Float balance, @Param("modified_date") Long modified_date, @Param("created_date") Long created_date, @Param("type") String type);
}
