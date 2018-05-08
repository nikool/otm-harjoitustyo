
package budgetapp.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

/**
 * A class that depicts a single transaction
 */
public class Transaction {
    
    private int id;
    private Double amount;
    private Enum month;

    /**
     * Add a transaction to the current month
     * @param amount the transaction amount
     */
    public Transaction(Double amount) {       
        this.amount = amount;
        
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.month = localDate.getMonth();        
    }
    
    /**
     * Add a transaction to a specific month
     * @param amount the transaction amount
     * @param month month to which to add the transaction
     */
    public Transaction(Double amount, int month) {    
        this.amount = amount;       
        this.month = Month.of(month);    
    }
    
    /**
     * Add a transaction with an id to a specific month
     * @param id the transactions id
     * @param amount transaction amount
     * @param month month to which to add
     */
    public Transaction(int id, double amount, int month) {
        this.id = id;
        this.amount = amount;
        this.month = Month.of(month);
    }

    public Double getAmount() {
        return amount;
    }

    public Enum getMonth() {
        return month;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setMonth(Enum month) {
        this.month = month;
    }
    
    public String toString() {
        return "" + getAmount();
    }
    
    
    
    
}
