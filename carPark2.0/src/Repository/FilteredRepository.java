package Repository;

import Domain.Identifiable;
import Filter.AbstractFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class FilteredRepository<ID, T extends Identifiable<ID>> {
    private final IRepository<ID, T> repository;
    private final AbstractFilter<T> filter;

    public FilteredRepository(IRepository<ID, T> repository, AbstractFilter<T> filter) {
        this.repository = repository;
        this.filter = filter;
    }

    public Iterable<T> getAll() {
            List<T> filteredList = new ArrayList<>();
            for (T entity : repository.getAll()) {
                if (filter.accept(entity)) {
                    filteredList.add(entity);
                }
            }
            return filteredList;
    }
}
