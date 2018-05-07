package domain;


import budgetapp.domain.Transaction;
import java.time.Month;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    
    Transaction transactionCurrentMonth;
    Transaction transactionSpecificMonth;
    Transaction transactionWithId;
    
    public TransactionTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        transactionCurrentMonth = new Transaction(10.0);
        transactionSpecificMonth = new Transaction(10.0, 1);
        transactionWithId = new Transaction(1, 10.0, 1);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void constructorAddsCorrectAmount() {
        assertEquals("10.0", transactionCurrentMonth.toString());
    }
    
    @Test
    public void constructorAddsCorrectMonth() {
        assertEquals("10.0", transactionSpecificMonth.toString());
        assertEquals(Month.JANUARY, transactionSpecificMonth.getMonth());
    }
    
    @Test
    public void settersWorks() {
        transactionSpecificMonth.setAmount(5.0);
        transactionSpecificMonth.setMonth(Month.DECEMBER);
        assertEquals("5.0", transactionSpecificMonth.toString());
        assertEquals(Month.DECEMBER, transactionSpecificMonth.getMonth());
    }
    
    @Test
    public void idGetsSet() {
        assertEquals(1, transactionWithId.getId());
    }
}
