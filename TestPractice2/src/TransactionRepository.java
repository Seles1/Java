import java.util.ArrayList;
import java.util.List;

class TransactionRepository<T extends Transaction> {
    private List<T> data = new ArrayList<>();


    public void add(T t) { data.add(t); }
    public List<T> getAll() { return data; }
}