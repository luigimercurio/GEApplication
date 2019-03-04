package ge;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.merkury.net.NetUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties (GEProperties.class)
public class GE {
	public static final Log logger = LogFactory.getLog (GE.class);

	static {
		NetUtil.setDefaultHeaders (new String[][] {
				{ "User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:57.0) Gecko/20100101 Firefox/57.0" },
				{ "Referer", "https://www.miamidade.gov/propertysearch/" },
				{ "Connection", "close" }
		});
	}

	@Bean
	public CSVParser csvParser () {
		logger.info ("csvParser");
		return new CSVParser ();
	}

}