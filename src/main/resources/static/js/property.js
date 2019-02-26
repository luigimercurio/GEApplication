"use strict"

function setActiveRollYearTab (year) {
	document.getElementById ("tab" + year).className = "active";
	document.getElementById ("tab" + year + "Content").className = "tab-pane active";
	document.getElementById ("tab" + activeYear).className = "";
	document.getElementById ("tab" + activeYear + "Content").className = "tab-pane";
	activeYear = year;
}

function showMap (geometry) {
	var mapExtent = new esri.geometry.Polygon (geometry).getExtent ();
	var map = new esri.Map ("map", {
		extent: mapExtent,
		fitExtent: true,
		logo: false,
		showAttribution: false,
		isMapNavigation: false,
		slider: false
	});

	var urlAerialMap = "https://imageserverintra.miamidade.gov/ArcGIS/rest/services/MapCache/MDCImagery/MapServer";
	var urlParcelLayer = "https://gisfs.miamidade.gov/mdarcgis/rest/services/MD_PA_PropertySearch/MapServer/8";

	var aerialLayer = new esri.layers.ArcGISTiledMapServiceLayer (urlAerialMap, { id: "aerial" });
	var parcels = new esri.layers.FeatureLayer (urlParcelLayer, { id: "PAParcelLayer" });

	var parcelSymbol = {
        "color": [0,0,0,0],
        "outline": {
            "color": [255,255,0,255],
            "width": 2,
            "type": "esriSLS",
            "style": "esriSLSSolid"
        },
        "type": "esriSFS",
        "style": "esriSFSSolid"
    };

	map.geometry = geometry;
	map.addLayer (aerialLayer);
	map.addLayer (parcels);
	map.on ("resize", function () {
		var geometry = map.geometry;
		
		map.destroy ();
		showMap (map.geometry);
	});
	map.on ("load", function () {
		map.graphics.add (new esri.Graphic ({
			geometry: geometry,
			symbol: parcelSymbol
		}));
	});
}

var dictionary = [
	{ label: "Community Development District", server: 12, attributes: ["NAME"] },
	{ label: "Community Redevelopment Area", server: 13, attributes: ["LOCATION"] },
	{ label: "Empowerment Zone", server: 14, attributes: ["NAME"] },
	{ label: "Enterprise Zone", server: 15, attributes: ["NAME"] },
	{ label: "Existing Land Use", server: 16, attributes: ["LU", "DESCR"] },
	{ label: "Urban Development", server: 17, attributes: ["UDB"] },
	{ label: "Zoning Code", server: 4, attributes: ["ZONE", "ZONE_DESC"] },
	{ label: "Zoning Municipalities", server: 5, attributes: ["ZONE", "ZONEDESC"] }
];

function setAdditionalInfo (t) {
	document.getElementById ("additionalInfoDiv").innerHTML = t.page;
}

function loadInfoAndShowMap (lon, lat) {

	var info = {};

	function res (field, attrs, val) {
		var features;
		var attributes;
		var desc;

		if (field) {
			desc = "N/A";
			features = val.features;
			if (features.length != 0) {
				attributes = features [0].attributes;
				desc = attributes [attrs [0]];
				for (var i = 1; i < attrs.length; i ++) {
					desc += " - " + attributes [attrs [i]];
				}
			}
			info [field] = desc;
			if (Object.keys (info).length === dictionary.length) {
				if (info ["Zoning Code"] == "N/A") {
					info ["Zoning Code"] = info ["Zoning Municipalities"];
				}
				delete info ["Zoning Municipalities"];
				info ["Urban Development"] = (info ["Urban Development"] ? "INSIDE" : "OUTSIDE") +
						" URBAN DEVELOPMENT BOUNDARY";
				//alert (JSON.stringify (info));
				jst.load ("html/additionalinformation.jst", info).then (setAdditionalInfo);
			}
		}
		else {
			val.features [0].geometry.spatialReference = val.spatialReference;
			showMap (val.features [0].geometry);
		}
	}

	function download (url, fld, attrs) {
		var xhr;
		var result;

		xhr = new XMLHttpRequest ();
		xhr.field = fld;
		xhr.attrs = attrs;
		xhr.url = url;
		xhr.onreadystatechange = function () {
			if (this.readyState === 4) {
				if (this.status !== 200) {
					download (this.url, this.field, this.attrs);
					return;
				}
				result = eval ("(" + this.responseText + ")");
				if (result.error) {
					download (this.url, this.field, this.attrs);
					return;
				}
				res (this.field, this.attrs, result);
			}
		};
		xhr.open ("GET", url);
		xhr.send ();
	}

	download ("https://gisfs.miamidade.gov/mdarcgis/rest/services/MD_PA_PropertySearch/mapServer/1/query?" +
			  "f=json&where=&returnGeometry=true&geometry=" + lon + "," + lat +
			  "&inSR=4326&geometryType=esriGeometryPoint");
	for (var i = 0; i < dictionary.length; i ++) {
		download ("https://gisfs.miamidade.gov/mdarcgis/rest/services/MD_PA_PropertySearch/mapServer/" +
				  dictionary [i].server + "/query?f=json&where=&returnGeometry=false&geometry=" +
				  lon + "," + lat + "&inSR=4326&geometryType=esriGeometryPoint&outFields=*",
				  dictionary [i].label, dictionary [i].attributes);
	}
}

