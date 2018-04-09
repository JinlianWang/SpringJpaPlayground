package com.springjpa.converter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;


public abstract class ConversionServiceAwareConverter<S, T> implements Converter<S, T> {
	
	@Autowired
    protected ConversionService conversionService;

    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }
    
    @PostConstruct
    private void register() {
      if (conversionService instanceof ConverterRegistry) {
        ((ConverterRegistry) conversionService).addConverter(this);
      } else {
        throw new IllegalStateException("Can't register Converter to ConverterRegistry");
      }
    }
}