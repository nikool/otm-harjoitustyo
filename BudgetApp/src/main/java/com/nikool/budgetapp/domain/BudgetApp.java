
package com.nikool.budgetapp.domain;

import com.nikool.budgetapp.ui.TextUi;
import java.util.Scanner;

public class BudgetApp {
    
    public static void main(String[] args) {
        Scanner inputScanner = new Scanner(System.in);
        TextUi ui = new TextUi(inputScanner);
    }
    
}
