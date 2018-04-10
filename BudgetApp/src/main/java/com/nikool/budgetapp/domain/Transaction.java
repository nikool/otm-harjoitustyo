
package com.nikool.budgetapp.domain;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Transaction {
    
    private Double amount;
    private Enum month;

    public Transaction(Double amount) {
        
        this.amount = amount;
        
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.month = localDate.getMonth();
        
    }

    public Double getAmount() {
        return amount;
    }

    public Enum getMonth() {
        return month;
    }
    
    public String toString() {
        return "Transaction amount: " + getAmount();
    }
    
    
    
    
}