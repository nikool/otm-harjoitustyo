
package budgetapp.domain;

import budgetapp.dao.InitialDao;
import java.time.Month;
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
    
    public boolean addRecurringTransaction(double amount, int startingMonth, int endingMonth) {
        if (amount == 0) {
            return false;
        } else {
            for (int i = startingMonth; i <= endingMonth; i++) {
                initDao.save(new Transaction(amount, i));
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
        return initDao.findAllOfMonth(month);
    }
    
    public List<Transaction> getTransactions() {
        return initDao.findAll();
    }
    
    public Enum<Month> getMonth(int value) {
        if (value == 1) {
            return Month.JANUARY;
        } else if (value == 2) {
            return Month.FEBRUARY;
        } else if (value == 3) {
            return Month.MARCH;
        } else if (value == 4) {
            return Month.APRIL;
        } else if (value == 5) {
            return Month.MAY;
        } else if (value == 6) {
            return Month.JUNE;
        } else if (value == 7) {
            return Month.JULY;
        } else if (value == 8) {
            return Month.AUGUST;
        } else if (value == 9) {
            return Month.SEPTEMBER;
        } else if (value == 10) {
            return Month.OCTOBER;
        } else if (value == 11) {
            return Month.NOVEMBER;
        } else if (value == 12) {
            return Month.DECEMBER;
        } else {
            return null;
        }
    }
}
