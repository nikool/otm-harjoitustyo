
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
    
//    public Enum<Month> getMonth(int value) {
//        switch (value) {
//            case 1:
//                return Month.JANUARY;
//            case 2:
//                return Month.FEBRUARY;
//            case 3:
//                return Month.MARCH;
//            case 4:
//                return Month.APRIL;
//            case 5:
//                return Month.MAY;
//            case 6:
//                return Month.JUNE;
//            case 7:
//                return Month.JULY;
//            case 8:
//                return Month.AUGUST;
//            case 9:
//                return Month.SEPTEMBER;
//            case 10:
//                return Month.OCTOBER;
//            case 11:
//                return Month.NOVEMBER;
//            case 12:
//                return Month.DECEMBER;
//            default:
//                return null;
//        }
//    }
}
