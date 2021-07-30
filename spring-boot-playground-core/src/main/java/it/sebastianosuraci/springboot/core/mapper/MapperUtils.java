package it.sebastianosuraci.springboot.core.mapper;

import org.hibernate.collection.spi.PersistentCollection;

class MapperUtils {
    static boolean wasInitialized(Object c) {
        if (!(c instanceof PersistentCollection)) {
            return true;
        }

        PersistentCollection pc = (PersistentCollection) c;
        return pc.wasInitialized();
    }

    private MapperUtils() {}
}
