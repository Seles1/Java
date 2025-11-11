package Repository;

import java.util.Map;
import java.util.HashMap;

import Exception.RepositoryException;
import Domain.Identifiable;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    protected Map<ID, T> elements = new HashMap<>();

    @Override
    public void add(T entity) throws RepositoryException {
        ID id = entity.getId();
        if (id == null) {
            throw new RepositoryException("Id null");
        }
        if (elements.containsKey(id)) {
            throw new RepositoryException("Id already exists");
        }
        elements.put(id, entity);
    }

    @Override
    public void modify(T entity) throws RepositoryException {
        ID id = entity.getId();
        if (id == null) {
            throw new RepositoryException("Id null");
        }
        if (!elements.containsKey(id)) {
            throw new RepositoryException("Id does not exist");
        }
        elements.put(id, entity);
    }

    @Override
    public void delete(ID id) throws RepositoryException {
        if (id == null) {
            throw new RepositoryException("Id null");
        }
        elements.remove(id);
    }

    @Override
    public T findById(ID id) {
        return elements.get(id);
    }

    public Iterable<T> getAll() {
        return elements.values();
    }
}
