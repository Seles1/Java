package Repository;

import Domain.Identifiable;
import Exceptions.RepositoryException;

import java.util.Iterator;
import java.util.Optional;

public interface IRepository<ID, T extends Identifiable<ID>> {
    public void add(T elem) throws RepositoryException;

    public Optional<T> delete(ID id) throws RepositoryException;

    public void modify(T elem) throws RepositoryException;

    public Optional<T> findById(ID id) throws RepositoryException;

    public Iterable<T> getAll();
}
