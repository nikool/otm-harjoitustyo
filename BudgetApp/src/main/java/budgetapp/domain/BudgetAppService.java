
package budgetapp.domain;

import budgetapp.dao.Dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The class responsible for the logic
 */

public class BudgetAppService {
    private Dao transactionDao;
    private Statistics statistics;
    
    public BudgetAppService(Dao dao) {
        this.transactionDao = dao;
        this.statistics = new Statistics();
    }
    
    /**
     * Adding a new transaction to the current month
     *
     * @param amount the transaction amount
     * @return boolean if adding worked
     */
    public boolean addTransaction(double amount) {
        if (amount == 0) {
            return false;
        } else {
            transactionDao.create(new Transaction(amount));
            return true;
        }
    }
    
    /**
     * Adding a transaction to a specific month
     * 
     * @param amount the transaction amount
     * @param month the month user wants the transaction added to
     * @return boolean on did it work
     */
    public boolean addTransactionToMonth(double amount, int month) {
        if (amount == 0) {
            return false;
        } else {
            transactionDao.create(new Transaction(amount, month));
            return true;
        }
    }
    
    /**
     * Add a recurring transactions to the specified timeframe
     * @param amount the transaction amount
     * @param startingMonth from which month to start
     * @param endingMonth to which month to end
     * @return true if adding worked, false if not
     */
    public boolean addRecurringTransaction(double amount, int startingMonth, int endingMonth) {
        if (amount == 0) {
            return false;
        } else {
            for (int i = startingMonth; i <= endingMonth; i++) {
                transactionDao.create(new Transaction(amount, i));
            }
            return true;
        }    
    }
    
    /**
     * Check that the users input is a double value
     * @param input the user typed input
     * @return true if the input is a double value
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
     * Check that the user input is a value for a month
     * @param input the user input
     * @return true if the input is an integer value and beetween 1 and 12
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
     * @param month the month in question
     * @return the list of transaction of the month
     */
    public List<Transaction> getTransactionOfMonth(int month) {
        return transactionDao.findAllOfMonth(month);
    }
    
    /**
     * Getter for all transactions
     * @return a list of all transactions
     */
    public List<Transaction> getTransactions() {
        return transactionDao.findAll();
    }
    
    /**
     * Print all the transactions of the parameter list
     * @param list a list of transactions
     * @return a string format of the tranasactions
     */
    public String printAllTransactions(List<Transaction> list) {
        StringBuilder sb = new StringBuilder();
        
        list.stream().forEach(t -> sb.append(t.getId()).append(" | ").append(t.getAmount()).append(", ").append(t.getMonth()).append("\n"));
        
        return sb.toString();
    }
    
    /**
     * Remove the specified transaction
     * @param transaction the transaction to be removed
     */
    public void removeTransaction(Transaction transaction) {
        transactionDao.delete(transaction.getId());
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
     * @return the list containing all negative transactions of the month
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
     * @return a list containing all the positive transactions of the month
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
    
    /**
     * Creates a randomized dataset for the app, useful for quick testing
     */
    public void createRandomData() {
        double rent;
        double expense;
        double income;
        
        Random rnd = new Random();
        
        rent = (200 + rnd.nextDouble() * 1000) * -1;
        
        for (int i = 1; i < 13; i++) {
            income = 400 + rnd.nextDouble() * 1000;
            addTransactionToMonth(statistics.round(income), i);
            addTransactionToMonth(statistics.round(rent), i);
            for (int k = 1; k < 5; k++) {
                expense = (20 + rnd.nextDouble() * 300) * -1;
                addTransactionToMonth(statistics.round(expense), i);
                if (rnd.nextDouble() < 0.33) {
                    income = 20 + rnd.nextDouble() * 200;
                    addTransactionToMonth(statistics.round(income), i);
                }
            }
        }
    }
}
