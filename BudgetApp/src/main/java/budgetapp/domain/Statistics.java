
package budgetapp.domain;

import java.time.YearMonth;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Statistics {
    
    private  List<Transaction> list;
    double result;
    LocalDate localDate;
    Date date = new Date();
    
    public double dailyAverage(List<Transaction> listOfTransactions, int month) {
        list = listOfTransactions;   
        result = endTotal(list);
        localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        YearMonth yearMonthObject = YearMonth.of(localDate.getYear(), month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        
        double average = result / daysInMonth;
        average = round(average);
        
        return average;
    }
    
    public double endTotal(List<Transaction> listOfTransactions) {
        list = listOfTransactions;
        result = 0;
        
        for (int i = 0; i < list.size(); i++) {
            result += list.get(i).getAmount();
        }
        
        return result;
    }
    
    private double round(double value) {
        double amount = value;
        amount = amount * 100;
        amount = Math.round(amount);
        amount = amount / 100;
        return amount;
    }
    
    
}
