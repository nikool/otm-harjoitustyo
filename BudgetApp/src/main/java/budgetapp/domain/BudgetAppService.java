
package budgetapp.domain;

import budgetapp.dao.Dao;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
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
     * 
     * @return boolean if adding worked
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
    
    /**
     * Adding a transaction to a specific month
     * 
     * @param amount the transaction amount
     * @param month the month user wants the transaction added to
     * 
     * @return boolean on did it work
     */
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
    
    /**
     * Add a recurring transactions to the specified timeframe
     * @param amount
     * @param startingMonth from which month to start
     * @param endingMonth to which month to end
     * @return 
     */
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
    
    /**
     * Check that the users input is a double value
     * @param input
     * @return 
     */
    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Print all the transactions of the parameter list
     * @param list a list of transactions
     * @return 
     */
    public String printAllTransactions(List<Transaction> list) {
        StringBuilder sb = new StringBuilder();
        
        list.stream().forEach(t -> sb.append(t.getId()).append(" | ").append(t.getAmount()).append(", ").append(t.getMonth()).append("\n"));
        
        return sb.toString();
    }
    
    /**
     * Check that the user input is a value for a month
     * @param input
     * @return 
     */
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
    
    /**
     * Returns a list of transactions for a specific month
     * @param month
     * @return 
     */
    public List<Transaction> getTransactionOfMonth(int month) {
        return transactionDao.findAllOfMonth(month);
    }
    
    public List<Transaction> getTransactions() {
        return transactionDao.findAll();
    }
    
    /**
     * Check that the users requested transaction exists
     * @param id the id of the wanted transaction
     * @return 
     */
    public boolean transactionExists(int id) {
        if (transactionDao.findOne(id) != null) {
            return true;
        } else {
            return false;
        }
    }
    
    public Transaction getTransaction(int id) {
        return transactionDao.findOne(id);
    }
    
    /**
     * Remove the specified transaction
     * @param transaction 
     */
    public void removeTransaction(Transaction transaction) {
        try {
            transactionDao.delete(transaction.getId());
        } catch (Exception e) {
            System.out.println("Removeal failed due to: " + e.getMessage());
        }
    }
    
    /**
     * Remove all transactions
     */
    public void removeAllTransactions() {
        transactionDao.deleteAll();
    }
    
    /**
     * Returns a list of all the expense transactions of the month
     * @param month the month of the transactions
     * @return 
     */
    public List<Transaction> getAllExpensesOfMonth(int month) {
        List<Transaction> expenses = new ArrayList<>();
        getTransactionOfMonth(month).stream().forEach(t -> {
            if (t.getAmount() < 0) {
                expenses.add(t);
            }
        });
        
        return expenses;
    }
    
    /**
     * Returns a list of all the income transactions of the month
     * @param month the month of the transactions
     * @return 
     */
    public List<Transaction> getAllIncomesOfMonth(int month) {
        List<Transaction> incomes = new ArrayList<>();
        getTransactionOfMonth(month).stream().forEach(t -> {
            if (t.getAmount() > 0) {
                incomes.add(t);
            }
        });
        
        return incomes;
    }
}
