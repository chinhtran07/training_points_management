package com.tps.components;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Component
public class EntityScanner {
    public Set<String> scanEntities(String basePackage) {
        Set<String> entityNames = new HashSet<>();
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        TypeFilter filter = new AnnotationTypeFilter(Entity.class);
        scanner.addIncludeFilter(filter);

        for (BeanDefinition bd : scanner.findCandidateComponents(basePackage)) {
            String className = bd.getBeanClassName();
            if (className != null) {
                entityNames.add(className);
            }
        }
        return entityNames;
    }
}
