
package budgetapp.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

public class Transaction {
    
    private int id;
    private Double amount;
    private Enum month;

    public Transaction(Double amount) {       
        this.amount = amount;
        
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        this.month = localDate.getMonth();        
    }
    
    public Transaction(Double amount, int month) {    
        this.amount = amount;       
        this.month = Month.of(month);    
    }
    
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
        return "Transaction amount: " + getAmount();
    }
    
    
    
    
}
