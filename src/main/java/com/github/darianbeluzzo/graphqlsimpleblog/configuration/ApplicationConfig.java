package com.github.darianbeluzzo.graphqlsimpleblog.configuration;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.NumberUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Configuration
public class ApplicationConfig {

    @Bean
    public GraphQLScalarType longType() {
	return ExtendedScalars.GraphQLLong;
    }

    /**
     * Classe responsável por converter um DTO em uma Entity e vice-versa
     *
     * @return ModelMapper instance
     */
    @Bean
    public ModelMapper modelMapper() {

	Locale brasil = new Locale("pt", "BR");
	DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(brasil));

	ModelMapper modelMapper = new ModelMapper();

	Converter<String, BigDecimal> stringToBigDecimal = new AbstractConverter<String, BigDecimal>() {
	    @Override
	    protected BigDecimal convert(String source) {
		return source == null ? null : NumberUtils.parseNumber(source, BigDecimal.class, df);
	    }
	};

	Converter<BigDecimal, String> bigDecimalToString = new AbstractConverter<BigDecimal, String>() {
	    @Override
	    protected String convert(final BigDecimal dest) {
		return dest == null ? null : df.format(dest);
	    }
	};

	modelMapper.addConverter(stringToBigDecimal);
	modelMapper.addConverter(bigDecimalToString);

	return modelMapper;
    }
}