import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        TransactionRepository<Transaction> repo = new TransactionRepository<>();
        TransactionService service = new TransactionService(repo);


        service.loadFromFile("src/transactions.txt");


        Scanner sc = new Scanner(System.in);
        System.out.println("1=Add Transaction, 2=Show All, 3=Filter Expenses, 4=Monthly Report");
        int c = sc.nextInt();
        sc.nextLine();


        if (c == 1) {
            System.out.print("Type (INCOME/EXPENSE): ");
            String type = sc.nextLine();
            System.out.print("Date (YYYY-MM-DD): ");
            LocalDate d = LocalDate.parse(sc.nextLine());
            System.out.print("Completed (true/false): ");
            boolean comp = Boolean.parseBoolean(sc.nextLine());
            System.out.print("Amount: ");
            double amt = Double.parseDouble(sc.nextLine());


            if (type.equals("INCOME")) {
                System.out.print("Source: ");
                repo.add(new Income(d, comp, amt, sc.nextLine()));


            } else if (type.equals("EXPENSE")) {
                System.out.print("Category: ");
                repo.add(new Expense(d, comp, amt, sc.nextLine()));
            }
        } else if (c == 2) {
            for (Transaction t : service.sortedByDateDesc()) {
                System.out.println(t.getType() + " | " + t.getDate() + " | " + t.getAmount() + " | " + t.getDetails());
            }
        } else if (c == 3) {
            for(Transaction t:repo.getAll()){
                System.out.println(t.getType());
            }
            System.out.println(service.filterExpenses());
        }
    }
}