package ge;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

@Configuration
@Repository
public class GEConfiguration extends WebMvcConfigurerAdapter {
	/*
	@Override
	public void addResourceHandlers (ResourceHandlerRegistry registry) {
		registry.addResourceHandler ("/css/**", "/templates/**")
		        .addResourceLocations ("/static/")
		        .setCacheControl (CacheControl.maxAge (365, TimeUnit.DAYS));
	}
	*/
}
