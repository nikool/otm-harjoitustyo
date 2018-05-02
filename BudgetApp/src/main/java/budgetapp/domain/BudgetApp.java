
package budgetapp.domain;

import budgetapp.dao.TransactionDao;
import budgetapp.ui.TextUi;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;

public class BudgetApp {
    
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        
        String transactionFile = properties.getProperty("transactionFile");
        
        TransactionDao transactionDao = new TransactionDao(transactionFile);
        
        Scanner inputScanner = new Scanner(System.in);
        BudgetAppService service = new BudgetAppService(transactionDao);
        
        TextUi ui = new TextUi(inputScanner, service);
    }
    
}
