package ge.controllers;

import ge.GE;
import org.merkury.io.IOUtil;
import org.merkury.net.NetUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.OutputStream;

import static ge.GEApplication.HOME;

// http://localhost:8080/propertysearch/scripts/services/paConfiguration.js
// https://gisfs.miamidade.gov/mdarcgis/rest/services/MD_PA_PropertySearch/MapServer/16/query?f=json&where=&returnGeometry=false&geometry=<lon>,<lat>&inSR=4326&geometryType=esriGeometryPoint&outFields=*

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
		String  res;
		File    file;
		String  filePath;

		//GE.logger.info (req);
		/* THIS IS NOT CACHED! IT IS MODIFIED! PRESERVE! */
		if (req.endsWith ("/styles.css")) {
			return "/css/styles.css";
		}
		if (req.endsWith (".ashx")) {
			response.setHeader ("Content-Type", "application/json");
		}
		try {
			res = req.substring ("/propertysearch".length ());
			if (res.length () <= 1) {
				res = "/miamidadegis/index.html";
			}
			else {
				res = "/miamidadegis" + res;
			}
			//System.out.println ("-> " + res);
			file = new File (HOME, "static" + res);
			//System.out.println ("-> " + file);
			if (!file.exists ()) {
				filePath = file.getCanonicalPath ();
				GE.logger.info ("Cache " + req + " -> " + filePath);
				NetUtil.getBinaryFile ("https://www.miamidade.gov" + req, null, new String[][] {
						{ "Referer", "https://www.miamidade.gov/propertysearch/" }, { "Connection", "close" }
				}, filePath);
				if ("/miamidadegis/index.html".equals (res)) {
					IOUtil.writeToFile (filePath,
					                    IOUtil.readFromFile (filePath).replaceFirst ("<head>", scriptInjection));
				}
			}
			return res;
		}
		catch (Throwable t) {
			throw new Error (t);
		}
	}
}
