import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;


class TransactionService {
    private TransactionRepository<Transaction> repo;


    public TransactionService(TransactionRepository<Transaction> repo) {
        this.repo = repo;
    }


    public void loadFromFile(String filename) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line;
        while ((line = br.readLine()) != null) {
            String[] p = line.split(",");
            String type = p[0];
            LocalDate date = LocalDate.parse(p[1]);
            boolean completed = Boolean.parseBoolean(p[2]);
            double amount = Double.parseDouble(p[3]);


            if (type.equals("INCOME")) {
                repo.add(new Income(date, completed, amount, p[4]));
            } else if (type.equals("EXPENSE")) {
                repo.add(new Expense(date, completed, amount, p[4]));
            }
        }
        br.close();
    }


    public List<Transaction> sortedByDateDesc() {
        List<Transaction> list = new ArrayList<>(repo.getAll());
        list.sort((a, b) -> b.getDate().compareTo(a.getDate()));
        return list;
    }


    public List<Transaction> filterExpenses() {
        List<Transaction> result = repo.getAll();
        return result.stream()
                .filter(e->e.getType().equals("EXPENSE")).collect(Collectors.toList());
    }


    public void generateMonthlyReport(String filename) throws Exception {
        Map<String, Double> sums = new HashMap<>();
        for (Transaction t : repo.getAll()) {
            if (t instanceof Expense e) {
                sums.put(e.getCategory(), sums.getOrDefault(e.getCategory(), 0.0) + e.getAmount());
            }
        }
        PrintWriter pw = new PrintWriter(new FileWriter(filename));
        for (var entry : sums.entrySet()) {
            pw.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}