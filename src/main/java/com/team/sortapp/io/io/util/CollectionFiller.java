package com.team.sortapp.io.io.util;

import com.team.sortapp.collection.CustomArrayList;
import com.team.sortapp.collection.CustomList;

import java.util.stream.Stream;

public class CollectionFiller {

    public static <T> CustomList<T> fillFromStream(Stream<T> stream) {
        CustomList<T> list = new CustomArrayList<>();
        stream.forEach(list::add);
        return list;
    }
}