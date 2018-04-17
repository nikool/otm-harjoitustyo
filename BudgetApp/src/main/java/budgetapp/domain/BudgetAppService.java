
package budgetapp.domain;

import budgetapp.dao.InitialDao;
import java.util.List;

public class BudgetAppService {
    private InitialDao initDao = new InitialDao();
    
    public boolean addTransaction(double amount) {
        if (amount == 0) {
            return false;
        } else {
            initDao.save(new Transaction(amount));
            return true;
        }
    }
    
    public boolean addTransactionToMonth(double amount, int month) {
        if (amount == 0) {
            return false;
        } else {
            initDao.save(new Transaction(amount, month));
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
        return initDao.findAllOfMonth(month);
    }
    
    public List<Transaction> getTransactions() {
        return initDao.findAll();
    }
}
