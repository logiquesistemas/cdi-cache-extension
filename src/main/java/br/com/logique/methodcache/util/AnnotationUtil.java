package br.com.logique.methodcache.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * Annotation utilities.
 *
 * Created by Gustavo on 21/11/2015.
 */
public class AnnotationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationUtil.class);

    public static <T extends Annotation> Optional<T> getAnnotation(Object proxy, Method method, Class<T> clazz)  {
        T annotation = null;
        try {
            Method classMethod = proxy.getClass().getMethod(method.getName(), method.getParameterTypes());
            annotation = classMethod.getDeclaredAnnotation(clazz);
            if (annotation == null) {
                annotation = AnnotationUtils.findAnnotation(classMethod, clazz);
            }

        } catch (NoSuchMethodException | SecurityException ex) {
            LOGGER.error("Failed to get Annotation {} from {}", clazz, method.getName(), ex);
        }
        return Optional.ofNullable(annotation);
    }

}
