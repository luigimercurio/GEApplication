package ge.controllers;

import ge.GE;
import org.merkury.io.IOUtil;
import org.merkury.util.NetUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;

@Controller
@RequestMapping ("")
public class GERelayer {
	protected static final String scriptInjection =
			"<head>\n<script src=\"/js/set.js\"></script>\n<script src=\"/js/map.js\"></script>\n";

	@GetMapping ("/PApublicServiceProxy/**")
	public void active (HttpServletRequest request, HttpServletResponse response) {
		String       req = request.getRequestURI ();
		String       qs  = request.getQueryString ();
		OutputStream os;

		if (qs != null) {
			req += '?' + qs;
		}
		GE.logger.info (req);
		try {
			os = response.getOutputStream ();
			NetUtil.relay ("https://www.miamidade.gov" + req, null, new String[][] {
					{ "Referer", "https://www.miamidade.gov/propertysearch/" }, { "Connection", "close" }
			}, os);
			os.close ();
		}
		catch (Throwable t) {
			throw new Error (t);
		}
	}

	@GetMapping ("/propertysearch/**")
	public String passive (HttpServletRequest request, HttpServletResponse response) {
		String  req = request.getRequestURI ();
		String  qs  = request.getQueryString ();
		String  res;
		File    dir;
		File    file;
		boolean inject;
		String  filePath;

		//System.out.println (req + '?' + qs);
		/* THIS IS NOT CACHED! IT IS MODIFIED! PRESERVE! */
		if (req.endsWith ("/styles.css")) {
			return "/miamidadegis/styles/styles.css";
		}
		if (req.endsWith (".ashx")) {
			response.setHeader ("Content-Type", "application/json");
		}
		try {
			res = req.substring ("/propertysearch".length ());
			if (res.length () <= 1) {
				res = "/miamidadegis/index.html";
				inject = true;
			}
			else {
				res = "/miamidadegis" + res;
				inject = false;
			}
			//System.out.println ("-> " + res);
			dir = new ClassPathResource ("").getFile ();
			file = new File (dir, "static" + res);
			//System.out.println ("-> " + file);
			if (!file.exists ()) {
				filePath = file.getCanonicalPath ();
				GE.logger.info ("Cache " + req + " -> " + filePath);
				NetUtil.getBinaryFile ("https://www.miamidade.gov/" + req, null, new String[][] {
						{ "Referer", "https://www.miamidade.gov/propertysearch/" }, { "Connection", "close" }
				}, filePath);
				if (inject) {
					IOUtil.writeToFile (filePath,
					                    IOUtil.readFromFile (filePath).replace ("<head>", scriptInjection));
				}
			}
			return res;
		}
		catch (Throwable t) {
			throw new Error (t);
		}
	}
}
