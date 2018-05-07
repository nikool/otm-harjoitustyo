
package budgetapp.ui;

import budgetapp.dao.TransactionDao;
import budgetapp.domain.BudgetAppService;
import budgetapp.ui.TextUi;
import java.io.FileInputStream;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BudgetApp extends Application {
    
        private BudgetAppService budgetappService;

        private Scene welcomeScene;
        private Scene statisticsScene;
        private Scene transactionScene;
        
    public static void main(String[] args) throws Exception {
//        Properties properties = new Properties();
//        properties.load(new FileInputStream("config.properties"));
//        
//        String transactionFile = properties.getProperty("transactionFile");
//        
//        TransactionDao transactionDao = new TransactionDao(transactionFile);
//        
//        Scanner inputScanner = new Scanner(System.in);
//        BudgetAppService service = new BudgetAppService(transactionDao);
//        
//        TextUi ui = new TextUi(inputScanner, service);
        launch(args);
    }
    
    private Node monthStatistics(int month) {
        BorderPane monthStatPane = new BorderPane();
        
        VBox listOfTransactions = new VBox();
        listOfTransactions.setPadding(new Insets(10));
        listOfTransactions.setSpacing(5);
        
        Text leftTitle = new Text("List of all transactions:");
        listOfTransactions.getChildren().add(leftTitle);
        
        for (int i = 0; i < budgetappService.getTransactionOfMonth(month).size(); i++) {
            Text transaction = new Text(budgetappService.getTransactionOfMonth(month).get(i).toString());
            listOfTransactions.getChildren().add(transaction);
        }
        monthStatPane.setLeft(listOfTransactions);
        
        return monthStatPane;
    }

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        
        String transactionFile = properties.getProperty("transactionFile");
        
        TransactionDao transactionDao = new TransactionDao(transactionFile);
        budgetappService = new BudgetAppService(transactionDao);
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
        
        welcomeScene = new Scene(welcomePane, 1280, 720);
        
        // Statistics scene
        
        BorderPane statisticsPane = new BorderPane();
        
        VBox selectMonth = new VBox();
        selectMonth.setPadding(new Insets(10));
        selectMonth.setSpacing(10);
        
        Text topTitle = new Text("BudgetApp | statistics");
        topTitle.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        statisticsPane.setTop(topTitle);
        
        Text leftTitle = new Text("Select month:");
        leftTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        
        selectMonth.getChildren().add(leftTitle);
        
        final ToggleGroup group = new ToggleGroup();
        
        RadioButton wholeYear = new RadioButton();
        wholeYear.setText("Whole year");
        wholeYear.setUserData(13);
        wholeYear.setToggleGroup(group);
        wholeYear.setSelected(true);
        
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
                    statisticsPane.setCenter(monthStatistics((int) group.getSelectedToggle().getUserData()));
                }
        });
        
        statisticsPane.setLeft(selectMonth);
        
        statisticsScene = new Scene(statisticsPane, 1280, 720);
        
        // Setup primary stage
        
        primaryStage.setTitle("BudgetApp");
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("closing");
        });
    }
    
    
}
