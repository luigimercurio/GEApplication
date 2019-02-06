package ge;

import org.springframework.boot.ApplicationHome;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.io.File;

@SpringBootApplication
public class GEApplication extends SpringBootServletInitializer {
	public static final File HOME;

	static {
		HOME = new ApplicationHome (GEApplication.class).getDir ();
		System.setProperty (GEApplication.class.getSimpleName (), HOME.getAbsolutePath ());
	}

	@Override
	protected SpringApplicationBuilder configure (SpringApplicationBuilder application) {
		return application.sources (GEApplication.class);
	}

	public static void main (String[] args) {
		SpringApplication.run (GEApplication.class);
	}
}
