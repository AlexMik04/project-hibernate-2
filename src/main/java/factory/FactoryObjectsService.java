package factory;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FactoryObjectsService implements FactoryObjects {
    private static final Logger logger = LoggerFactory.getLogger(FactoryObjectsService.class);

    private static FactoryObjectsService instance;

    private final SessionFactory sessionFactory;
    private final Map<Class<?>, Object> serviceCache = new HashMap<>();

    private FactoryObjectsService(SessionFactory sessionFactory) {
        this.sessionFactory = Objects.requireNonNull(sessionFactory, "SessionFactory cannot be null");
    }

    public static FactoryObjectsService getInstance(SessionFactory sessionFactory) {
        if (instance == null) {
            instance = new FactoryObjectsService(sessionFactory);
        }
        return instance;
    }

    @Override
    public <T> T getObject(Class<T> type) {
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers())) {
            logger.error("Cannot create service from interface or abstract class: {}", type.getSimpleName());
            throw new IllegalStateException("Cannot create service from interface or abstract class: " + type.getSimpleName());
        }

        return (T) serviceCache.computeIfAbsent(type, cls -> {
            try {
                Constructor<?> constructor = cls.getConstructor(SessionFactory.class);
                return constructor.newInstance(sessionFactory);
            } catch (Exception e) {
                logger.error("Failed to create service: {}", cls.getSimpleName());
                throw new IllegalStateException("Failed to create service: " + cls.getSimpleName(), e);
            }
        });
    }
}
