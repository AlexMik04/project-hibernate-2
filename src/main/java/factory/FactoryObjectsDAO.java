package factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class FactoryObjectsDAO implements FactoryObjects {
    private static final Logger logger = LoggerFactory.getLogger(FactoryObjectsDAO.class);

    private static FactoryObjectsDAO instance;

    private final Map<Class<?>, Object> daoCache = new HashMap<>();

    private FactoryObjectsDAO() {}

    public static FactoryObjectsDAO getInstance() {
        if (instance == null) {
            instance = new FactoryObjectsDAO();
        }
        return instance;
    }

    @Override
    public <T> T getObject(Class<T> type) {
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            logger.error("Cannot create DAO from interface or abstract class: {}", type.getSimpleName());
            throw new IllegalStateException("Cannot create DAO from interface or abstract class: " + type.getSimpleName());
        }

        return (T) daoCache.computeIfAbsent(type, cls -> {
            try {
                return type.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                logger.error("Failed to create DAO: {}", cls.getSimpleName());
                throw new IllegalStateException("Failed to create DAO: " + cls.getSimpleName(), e);
            }
        });
    }
}

