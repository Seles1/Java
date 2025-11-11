package Repository;

import Domain.Identifiable;
import Exception.RepositoryException;


public interface IRepository<ID, T extends Identifiable<ID>> {
    public void add(T elem) throws RepositoryException;

    public void modify(T elem) throws RepositoryException;

    public void delete(ID id) throws RepositoryException;

    public T findById(ID id) throws RepositoryException;

    public Iterable<T> getAll();
}
