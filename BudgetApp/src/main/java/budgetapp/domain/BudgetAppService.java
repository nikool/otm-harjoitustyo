
package budgetapp.domain;

import budgetapp.dao.Dao;
import java.time.Month;
import java.util.List;

/*
 * The class responsible for the logic
 */

public class BudgetAppService {
    private Dao transactionDao;
    
    public BudgetAppService(Dao dao) {
        this.transactionDao = dao;
    }
    
    /**
     * Adding a new transaction to the current month
     *
     * @param amount the transaction amount
     */
    
    public boolean addTransaction(double amount) {
        if (amount == 0) {
            return false;
        } else {
            try {
                transactionDao.create(new Transaction(amount));
            } catch (Exception ex) {
                return false;
            }
            return true;
        }
    }
    
    public boolean addTransactionToMonth(double amount, int month) {
        if (amount == 0) {
            return false;
        } else {
            try {
                transactionDao.create(new Transaction(amount, month));
            } catch (Exception ex) {
                return false;
            }
            return true;
        }
    }
    
    public boolean addRecurringTransaction(double amount, int startingMonth, int endingMonth) {
        if (amount == 0) {
            return false;
        } else {
            for (int i = startingMonth; i <= endingMonth; i++) {
                try {
                    transactionDao.create(new Transaction(amount, i));
                } catch (Exception ex) {
                    return false;
                }
            }
            return true;
        }    
    }
    
    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public String printAllTransactions(List<Transaction> list) {
        StringBuilder sb = new StringBuilder();
        
        list.stream().forEach(t -> sb.append(t.getAmount()).append(", ").append(t.getMonth()).append("\n"));
        
        return sb.toString();
    }
    
    public boolean isMonth(String input) {
        
        try {
            Integer.parseInt(input);       
        } catch (NumberFormatException e) {
            return false;
        }
        
        int month = Integer.parseInt(input);
        if (month > 0 && month < 13) {
            return true;
        } else {
            return false;
        }       
    }
    
    public List<Transaction> getTransactionOfMonth(int month) {
        return transactionDao.findAllOfMonth(month);
    }
    
    public List<Transaction> getTransactions() {
        return transactionDao.findAll();
    }
}
