package Repository;

import Domain.Identifiable;
import Exceptions.RepositoryException;

public abstract class FileRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID, T> {
    protected String fileName;

    public abstract void readFromFile() throws RepositoryException;

    public abstract void writeToFile() throws RepositoryException;

    public FileRepository(String fileName) throws RepositoryException{
        this.fileName = fileName;
        readFromFile();
    }

    @Override
    public void add(T entity) throws RepositoryException {
        super.add(entity);
        writeToFile();
    }

    @Override
    public void delete(ID id) throws RepositoryException {
        super.delete(id);
        writeToFile();
    }

    @Override
    public void modify(T entity) throws RepositoryException {
        super.modify(entity);
        writeToFile();
    }
}
