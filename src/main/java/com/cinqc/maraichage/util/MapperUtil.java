package com.cinqc.maraichage.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;


public class MapperUtil {
	private static ModelMapper modelMapper = new ModelMapper();

	private MapperUtil() {
	}

	public static ModelMapper getModelMapperInstance() {
		return modelMapper;
	}

	public static <S, T> List<T> mapList(Iterable<S> source, Class<T> targetClass) {
		return StreamSupport.stream(source.spliterator(), false).map(element -> modelMapper.map(element, targetClass))
				.collect(Collectors.toList());
	}
	
}
