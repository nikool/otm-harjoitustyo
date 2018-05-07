
package budgetapp.ui;

import budgetapp.dao.TransactionDao;
import budgetapp.domain.BudgetAppService;
import budgetapp.domain.Statistics;
import budgetapp.domain.Transaction;
import java.io.FileInputStream;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BudgetApp extends Application {
        private BudgetAppService budgetappService;
        private Statistics statistics;

        private Scene welcomeScene;
        private Scene statisticsScene;
        private Scene transactionScene;
        
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    /**
     * A helper method for drawing the graphs and printing the transactions
     * @param month the month the user wants information of, 13 for the whole years data
     * @return returns a GridPane node containing the statistics and graphs
     */
    private Node monthStatistics(int month) {
        List<Transaction> transactions = new ArrayList<>();
        transactions = budgetappService.getTransactionOfMonth(month);
        
        GridPane monthStatPane = new GridPane();
        GridPane yearStatPane = new GridPane();
        
        // Whole years data
        
        if (month == 13) {
            
            // A list of monthly totals
            
            VBox yearVerticalList = new VBox();
            yearVerticalList.setPadding(new Insets(10));
            yearVerticalList.setSpacing(5);
            
            yearVerticalList.getChildren().add(new Text("List of all monthly end totals:"));
            
            ObservableList dataOfMonthlyTotals = FXCollections.observableArrayList();
            ListView listOfMonthlyTotals = new ListView(dataOfMonthlyTotals);
            
            for (int i = 1; i < 13; i++) {
                dataOfMonthlyTotals.add(new Text(Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH) 
                        + ": " + statistics.endTotal(budgetappService.getTransactionOfMonth(i))));
            }
            
            yearVerticalList.getChildren().add(listOfMonthlyTotals);
            
            // A button to remove all transactions
            
            Button removeAll = new Button("Remove all");
            removeAll.setOnAction((ActionEvent e) -> {
                budgetappService.removeAllTransactions();
            });
            
            yearVerticalList.getChildren().add(removeAll);
            
            yearStatPane.add(yearVerticalList, 0, 0);
            
            // A line graph of the account balance for the whole year
            
            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Months");
            yAxis.setLabel("Amount");
            
            LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
            chart.setTitle("Transactions of the whole year");
            
            XYChart.Series series1 = new XYChart.Series<>();
            series1.setName("Expenses");
            
            XYChart.Series series2 = new XYChart.Series<>();
            series2.setName("Incomes");
            
            for (int i = 1; i < 13; i++) {
                double expensesOfMonth = statistics.endTotal(budgetappService.getAllExpensesOfMonth(i)) * - 1;
                double incomesOfMonth = statistics.endTotal(budgetappService.getAllIncomesOfMonth(i));
                series1.getData().add(new XYChart.Data(i, expensesOfMonth));
                series2.getData().add(new XYChart.Data(i, incomesOfMonth));
            }
            
            chart.getData().add(series1);
            chart.getData().add(series2);
            
            yearStatPane.add(chart, 1, 0);
            
            return yearStatPane;
        }
        
        // A list of the months transactions
        
        VBox verticalList = new VBox();
        verticalList.setPadding(new Insets(10));
        verticalList.setSpacing(5);
        
        Text leftTitle = new Text("List of all transactions:");
        verticalList.getChildren().add(leftTitle);
        
        ObservableList listOfTransactionsInMonth = FXCollections.observableArrayList();
        ListView listOfTransactions = new ListView(listOfTransactionsInMonth);
        
        for (int i = 0; i < transactions.size(); i++) {
            Text transaction = new Text(transactions.get(i).toString());
            listOfTransactionsInMonth.add(transaction);
        }
        
        verticalList.getChildren().add(listOfTransactions);
        
        // A button to remove all transactions of a single month
        
        Button removeAll = new Button("Remove all");
        removeAll.setOnAction((ActionEvent e) -> {
                List<Transaction> listToRemove = new ArrayList<>();
                
                for (int i = 0; i < budgetappService.getTransactionOfMonth(month).size(); i++) {
                    listToRemove.add(budgetappService.getTransactionOfMonth(month).get(i));
                }
                
                listToRemove.stream().forEach(t -> budgetappService.removeTransaction(t));
            });
        verticalList.getChildren().add(removeAll);
        
        monthStatPane.add(verticalList, 0, 0);
        
        // A line graph of the months transactions
        
        double endTotal = 0;
        
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Transactions");
        yAxis.setLabel("Amount");
        
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Transactions of " + Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Account balance");
        
        series.getData().add(new XYChart.Data(0, 0));
        
        for (int i = 1; i <= transactions.size(); i++) {
            endTotal += transactions.get(i - 1).getAmount();
            series.getData().add(new XYChart.Data(i, endTotal));
        }
        
        chart.getData().add(series);
        monthStatPane.add(chart, 1, 0);
        
        return monthStatPane;
    }

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        
        String transactionFile = properties.getProperty("transactionFile");
        
        TransactionDao transactionDao = new TransactionDao(transactionFile);
        budgetappService = new BudgetAppService(transactionDao);
        statistics = new Statistics();
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        // Welcome scene
        
        GridPane welcomePane = new GridPane();
        welcomePane.setVgap(10);
        welcomePane.setHgap(10);
        welcomePane.setPadding(new Insets(10, 10, 10, 10));
        
        VBox welcomeVBox = new VBox();
        welcomeVBox.setPadding(new Insets(10));
        welcomeVBox.setSpacing(10);
        
        Text title = new Text("BudgetApp");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        welcomePane.add(title, 0, 0);
        
        Text welcomeText = new Text("Welcome to the BudgetApp! \n \n"
                + "With the BudgetApp you can monitor your monthly "
                + "expenses and incomes.");
        welcomeText.setFont(Font.font("Arial"));
        welcomeVBox.getChildren().add(welcomeText);
        
        Button enterAppButton = new Button("Press to enter");
        enterAppButton.setOnAction(a -> {
            primaryStage.setScene(statisticsScene);
        });
        welcomeVBox.getChildren().add(enterAppButton);
        
        welcomePane.add(welcomeVBox, 0, 1);
        
        welcomeScene = new Scene(welcomePane, 1000, 600);
        
        // Statistics scene
        
        GridPane statisticsPane = new GridPane();
        statisticsPane.setVgap(10);
        statisticsPane.setHgap(10);
        statisticsPane.setPadding(new Insets(10, 10, 10, 10));
        
        VBox selectMonth = new VBox();
        selectMonth.setPadding(new Insets(10));
        selectMonth.setSpacing(15);
        
        Text topTitle = new Text("BudgetApp | statistics");
        topTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        statisticsPane.add(topTitle, 0, 0);
        
        Text leftTitle = new Text("Select month:");
        leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        
        selectMonth.getChildren().add(leftTitle);
        
        final ToggleGroup group = new ToggleGroup();
        
        RadioButton wholeYear = new RadioButton();
        wholeYear.setText("Whole year");
        wholeYear.setUserData(13);
        wholeYear.setToggleGroup(group);
        
        selectMonth.getChildren().add(wholeYear);
        
        for (int i = 1; i < 13; i++) {
            RadioButton rb = new RadioButton();
            rb.setText(Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            rb.setToggleGroup(group);
            rb.setUserData(i);
            selectMonth.getChildren().add(rb);
        }
        
        group.selectedToggleProperty().addListener(
            (ObservableValue<? extends Toggle> ov, Toggle old_toggle,
            Toggle new_toggle) -> {
                if (group.getSelectedToggle() != null) {
                    if (statisticsPane.getChildren().size() > 3) {
                        statisticsPane.getChildren().remove(3);
                    }
                    if (statisticsPane.getChildren().size() > 4) {
                        statisticsPane.getChildren().remove(4);
                    }
                    statisticsPane.add(monthStatistics((int) group.getSelectedToggle().getUserData()), 1, 1);
                }
        });
        
        statisticsPane.add(selectMonth, 0, 1);
        
        statisticsScene = new Scene(statisticsPane, 1000, 700);
        
        // Add new transaction
        
        VBox bottomVert = new VBox();
        bottomVert.setPadding(new Insets(10));
        bottomVert.setSpacing(10);
        
        Text bottomTitle = new Text("Add a new transaction:");
        bottomTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        bottomVert.getChildren().add(bottomTitle);
        
        Text bottomHelper1 = new Text("Remember to add a minus sign infront of the amount to add an expense.");
        Text bottomHelper2 = new Text("To add the transaction to the current month, leave the month-field empty.\n To add an recurring transaction enter the starting and ending months with a space in beetween.");
        bottomVert.getChildren().add(bottomHelper1);
        
        HBox transactionAdd = new HBox();
        transactionAdd.setSpacing(10);
        
        TextField amount = new TextField();
        amount.setPromptText("Enter the amount");
        transactionAdd.getChildren().add(amount);
        
        TextField month = new TextField();
        month.setPromptText("Enter the month");
        transactionAdd.getChildren().add(month);
        
        Label labelPrompt = new Label();
        
        Button submit = new Button("Submit");
        transactionAdd.getChildren().add(submit);
        
        submit.setOnAction((ActionEvent e) -> {
            labelPrompt.setText(null);
            
            if (amount.getText().trim().isEmpty() || amount.getText() == null || amount.getText().equals("")) {
                labelPrompt.setText("You have not entered an amount.");
            } else if (!budgetappService.isDouble(amount.getText())) {
                labelPrompt.setText("Please enter a proper value. Remember to use a dot as a separator for decimals.");
            } else if (amount.getText().trim().equals("0")) {
                labelPrompt.setText("Amount can't be zero.");
            } else {
                double amountDouble = Double.parseDouble(amount.getText());
                if (month.getText().equals("")) {
                    budgetappService.addTransaction(amountDouble);
                    amount.clear();
                    labelPrompt.setText("Transaction added!");
                }
                String input = month.getText();
                String[] bits = input.split(" ");
                
                if (bits.length == 1) {
                    if (!budgetappService.isMonth(bits[0])) {
                        labelPrompt.setText("The month value has to be beetween 1 and 12!");
                    } else {
                        budgetappService.addTransactionToMonth(amountDouble, Integer.parseInt(bits[0]));
                        labelPrompt.setText("Transaction added!");
                        amount.clear();
                        month.clear();
                    }
                } else if (bits.length == 2) {
                    if (!budgetappService.isMonth(bits[0]) || !budgetappService.isMonth(bits[1])) {
                        labelPrompt.setText("The month value has to be beetween 1 and 12! Remember to add a space beetween the starting and ending months.");
                    } else {
                        int startingMonth = Integer.parseInt(bits[0]);
                        int endingMonth = Integer.parseInt(bits[1]);
                        
                        if (startingMonth > endingMonth) {
                            labelPrompt.setText("The starting month has to be earlier than the ending month.");
                        } else {
                            budgetappService.addRecurringTransaction(amountDouble, startingMonth, endingMonth);
                            labelPrompt.setText("Transaction added!");
                            amount.clear();
                            month.clear();
                        }
                    }
                }
            }
        });
        
        Button clear = new Button("Clear");
        transactionAdd.getChildren().add(clear);
        
        clear.setOnAction((ActionEvent e) -> {
            amount.clear();
            month.clear();
            labelPrompt.setText(null);
        });
        
        bottomVert.getChildren().add(transactionAdd);
        bottomVert.getChildren().add(bottomHelper2);
        bottomVert.getChildren().add(labelPrompt);
        
        statisticsPane.add(bottomVert, 0, 2, 2, 2);
        
        // Setup primary stage
        
        primaryStage.setTitle("BudgetApp");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("closing");
        });
    }
    
    
}
