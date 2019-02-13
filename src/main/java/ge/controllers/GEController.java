package ge.controllers;

import ge.GE;
import ge.models.Property;
import ge.models.data.PropertyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping ("")
public class GEController {
	protected static class Coordinates {
		public double west = 0;
		public double south = 0;
		public double east = 0;
		public double north = 0;
		public double lon = 0;
		public double lat = 0;
		public double range = 0;
		public double tilt = 0;
		public double heading = 0;

		protected Coordinates (String bbox) {
			String [] p = bbox.split (",");
			int len = p.length;

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

	@GetMapping ("prop.kml")
	public String index (HttpServletResponse response) {
		response.setHeader (HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		return "jst/prop";
	}


	@GetMapping ("propx.kml")
	public String index (Model model, HttpServletResponse response,
	                     @RequestParam (required = false) String bbox) {
		Coordinates coord;
		Property [] properties;

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

		return "jst/propx";
	}
}
