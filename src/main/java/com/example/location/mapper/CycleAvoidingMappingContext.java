
package com.example.location.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * An implementation to track cycles in graphs to be used as {@link Context} parameter.
 *
 */
public class CycleAvoidingMappingContext {
    private Map<String, Object> knownInstances = new HashMap<>();

    /**
     * Gets an instance out of this context if it is already mapped.
     *
     * @param source
     *        given source
     * @param targetType
     *        given target type.
     * @return Returns the resulting type.
     */
    @BeforeMapping
    public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
        if (Objects.isNull(source))
            return null;
       String objectHashMapCode = source.getClass().getSimpleName().concat("@").concat(String.valueOf(source.hashCode()));
        return Objects.isNull(objectHashMapCode)? null : targetType.cast(knownInstances.get(objectHashMapCode));
    }

    /**
     * Puts an instance into the cache, so that it can be remembered to avoid endless mapping.
     *
     * @param source
     *        given source
     * @param target
     *        given target
     */
    @BeforeMapping
    public void storeMappedInstance(Object source, @MappingTarget Object target) {
        knownInstances.put(source.getClass().getSimpleName().concat("@").concat(String.valueOf(source.hashCode())), target);
    }
}
