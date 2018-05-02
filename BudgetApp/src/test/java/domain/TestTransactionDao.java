package domain;

import budgetapp.dao.Dao;
import budgetapp.domain.Transaction;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class TestTransactionDao implements Dao {
    List<Transaction> transactions;
    
    public TestTransactionDao() {
        transactions = new ArrayList<>();
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
    }

    @Override
    public void delete(int id) throws Exception {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == id) {
                transactions.remove(i);
            }
        }
    }

    @Override
    public Transaction create(Transaction transaction) throws Exception {
        transaction.setId(transactions.size() + 1);
        transactions.add(transaction);
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
