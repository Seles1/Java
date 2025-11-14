import java.time.LocalDate;
class Income extends Transaction {
    private String source;


    public Income(LocalDate date, boolean completed, double amount, String source) {
        super(date, completed, amount);
        this.source = source;
    }


    public String getType() { return "INCOME"; }
    public String getDetails() { return "Source: " + source; }
}