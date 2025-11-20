package Filter;
@FunctionalInterface
public interface AbstractFilter<T>{
    boolean accept(T entity);
}
