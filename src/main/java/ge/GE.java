package ge;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.merkury.jst.JSTController;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties (GEProperties.class)
public class GE {
	protected static final Log logger = LogFactory.getLog (GE.class);

	@Bean
	public CSVParser getParser () {
		logger.info ("getParser");
		return new CSVParser ();
	}

}