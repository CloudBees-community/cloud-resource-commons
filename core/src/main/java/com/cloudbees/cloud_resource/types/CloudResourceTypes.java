package com.cloudbees.cloud_resource.types;

import java.util.HashSet;
import java.util.Set;

/**
 * Various methods to gather cloud resource type names from a resource.
 *
 * @author Kohsuke Kawaguchi
 */
public class CloudResourceTypes {
    /**
     * Recursively list up all the {@link CloudResourceType} annotations on the given class
     * and return them.
     */
    public static Set<String> of(Class<?> bean) {
        Set<String> r = new HashSet<String>();
        typesOf(bean,r);
        return r;
    }

    public static Set<String> of(Object instance) {
        return of(instance.getClass());
    }

    private static void typesOf(Class<?> t, Set<String> r) {
        if (t==null)    return;
        CloudResourceType a = t.getAnnotation(CloudResourceType.class);
        if (a!=null)
            r.add(a.value());
        for (Class<?> i : t.getInterfaces()) {
            typesOf(i,r);
        }
        typesOf(t.getSuperclass(),r);
    }
}
