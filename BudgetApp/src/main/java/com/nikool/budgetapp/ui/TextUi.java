
package com.nikool.budgetapp.ui;

import com.nikool.budgetapp.dao.initialDao;
import com.nikool.budgetapp.domain.Transaction;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUi {
    private Scanner scanner;
    private initialDao initDao = new initialDao();
    
    public TextUi(Scanner scanner) { 
        this.scanner = scanner;
        startUi();
    }
    
    public void startUi() {
        System.out.println("");
        System.out.println("Welcome to the BudgetApp!");
        System.out.println("");
        System.out.println(commands());
        
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("e")) {
                System.out.println("Thanks for using BudgetApp!");
                break;
            }
            if (userInput.equals("a")) {
                addTransaction();
                System.out.println(commands());
            }
        }
    }
    
    public String commands() {
        List commands = new ArrayList();
        commands.add("a | add a new expense or an income to this month");
        commands.add("e | exit BudgetApp");
        
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the available commands:").append("\n");
        commands.stream().forEach(c -> sb.append(c).append("\n"));
        return sb.toString();
    }
    
    public void addTransaction() {
        System.out.println("");
        System.out.println("Press e to add an expense, or i to add an income.");
        String input = scanner.nextLine();
        
        if (input.equals("e")) {
            
            System.out.println("Write the amount and press enter:");
            double amount = scanner.nextDouble();
            
            if (amount > 0) {
                amount = amount * ( - 1);
            }
            
            initDao.save(new Transaction(amount));
            System.out.println("A new expense added!");
            
        } else if (input.equals("i")) {
            
            System.out.println("Write the amount and press enter:");
            double amount = scanner.nextDouble();
            
            if (amount > 0 && amount != 0) {
                initDao.save(new Transaction(amount));
                System.out.println("A new income added!");
            } else {
                System.out.println("The amount must be over zero!");
            } 
            
        } else {
            
            System.out.println("You must input either e or i!");
            addTransaction();
        }
    }
    
}
