package domain;


import budgetapp.domain.BudgetAppService;
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
        budgetService.addTransactionToMonth(1, 1);
        budgetService.addTransactionToMonth(-1, 1);
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
        budgetService.removeAllTransactions();
        budgetService.addTransaction(1);
        assertEquals("1.0", budgetService.getTransactionOfMonth(localDate.getMonthValue()).get(0).toString());
    }
    
    @Test
    public void addRecurringAddsToCorrectMonths() {
        budgetService.removeAllTransactions();
        budgetService.addRecurringTransaction(1, 1, 12);
        for (int i = 1; i < 13; i++) {
            assertEquals("1.0", budgetService.getTransactionOfMonth(i).get(0).toString());
        }
    }
    
    @Test
    public void addTransactionReturnsFalseForZero() {
        assertEquals(false,budgetService.addTransaction(0));
    }
    
    @Test
    public void removeAllIsWorking() {
        budgetService.removeAllTransactions();
        assertEquals(0, budgetService.getTransactions().size());
    }
    
    @Test
    public void getTransactionsReturnsCorrectly() {
        budgetService.removeAllTransactions();
        budgetService.addTransactionToMonth(1, 12);
        assertEquals("1 | 1.0, " + Month.DECEMBER + "\n", budgetService.printAllTransactions(budgetService.getTransactions()));
    }
    
    @Test
    public void recurringCantAddZero() {
        assertEquals(false, budgetService.addRecurringTransaction(0, 1, 12));
    }
    
    @Test
    public void toCurrentMonthCantAddZero() {
        assertEquals(false, budgetService.addTransaction(0));
    }
    
    @Test
    public void toSpecificMonthCantAddZero() {
        assertEquals(false, budgetService.addTransactionToMonth(0, 1));
    }
    
    @Test
    public void removingOneWorks() {
        budgetService.addTransactionToMonth(1, 12);
        budgetService.removeTransaction(budgetService.getTransactionOfMonth(12).get(0));
        assertEquals(0, budgetService.getTransactionOfMonth(12).size());
    }
    
    @Test
    public void getAllExpensesOfMonthFindsExpenses() {
        assertEquals(1, budgetService.getAllExpensesOfMonth(1).size());
    }
    
    @Test
    public void getAllIncomesOfMonthFindsIncomes() {
        assertEquals(1, budgetService.getAllIncomesOfMonth(1).size());
    }
}
