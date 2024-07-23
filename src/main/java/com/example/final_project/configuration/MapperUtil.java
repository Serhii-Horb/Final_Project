package com.example.final_project.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class MapperUtil {

    /**
     * Creates and returns a ModelMapper bean.
     *
     * @return An instance of ModelMapper used for object mapping.
     */
    @Bean
    public ModelMapper getMapper() {
        return new ModelMapper();
    }

    /**
     * Converts a list of objects from one type to another using a provided converter function.
     *
     * @param <R> The type of objects in the resulting list.
     * @param <E> The type of objects in the source list.
     * @param list The list of objects to be converted.
     * @param converter The function used to convert each object.
     * @return A list of converted objects.
     */
    public static <R, E> List<R> convertList(List<E> list, Function<E, R> converter) {
        return list.stream().map(e -> converter.apply(e)).collect(Collectors.toList());
    }
}