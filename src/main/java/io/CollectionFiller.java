package io;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;

import java.util.stream.Stream;

public class CollectionFiller {

    // Принимает стандартный Java Stream и последовательно наполняет им CustomList.
    public static <T> CustomList<T> fillFromStream(Stream<T> stream) {
        CustomList<T> list = new CustomArrayList<>();
        stream.forEach(list::add);
        return list;
    }
}