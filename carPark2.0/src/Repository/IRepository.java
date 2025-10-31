package Repository;

import Domain.Identifiable;
import Exceptions.RepositoryException;

import java.util.Iterator;
import java.util.Optional;

public interface IRepository<ID, T extends Identifiable<ID>> {
    public void add(T elem) throws RepositoryException;

    public void delete(ID id) throws RepositoryException;

    public void modify(T elem) throws RepositoryException;

    public T findById(ID id);

    public Iterable<T> getAll();
}
