package ge;

import org.merkury.io.IOUtil;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class GEApplication extends SpringBootServletInitializer {
	public static final String HOME = new ApplicationHome (GEApplication.class).getDir ().getAbsolutePath ();

	protected static Map<String, Object> properties () {
		String[]            dbInfo;
		Map<String, Object> properties;

		properties = new HashMap<> ();
		try {
			dbInfo = IOUtil.readFromFile (HOME + "/dbinfo").split ("\\|");
		}
		catch (Throwable t) {
			throw new Error (t);
		}
		properties.put (GEApplication.class.getSimpleName (), HOME);
		properties.put ("spring.datasource.url", dbInfo[0]);
		properties.put ("spring.datasource.username", dbInfo[1]);
		properties.put ("spring.datasource.password", dbInfo[2]);
		return properties;
	}

	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder application) {
		return application.sources (GEApplication.class).properties (properties ());
	}

	public static void main (String[] args) {
		new SpringApplicationBuilder ().sources (GEApplication.class).properties (properties ()).run ();
	}
}
