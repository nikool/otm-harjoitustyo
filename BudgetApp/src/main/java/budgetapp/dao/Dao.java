
package budgetapp.dao;

import budgetapp.domain.Transaction;
import java.util.*;

/**
 * General data access object interface
 * @author nikoo
 */
public interface Dao {
    
    /**
     * returns a list of all transactions
     * @return 
     */
    List<Transaction> findAll();
    
    /**
     * Updates a specific transactions amount, month or both
     * @param id
     * @param amount
     * @param month
     * @throws Exception 
     */
    void update(int id, double amount, int month) throws Exception;
    
    /**
     * Deletes the transaction with the specified id
     * @param id 
     */
    void delete(int id);
    
    /**
     * Creates a new transaction with an id and returns it
     * @param transaction
     * @return
     * @throws Exception 
     */
    Transaction create(Transaction transaction) throws Exception;
    
    /**
     * Return a list of the specified months transactions
     * @param month
     * @return 
     */
    List<Transaction> findAllOfMonth(int month);
    
    /**
     * Returns the transaction with the specific id
     * @param id
     * @return 
     */
    Transaction findOne(int id);
    
    /**
     * Deletes all transactions
     */
    void deleteAll();
}
