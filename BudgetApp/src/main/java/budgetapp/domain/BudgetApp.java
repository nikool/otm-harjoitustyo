
package budgetapp.domain;

import budgetapp.ui.TextUi;
import budgetapp.domain.BudgetAppService;
import java.util.Scanner;

public class BudgetApp {
    
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        BudgetAppService service = new BudgetAppService();
        TextUi ui = new TextUi(inputScanner, service);
    }
    
}
