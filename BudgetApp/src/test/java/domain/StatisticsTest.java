
package domain;

import budgetapp.domain.Statistics;
import budgetapp.domain.Transaction;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class StatisticsTest {
    
    private  List<Transaction> list;
    double result;
    LocalDate localDate;
    Date date = new Date();
    Statistics stat;
    
    public StatisticsTest() {
        list = new ArrayList<>();
        list.add(new Transaction(10.0, 1));
        stat = new Statistics();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void dailyAverageWorks() {
        assertEquals(0.32, stat.dailyAverage(list, 1),0);
    }
    
    @Test
    public void endTotalIsCorrect() {
        assertEquals(10.0, stat.endTotal(list),0);
    }
}
