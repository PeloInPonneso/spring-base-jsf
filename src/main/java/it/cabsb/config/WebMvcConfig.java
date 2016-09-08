package it.cabsb.config;


import it.cabsb.i18n.Iso;
import it.cabsb.i18n.Iso.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Order(1)
@Configuration
@EnableWebMvc
@PropertySource({ "classpath:app.properties" })
@ComponentScan(basePackages = { "it.cabsb.faces.ldm", "it.cabsb.web.ws" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
    private Environment env;
	
	@Bean
	public Iso iso() {
		final Iso iso = new Iso();
		String[] langs = env.getProperty("application.lang", "available.lang").split(",");
		for (String lang : langs) {
			Locale locale = new Locale(lang);
			List<Country> countries = new ArrayList<Country>();
			for (String isoCountry : Locale.getISOCountries()) {
				Locale country = new Locale("", isoCountry);
				countries.add(iso.new Country(country.getCountry(), country.getDisplayCountry(locale)));
			}
			iso.getCountries().put(lang, countries);
		}
		return iso;
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        final PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();
        return propertySourcesPlaceholderConfigurer;
    }
	
}
