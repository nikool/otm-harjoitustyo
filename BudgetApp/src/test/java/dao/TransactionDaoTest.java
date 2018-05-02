
package dao;

import budgetapp.dao.Dao;
import budgetapp.dao.TransactionDao;
import budgetapp.domain.Transaction;
import domain.TestTransactionDao;
import java.io.File;
import java.io.FileWriter;
import java.time.Month;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class TransactionDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    File transactionFile;
    Dao dao;
    
    @Before
    public void setUp() throws Exception{
        transactionFile = testFolder.newFile("testfile_transactions.txt");
        
        try (FileWriter file = new FileWriter(transactionFile.getAbsolutePath())) {
            file.write("1;10.0;1\n");
        }
        
        dao = new TransactionDao(transactionFile.getAbsolutePath());
    }
    
    @Test
    public void transactionsAreReadCorrectly() {
        List<Transaction> transactions = dao.findAll();
        assertEquals(1, transactions.size());
        Transaction t = transactions.get(0);
        assertEquals(1, t.getId());
        assertEquals(10.0, t.getAmount(), 0);
        assertEquals(Month.JANUARY, t.getMonth());
    }
    
    @Test
    public void transactionsCanBeDeleted() {
        dao.delete(1);
        assertEquals(0, dao.findAll().size());
    }
    
    @After
    public void tearDown() {
        transactionFile.delete();
    }
}
