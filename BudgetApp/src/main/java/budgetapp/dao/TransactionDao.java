
package budgetapp.dao;

import budgetapp.domain.Transaction;
import java.io.File;
import java.io.FileWriter;
import java.time.Month;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class TransactionDao implements Dao {
    public List<Transaction> transactions;
    private String file;
    
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
    
    private int generateId() {
        return transactions.size() + 1;
    }
    
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

    @Override
    public List<Transaction> findAll() {
        return transactions;
    }

    @Override
    public void update(int id, double amount, int month) throws Exception {
        for (Transaction t : transactions) {
            if (t.getId() == id) {
                t.setAmount(amount);
                t.setMonth(Month.of(month));
            }
        }
        save();
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == id) {
                transactions.remove(i);
            }
        }
        save();
    }

    @Override
    public Transaction create(Transaction transaction) throws Exception {
        transaction.setId(generateId());
        transactions.add(transaction);
        save();
        return transaction;
    }

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
    
    @Override
    public Transaction findOne(int id) {
        for (Transaction t : transactions) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }
    
    @Override
    public void deleteAll() {
        transactions.removeAll(transactions);
    }
}
