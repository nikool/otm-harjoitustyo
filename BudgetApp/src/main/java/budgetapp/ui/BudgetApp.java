
package budgetapp.ui;

import budgetapp.dao.TransactionDao;
import budgetapp.domain.BudgetAppService;
import budgetapp.domain.Statistics;
import budgetapp.domain.Transaction;
import budgetapp.ui.TextUi;
import java.io.FileInputStream;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
    
    private Node monthStatistics(int month) {
        List<Transaction> transactions = new ArrayList<>();
        transactions = budgetappService.getTransactionOfMonth(month);
        
        GridPane monthStatPane = new GridPane();
        GridPane yearStatPane = new GridPane();
        
        // Whole years data
        
        if (month == 13) {
            
            // A list of monthly totals
            
            VBox listOfMonthTotals = new VBox();
            listOfMonthTotals.setPadding(new Insets(10));
            listOfMonthTotals.setSpacing(5);
            
            listOfMonthTotals.getChildren().add(new Text("List of all monthly end totals:"));
            
            for (int i = 1; i < 13; i++) {
                listOfMonthTotals.getChildren().add(new Text(Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH) 
                        + ": " + statistics.endTotal(budgetappService.getTransactionOfMonth(i))));
            }
            
            yearStatPane.add(listOfMonthTotals, 0, 0);
            
            // Pie charts of monthly consumption and income
            
            PieChart pieChartExpenditures = new PieChart();
            PieChart pieChartIncome = new PieChart();
            
            for (int i = 1; i < 13; i++) {
                ObservableList<PieChart.Data> expenditurePieChartData = FXCollections.observableArrayList();
                
                expenditurePieChartData.add(new PieChart.Data(Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH), 
                        statistics.endTotal(budgetappService.getAllExpensesOfMonth(i))));
                
                pieChartExpenditures.getData().addAll(expenditurePieChartData);
            }
            pieChartExpenditures.setTitle("Monthly expenditures");
            yearStatPane.add(pieChartExpenditures, 1, 0);
            
            for (int i = 1; i < 13; i++) {
                ObservableList<PieChart.Data> incomePieChartData = FXCollections.observableArrayList(
                new PieChart.Data(Month.of(i).getDisplayName(TextStyle.FULL, Locale.ENGLISH), 
                        statistics.endTotal(budgetappService.getAllExpensesOfMonth(i))));
                
                pieChartIncome.getData().addAll(incomePieChartData);
            }
            pieChartIncome.setTitle("Monthly incomes");
            yearStatPane.add(pieChartIncome, 2, 0);
            
            return yearStatPane;
        }
        
        // A list of the months transactions
        
        VBox listOfTransactions = new VBox();
        listOfTransactions.setPadding(new Insets(10));
        listOfTransactions.setSpacing(5);
        
        Text leftTitle = new Text("List of all transactions:");
        listOfTransactions.getChildren().add(leftTitle);
        
        for (int i = 0; i < transactions.size(); i++) {
            Text transaction = new Text(transactions.get(i).toString());
            listOfTransactions.getChildren().add(transaction);
        }
        monthStatPane.add(listOfTransactions, 0, 0);
        
        // A line graph of the months transactions
        
        double endTotal = 0;
        
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Transactions");
        yAxis.setLabel("Amount");
        
        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Transaction's of " + Month.of(month).getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Month total");
        
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
        
        BorderPane welcomePane = new BorderPane();
        VBox welcomeVBox = new VBox();
        welcomeVBox.setPadding(new Insets(20));
        welcomeVBox.setSpacing(10);
        
        Text title = new Text("BudgetApp");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcomePane.setTop(title);
        
        Text empty = new Text("");
        welcomeVBox.getChildren().add(empty);
        
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
        
        welcomePane.setCenter(welcomeVBox);
        
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
        
        statisticsScene = new Scene(statisticsPane, 1000, 600);
        
        // Add new transaction
        
        VBox bottomVert = new VBox();
        bottomVert.setPadding(new Insets(10));
        bottomVert.setSpacing(10);
        
        Text bottomTitle = new Text("Add a new transaction:");
        bottomTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        bottomVert.getChildren().add(bottomTitle);
        
        Text bottomHelper = new Text("Remember to add a minus sign infront of the amount to add an expense. \n"
                + "To add an recurring transaction enter the starting and ending months with a space in beetween.");
        bottomVert.getChildren().add(bottomHelper);
        
        HBox transactionAdd = new HBox();
        transactionAdd.setSpacing(10);
        
        TextField amount = new TextField();
        amount.setPromptText("Enter the amount");
        transactionAdd.getChildren().add(amount);
        
        TextField month = new TextField();
        month.setPromptText("Enter the month");
        transactionAdd.getChildren().add(month);
        
        Button submit = new Button("Submit");
        transactionAdd.getChildren().add(submit);
        
        bottomVert.getChildren().add(transactionAdd);
        
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
