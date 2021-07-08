package it.sebastianosuraci.springboot.demo.mapper;

import org.hibernate.collection.spi.PersistentCollection;

class MapperUtil {
    static boolean wasInitialized(Object c) {
        if (!(c instanceof PersistentCollection)) {
            return true;
        }

        PersistentCollection pc = (PersistentCollection) c;
        return pc.wasInitialized();
    }

    private MapperUtil() {}
}
