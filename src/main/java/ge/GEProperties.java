package ge;

import org.merkury.jst.JST;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties (prefix = "ge.dataset")
public class GEProperties {
	protected String link = "";
	protected String file = "";

	public String getLink () {
		return link;
	}

	public void setLink (String l) {
		GE.logger.info ("Setting link to: " + l);
		link = l;
	}

	public String getFile () {
		return file;
	}

	public void setFile (String f) {
		GE.logger.info ("Setting file to: " + f);
		file = f;
	}
}

