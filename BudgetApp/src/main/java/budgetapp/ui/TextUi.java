
package budgetapp.ui;

import budgetapp.dao.InitialDao;
import budgetapp.domain.BudgetAppService;
import budgetapp.domain.Statistics;
import budgetapp.domain.Transaction;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TextUi {
    private Scanner scanner;
    private InitialDao initDao = new InitialDao();
    private BudgetAppService budgetService;  
    private List<String> commands = new ArrayList<>();
    private boolean exit = false;
    private Statistics statistics = new Statistics();
    
    public TextUi(Scanner scanner, BudgetAppService service) { 
        this.scanner = scanner;
        this.budgetService = service;
        commands.add("a | add a new expense or an income");
        commands.add("l | list all transactions");
        commands.add("m | list transactions of a specific month");
        commands.add("s | show statistics");
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
            } else if (input.equals("s")) {
                statistics();
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
        boolean recurringAdded = false;
        
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
            while (true) {
                System.out.println("");
                System.out.println("If you want to add the transaction to a specific month, "
                        + "input the numerical value of the month (a value beetween 1 and 12) and press enter. "
                        + "Leave empty to add to the current month. "
                        + "To add a monthly concurring transaction, press x.");    
                String monthValue = scanner.nextLine();
                if (monthValue.equals("")) {
                    break;
                }
                if (monthValue.equals("x")) {
                    String input;
                    String[] bits;
                    while (true) {
                        System.out.println("");
                        System.out.println("From which month to which do you want the transaction to occur? "
                                + "Input the numerical values of strating and ending months with a space in beetween.");
                        input = scanner.nextLine();
                        bits = input.split(" ");
                        if (bits.length != 2) {
                            System.out.println("");
                            System.out.println("Please input only the starting and ending months!");
                        }
                        if (!budgetService.isMonth(bits[0]) || !budgetService.isMonth(bits[1])) {
                            System.out.println("");
                            System.out.println("Please input a proper value for the month!");
                        } else {
                            break;
                        }
                    }
                    int startingMonth = Integer.parseInt(bits[0]);
                    int endingMonth = Integer.parseInt(bits[1]);
                    budgetService.addRecurringTransaction(amount, startingMonth, endingMonth);
                    recurringAdded = true;
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
                if (recurringAdded == false) {
                    if (!budgetService.addTransaction(amount)) {
                        System.out.println("Please input a transaction value other than zero.");
                    } else {
                        System.out.println("New transaction added!");
                        break;
                    }
                } else {
                    recurringAdded = false;
                    break;
                }    
            } else {
                if (recurringAdded == false) {
                    if (!budgetService.addTransactionToMonth(amount, month)) {
                        System.out.println("Please input a transaction value other than zero.");
                    } else {
                        System.out.println("New transaction added!");               
                        break;
                    }  
                } else {
                    recurringAdded = false;
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
    
    private void statistics() {
        System.out.println("");
        System.out.println("Statistics");
        System.out.println("");
        System.out.println("Monthly end totals and daily averages:");
        System.out.println("");
        for (int i = 1; i < 13; i++) {
            double amount = statistics.endTotal(budgetService.getTransactionOfMonth(i));
            double average = statistics.dailyAverage(budgetService.getTransactionOfMonth(i), i);
            System.out.println(budgetService.getMonth(i).toString() + ": " + amount + ", daily average: " + average);
        }
    }
    
    
}
