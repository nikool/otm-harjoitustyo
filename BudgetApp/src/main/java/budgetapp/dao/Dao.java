
package budgetapp.dao;

import budgetapp.domain.Transaction;
import java.util.*;

public interface Dao {

    List<Transaction> findAll();
    
    void update(int id, double amount, int month) throws Exception;

    void delete(int id) throws Exception;
    
    Transaction create(Transaction transaction) throws Exception;
    
    List<Transaction> findAllOfMonth (int month);
}
