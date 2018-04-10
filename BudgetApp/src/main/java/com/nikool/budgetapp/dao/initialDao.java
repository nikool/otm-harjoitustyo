
package com.nikool.budgetapp.dao;

import com.nikool.budgetapp.domain.Transaction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class initialDao implements Dao<Transaction, Integer>{
    
    private List<Transaction> testDatabase = new ArrayList<>();
    

    @Override
    public Transaction findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Transaction> findAll() {
        return testDatabase;
    }

    @Override
    public void save(Transaction transaction) {
        testDatabase.add(transaction);
    }

    @Override
    public void update(Transaction object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
