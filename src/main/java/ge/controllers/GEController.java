package ge.controllers;

import ge.GE;
import ge.models.Property;
import ge.models.data.PropertyDao;
import org.merkury.json.JSONArray;
import org.merkury.json.JSONObject;
import org.merkury.util.NetUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping ("")
public class GEController {
	protected static final String propertyURL =
			"https://www.miamidade.gov/PApublicServiceProxy/PaServicesProxy.ashx?"
					+ "Operation=GetPropertySearchByFolio&clientAppName=PropertySearch&folioNumber=";
	protected static final String listURL     =
			"https://www.miamidade.gov/PApublicServiceProxy/PaServicesProxy.ashx?"
					+ "Operation=GetPropertySearchByPartialFolio&clientAppName=PropertySearch&partialFolioNumber=";

	protected static class Coordinates {
		public double west    = 0;
		public double south   = 0;
		public double east    = 0;
		public double north   = 0;
		public double lon     = 0;
		public double lat     = 0;
		public double range   = 0;
		public double tilt    = 0;
		public double heading = 0;

		protected Coordinates (String bbox) {
			String[] p   = bbox.split (",");
			int      len = p.length;

			switch (len) {
				case 9:
					heading = Double.parseDouble (p[8]);
				case 8:
					tilt = Double.parseDouble (p[7]);
				case 7:
					range = Double.parseDouble (p[6]);
				case 6:
					lat = Double.parseDouble (p[5]);
				case 5:
					lon = Double.parseDouble (p[4]);
				case 4:
					north = Double.parseDouble (p[3]);
				case 3:
					east = Double.parseDouble (p[2]);
				case 2:
					south = Double.parseDouble (p[1]);
				case 1:
					west = Double.parseDouble (p[0]);
				default:
					break;
			}
		}
	}

	@Autowired
	protected PropertyDao dao;

	@GetMapping ("")
	public String index () {
		return "index.html";
	}

	@GetMapping ("property")
	public String property () {
		return "index.html";
	}

	@GetMapping ("property/{folio}")
	public String property (Model model, @PathVariable String folio) {
		JSONObject res;
		JSONArray  list;
		Property   property;
		int        current;
		int        count;

		try {
			switch (folio.length ()) {
				case 9:
					list = new JSONArray ();
					current = 0;
					do {
						res = JSONObject.from (NetUtil.getCharFile (
								listURL + folio + "&from=" + (current + 1) + "&to=" + (current + 200), null,
								new String[][] {
										{ "Referer", "https://www.miamidade.gov/propertysearch/" },
										{ "Connection", "close" }
								}));
						list.addArray (res.array ("MinimumPropertyInfos"));
						current += 200;
						count = res.intValue ("Total");
						//GE.logger.info (current + " / " + count);
					} while (current < count);
					model.addAttribute ("list", list);
					return "html/list";
				case 13:
					property = dao.load (folio);
					model.addAttribute ("folio", folio);
					model.addAttribute ("lon", property.getX ());
					model.addAttribute ("lat", property.getY ());
					res = JSONObject.from (NetUtil.getCharFile (propertyURL + folio, null, new String[][] {
							{ "Referer", "https://www.miamidade.gov/propertysearch/" },
							{ "Connection", "close" }
					}));
					model.addAllAttributes (res.asMap ());
					return "html/property";
				default:
					break;
			}
		}
		catch (Throwable t) {
			throw new Error (t);
		}
		return "forward:/index.html";
	}

	@GetMapping ("prop.kml")
	public String prop (Model model, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader (HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		model.addAttribute ("host", "http://" + request.getServerName () + ':' + request.getServerPort ());
		return "kml/prop";
	}

	@GetMapping ("prop2.kml")
	public String prop2 (Model model, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader (HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		return "kml/prop2";
	}

	@GetMapping ("propx.kml")
	public String propx (Model model, HttpServletResponse response,
	                     @RequestParam (required = false) String bbox) {
		Coordinates coord;
		Property[]  properties;

		if (bbox == null) {
			bbox = "-80.194573,25.772941,-80.192573,25.774941,-80.193573,25.773941,1000,0,0";
		}
		GE.logger.info ("bbox: " + bbox);
		coord = new Coordinates (bbox);
		properties = dao.locate (coord.lon - 0.0025, coord.lon + 0.0025, coord.lat - 0.0025, coord.lat + 0.0025);
		GE.logger.info (properties.length + " properties");
		response.setHeader (HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		model.addAttribute ("properties", properties);
		model.addAttribute ("range", coord.range);

		return "kml/propx";
	}
}
