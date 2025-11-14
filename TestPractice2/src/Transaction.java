import java.io.*;
import java.time.LocalDate;
import java.util.*;


abstract class Transaction {
    private LocalDate date;
    private boolean completed;
    private double amount;


    public Transaction(LocalDate date, boolean completed, double amount) {
        this.date = date;
        this.completed = completed;
        this.amount = amount;
    }


    public LocalDate getDate() { return date; }
    public boolean isCompleted() { return completed; }
    public double getAmount() { return amount; }


    public abstract String getDetails();
    public abstract String getType();
}