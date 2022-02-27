package com.kadomos.apigw.database.beans;
import javax.persistence.*;

/**
 * The entity is representing the transactions table in the database
 * which holds the transactional history of the account
 */
@Entity
@Table(name = "transaction_b")
public class Transaction {

    /**
     * An immutable identifier representing the transaction in the database
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The description of the transaction
     */
    private String description;

    /**
     * The value of the transaction being processed
     */
    private Float value;

    /**
     * The last created date of the transaction represented in milliseconds
     */
    private Long createdDate;

    /**
     * The last modified date of the transaction represented in milliseconds
     */
    private Long modifiedDate;

    /**
     * Holds the balance of the account representing last calculated value after the last
     * transaction was completed
     */
    private Float balance;

    /**
     * The type of the transaction Credit or Debit
     */
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public Long getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Long modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
