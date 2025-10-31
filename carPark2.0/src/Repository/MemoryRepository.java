package Repository;

import Domain.Identifiable;
import Exceptions.RepositoryException;

import java.util.HashMap;
import java.util.Map;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    protected Map<ID, T> elements = new HashMap<>();

    @Override
    public void add(T entity) throws RepositoryException {
        ID id = entity.getId();
        if (id == null) {
            throw new RepositoryException("ID is null");
        }
        if (elements.containsKey(id)) {
            throw new RepositoryException("Entity already exists, id: " + id);
        }
        elements.put(id, entity);
    }

    @Override
    public void delete(ID id) throws RepositoryException {
        if (elements.remove(id) == null) {
            throw new RepositoryException("ID does not exist: " + id);
        }
    }

    @Override
    public void modify(T entity) throws RepositoryException {
        ID id = entity.getId();
        if (id == null) {
            throw new RepositoryException("ID cannot be null");
        }
        if (!elements.containsKey(id)) {
            throw new RepositoryException("ID does not exist: " + id);
        }
        elements.put(id, entity);
    }

    public T findById(ID id) {
        return elements.get(id);
    }

    public Iterable<T> getAll() {
        return elements.values();
    }
}
