import java.time.LocalDate;

class Expense extends Transaction {
    private String category;


    public Expense(LocalDate date, boolean completed, double amount, String category) {
        super(date, completed, amount);
        this.category = category;
    }


    public String getType() { return "EXPENSE"; }
    public String getDetails() { return "Category: " + category; }
    public String getCategory() { return category; }
}