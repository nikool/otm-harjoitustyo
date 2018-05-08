
package budgetapp.dao;

import budgetapp.domain.Transaction;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A data access object for transactions, implements the dao interface
 * @author nikoo
 */
public class TransactionDao implements Dao {
    public List<Transaction> transactions;
    private String file;
    
    /**
     * Constructs a new TransactionDao with a specified save file.
     * Checks if a file already exists, if not, creates a new one.
     * @param file a file where the apps data is stored
     * @throws Exception if the file is not found, creates a new one
     */
    public TransactionDao(String file) throws Exception {
        transactions = new ArrayList<>();
        this.file = file;
        try {
            Scanner reader = new Scanner(new File(file));
            
            System.out.println("Checking for an existing transactions.txt -file.");
            
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                int id = Integer.parseInt(parts[0]);
                double amount = Double.parseDouble(parts[1]);
                int month = Integer.parseInt(parts[2]);
                Transaction transaction = new Transaction(id, amount, month);
                transactions.add(transaction);
            }
            
            System.out.println("File found.");
        } catch (Exception e) {
            System.out.println("File not found, creating a new one.");
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
    }
    
    /**
     * Generates a new id for a transaction from the list size
     * @return a int value used for the id of the transaction
     */
    private int generateId() {
        return transactions.size() + 1;
    }
    
    /**
     * Saves any changes to the file
     */
    private void save() {
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (Transaction transaction : transactions) {
                writer.write(transaction.getId() + ";" 
                        + transaction.getAmount() + ";" 
                        + (transaction.getMonth().ordinal() + 1) + "\n");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    /**
     * Creates a list of all the transactions
     * @return a list of the transactions
     */
    @Override
    public List<Transaction> findAll() {
        return transactions;
    }
    
    /**
     * Deletes a transaction
     * @param id the id value of the transaction to be deleted
     */
    @Override
    public void delete(int id) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == id) {
                transactions.remove(i);
            }
        }
        save();
    }
    
    /**
     * Saves a new transaction to the file with an id
     * @param transaction a transaction with no id
     * @return the new created transaction with an id
     */
    @Override
    public Transaction create(Transaction transaction) {
        transaction.setId(generateId());
        transactions.add(transaction);
        save();
        return transaction;
    }
    
    /**
     * Finds all transactions of a single month
     * @param month the month in question
     * @return a list of the months transactions
     */
    @Override
    public List<Transaction> findAllOfMonth(int month) {
        List<Transaction> transactionsOfMonth = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getMonth().ordinal() + 1 == month) {
                transactionsOfMonth.add(t);
            }
        }
        return transactionsOfMonth;
    }
    
    /**
     * Finds one transaction based on its id
     * @param id the id of the transaction to be searched
     * @return the transaction if found, null if not
     */
    @Override 
    public Transaction findOne(int id) {
        for (Transaction t : transactions) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
    
    /**
     * Deletes all transactions
     */
    @Override
    public void deleteAll() {
        transactions.removeAll(transactions);
    }
}
