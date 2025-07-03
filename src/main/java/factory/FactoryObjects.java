package factory;

public interface FactoryObjects {
    <T> T getObject(Class<T> type);
}
