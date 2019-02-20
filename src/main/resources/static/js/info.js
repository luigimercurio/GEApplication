"use strict"
var info = {};

function downloadInfo (lon, lat) {
	var map = [
		{ label: "Community Development District", server: 12, attributes: ["NAME"] },
		{ label: "Community Redevelopment Area", server: 13, attributes: ["LOCATION"] },
		{ label: "Empowerment Zone", server: 14, attributes: ["NAME"] },
		{ label: "Enterprise Zone", server: 15, attributes: ["NAME"] },
		{ label: "Existing Land Use", server: 16, attributes: ["LU", "DESCR"] },
		{ label: "Urban Development", server: 17, attributes: ["UDB"] },
		{ label: "Zoning Code", server: 4, attributes: ["ZONE", "ZONE_DESC"] },
		{ label: "ZoningMunicipalities", server: 5, attributes: ["ZONE", "ZONEDESC"] }
	];

	function results (field, attrs, val) {
		var features;
		var attributes;
		var desc;

		desc = "NONE";
		if (val) {
			features = eval ("(" + val + ")").features;
			if (features.length !== 0) {
				attributes = features [0].attributes;
				desc = attributes [attrs [0]];
				for (var i = 1; i < attrs.length; i ++) {
					desc += " - " + attributes [attrs [i]];
				}
			}
		}
		info [field] = desc;
		if (Object.keys (info).length === map.length) {
			alert (JSON.stringify (info));
		}
	}

	function download (fld, attrs, url) {
		var xhr;

		xhr = new XMLHttpRequest ();
		xhr.field = fld;
		xhr.attrs = attrs;
		xhr.onreadystatechange = function () {
			if (this.readyState === 4) {
				results (this.field, this.attrs, this.status === 200 ? this.responseText : null);
			}
		};
		xhr.open ("GET", url);
		xhr.send ();
	}

	for (var i = 0; i < map.length; i ++) {
		download (map [i].label, map [i].attributes,
		          "https://gisfs.miamidade.gov/mdarcgis/rest/services/MD_PA_PropertySearch/MapServer/" +
		          map [i].server + "/query?f=json&where=&returnGeometry=false&geometry=" + lon + "," + lat +
		          "&inSR=4326&geometryType=esriGeometryPoint&outFields=*");
	}
}

//downloadInfo (-80.13148696992602, 25.783018204251302);

























