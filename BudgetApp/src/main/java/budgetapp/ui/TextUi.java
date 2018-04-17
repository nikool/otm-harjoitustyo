
package budgetapp.ui;

import budgetapp.dao.InitialDao;
import budgetapp.domain.BudgetAppService;
import budgetapp.domain.Transaction;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextUi {
    private Scanner scanner;
    private InitialDao initDao = new InitialDao();
    private BudgetAppService budgetService;  
    private List<String> commands = new ArrayList<>();
    private boolean exit = false;
    
    public TextUi(Scanner scanner, BudgetAppService service) { 
        this.scanner = scanner;
        this.budgetService = service;
        commands.add("a | add a new expense or an income");
        commands.add("l | list all transactions");
        commands.add("m | list transactions of a specific month");
        commands.add("e | exit BudgetApp");
        startUi();
    }
    
    public void startUi() {
        System.out.println("");
        System.out.println("Welcome to the BudgetApp!");
        while (!exit) {
            System.out.println(commands());
            userInput();
        }    
        System.out.println("");
        System.out.println("Thanks for using the BudgetApp!");
    }
    
    public String commands() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n").append("Here are the available commands:").append("\n");
        commands.stream().forEach(c -> sb.append(c).append("\n"));
        return sb.toString();
    }
    
    public void userInput() {
        while (true) {
            String input = scanner.nextLine();
            
            if (input.equals("a")) {
                addTransaction();
                break;
            } else if (input.equals("l")) {
                listTransactions();
                break;
            } else if (input.equals("e")) {
                exit = true;
                break;
            } else if (input.equals("m")) {
                listTransactionsOfAMonth();
                break;
            } else {
                System.out.println("Please input a valid command!");
                System.out.println(commands());
            }
        } 
    }
    
    public void addTransaction() {
        double amount = 0;
        int month = 0;
        
        System.out.println("");
        System.out.println("Add a new transaction. Remember to add a minus sign infront of the value to add an expense.");
        
        while (true) {
            System.out.println("");
            System.out.println("Write the value and press enter:");
            String value = scanner.nextLine();
            
            if (budgetService.isDouble(value)) {
                amount = Double.parseDouble(value);
            } else {
                System.out.println("Please input a value. Remember to use . as a separator for decimals!");
            }
            
            System.out.println("");
            System.out.println("If you want to add the transaction to a specific month, input the numerical value of the month (a value beetween 1 and 12) and press enter. Leave empty to add to the current month.");    
            while (true) {
                String monthValue = scanner.nextLine();
                if (monthValue.equals("")) {
                    break;
                }
                if (!budgetService.isMonth(monthValue)) {
                    System.out.println("Please input a value beetween 1 and 12.");
                } else {
                    month = Integer.parseInt(monthValue);
                    break;
                }
            }
            if (month == 0) {
                if (!budgetService.addTransaction(amount)) {
                    System.out.println("Please input a transaction value other than zero.");
                } else {
                    System.out.println("New transaction added!");               
                    break;
                }
            } else {
                if (!budgetService.addTransactionToMonth(amount, month)) {
                    System.out.println("Please input a transaction value other than zero.");
                } else {
                    System.out.println("New transaction added!");               
                    break;
                } 
            }    
        }
    }
    
    private void listTransactions() {
        System.out.println("");
        System.out.println("List of all transactions:");
        System.out.println("");
        System.out.println(budgetService.printAllTransactions(budgetService.getTransactions()));
    }
    
    private void listTransactionsOfAMonth() {
        int month = 0;
        
        System.out.println("Which months transactions do you want to list? Enter the months numerical value:");
        while (true) {
            String monthValue = scanner.nextLine();
            if (!budgetService.isMonth(monthValue)) {
                System.out.println("Please input a value beetween 1 and 12.");
            } else {
                month = Integer.parseInt(monthValue);
                break;
            }
        }
        System.out.println("");
        System.out.println("List of transactions:");
        System.out.println("");
        System.out.println(budgetService.printAllTransactions(budgetService.getTransactionOfMonth(month)));
    }
    
    
}
