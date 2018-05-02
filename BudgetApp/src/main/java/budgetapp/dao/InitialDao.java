
package budgetapp.dao;

import budgetapp.domain.Transaction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InitialDao {
    
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

    public List<Transaction> findAll() {
        return testDatabase;
    }

    public void save(Transaction transaction) {
        testDatabase.add(transaction);
    }
}
