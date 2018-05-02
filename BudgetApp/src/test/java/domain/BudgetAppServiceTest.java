package domain;


import budgetapp.domain.BudgetAppService;
import budgetapp.domain.Transaction;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class BudgetAppServiceTest {
    BudgetAppService budgetService;
    Date date = new Date();
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    TestTransactionDao transactionDao;
    
    public BudgetAppServiceTest() { 
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        transactionDao = new TestTransactionDao();
        budgetService = new BudgetAppService(transactionDao);
        budgetService.addTransaction(1);
        budgetService.addTransactionToMonth(1, 12);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    @Test
    public void detectorsDenyWrongValues() {
        assertEquals(false, budgetService.isDouble("double"));
        assertEquals(false, budgetService.isDouble("10,1"));
        
        assertEquals(false, budgetService.isMonth("january"));
        assertEquals(false, budgetService.isMonth("0"));
        assertEquals(false, budgetService.isMonth("13"));
        assertEquals(false, budgetService.isMonth("1,1"));
        assertEquals(false, budgetService.isMonth("1.1"));
    }
    
    @Test
    public void detectorsAllowRightValues() {
        assertEquals(true, budgetService.isDouble("1"));
        assertEquals(true, budgetService.isDouble("10.1"));
        assertEquals(true, budgetService.isDouble("-1"));
        assertEquals(true, budgetService.isDouble("-1.5"));
        
        assertEquals(true, budgetService.isMonth("1"));
        assertEquals(true, budgetService.isMonth("12"));
    }
    
    @Test
    public void addTransactionAddsToCurrentMonth() {
        assertEquals("1 | 1.0, " + localDate.getMonth() + "\n", budgetService.printAllTransactions(budgetService.getTransactionOfMonth(localDate.getMonthValue())));
    }
    
    @Test
    public void addTransactionReturnsFalseForZero() {
        assertEquals(false,budgetService.addTransaction(0));
    }
    
    @Test
    public void removeAllIsWorking() {
        budgetService.removeAllTransactions();
        assertEquals("", budgetService.printAllTransactions(budgetService.getTransactions()));
    }
    @Test
    public void getTransactionsReturnsCorrectly() {
        assertEquals("1 | 1.0, " + localDate.getMonth() + "\n" + "2 | 1.0, " + Month.DECEMBER + "\n", budgetService.printAllTransactions(budgetService.getTransactions()));
    }
    
    @Test
    public void transactionsAreFoundIfExisting() {
        assertEquals(true, budgetService.transactionExists(1));
    }
    
    @Test
    public void transactionsAreNotFoundIfNotExisting() {
        assertEquals(false, budgetService.transactionExists(99));
    }
    
    @Test
    public void recurringCantAddZero() {
        assertEquals(false, budgetService.addRecurringTransaction(0, 1, 12));
    }
}
