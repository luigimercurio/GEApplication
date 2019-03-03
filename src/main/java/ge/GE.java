package ge;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties (GEProperties.class)
public class GE {
	public static final Log logger = LogFactory.getLog (GE.class);

	@Bean
	public CSVParser csvParser () {
		logger.info ("csvParser");
		return new CSVParser ();
	}

}