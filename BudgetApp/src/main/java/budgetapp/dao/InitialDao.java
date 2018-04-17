
package budgetapp.dao;

import budgetapp.domain.Transaction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InitialDao implements Dao<Transaction, Integer> {
    
    private List<Transaction> testDatabase = new ArrayList<>();
    

    
    public List<Transaction> findAllOfMonth(Integer month) {
        List<Transaction> transactionsOfMonth = new ArrayList<>();
        for (int i = 0; i < testDatabase.size(); i++) {
            if (testDatabase.get(i).getMonth().ordinal() + 1 == month) {
                transactionsOfMonth.add(testDatabase.get(i));
            }
        }
        return transactionsOfMonth;
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

    @Override
    public Transaction findOne(Integer key) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
