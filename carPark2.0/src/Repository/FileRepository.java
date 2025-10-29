package Repository;

import Domain.Identifiable;

public abstract class FileRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID, T> {
    protected String fileName;

    public abstract void readFromFile() throws Exception;

    public abstract void writeToFile() throws Exception;

    public FileRepository(String fileName) throws Exception{
        this.fileName = fileName;
        readFromFile();
    }

    @Override
    public void add(T entity) throws Exception {
        super.add(entity);
        writeToFile();
    }

    @Override
    public void delete(ID id) throws Exception {
        super.delete(id);
        writeToFile();
    }

    @Override
    public void modify(T entity) throws Exception {
        super.modify(entity);
        writeToFile();
    }
}
