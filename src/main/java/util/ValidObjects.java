package util;

import jakarta.persistence.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public final class ValidObjects {
    private static final Logger logger = LoggerFactory.getLogger(ValidObjects.class);

    private ValidObjects() {}

    public static <T> boolean isWholeNumberType(T number) {
        if (number == null) {
            logger.warn("Provided number is null in isWholeNumberType");
            return false;
        }

        Class<?> clazz = number.getClass();
        return clazz == Byte.class
                || clazz == Short.class
                || clazz == Integer.class
                || clazz == Long.class;
    }

    public static <T> void validExistsInDatabaseException(T obj, String message) {
        if (obj == null) {
            String safeMessage = message != null ? message : "Message is null";
            logger.error(safeMessage);
            throw new PersistenceException(safeMessage);
        }
    }

    public static <T> void validNullException(T obj, String message) {
        if (obj == null) {
            String safeMessage = message != null ? message : "Message is null";
            logger.error(safeMessage);
            throw new NullPointerException(safeMessage);
        }
    }

    public static <T> void validEmptyNullCollectionException(Collection<T> collection) {
        validNullException(collection, collection.getClass().getSimpleName() + " cannot be null");

        if (collection.isEmpty()) {
            logger.error("Validation failed: Collection {} is empty", collection.getClass().getSimpleName());
            throw new IllegalStateException("Collection cannot be empty");
        }
    }
}
