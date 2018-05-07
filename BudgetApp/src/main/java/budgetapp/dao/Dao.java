
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
     * @return returns all transactions
     */
    List<Transaction> findAll();
    
    /**
     * Updates a specific transactions amount, month or both
     * @param id the id number of the transaction to be updated
     * @param amount the new amount for the transaction
     * @param month the new month for the transaction
     * @throws Exception 
     */
    void update(int id, double amount, int month) throws Exception;
    
    /**
     * Deletes the transaction with the specified id
     * @param id the id of the transaction to be deleted
     */
    void delete(int id);
    
    /**
     * Creates a new transaction with an id and returns it
     * @param transaction the original transaction
     * @return a new transaction with an id
     * @throws Exception 
     */
    Transaction create(Transaction transaction) throws Exception;
    
    /**
     * Return a list of the specified months transactions
     * @param month the month of which transactions to get
     * @return returns a list of all the months transactions
     */
    List<Transaction> findAllOfMonth(int month);
    
    /**
     * Returns the transaction with the specific id
     * @param id the id of the transaction to be returned
     * @return the found transaction
     */
    Transaction findOne(int id);
    
    /**
     * Deletes all transactions
     */
    void deleteAll();
}
