package Repository;

import Domain.Identifiable;

import java.util.Iterator;
import java.util.Optional;

public interface IRepository<ID, T extends Identifiable<ID>> {
    public void add(T elem) throws Exception;

    public void delete(ID id) throws Exception;

    public void modify(T elem) throws Exception;

    public T findById(ID id);

    public Iterable<T> getAll();
}
