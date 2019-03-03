## Overview
Google Earth plugin for Commercial Real Estate professionals. Will present information about any property in
Miami-Dade County using a map-based interface

### Features
**Property mapping:** All properties are mapped according to their GeoLocation information (i.e. Latitude / Longitude)

**Color coding:** Properties are represented by colored pins, allowing the user to distinguish between condos /
cooperatives and single-owner properties

**Active information access:** Information is accesible py *"pushing"* a pin identifying the property and opens an
*"information bubble"* within the Google Earth screen

**Layered information access for condos/cooperatives:** When a condo/coop pin is pushed, a list of all individual
parcels present in the condo/coop is presented, allowing easy access to each one of them

**Multiple information sections:** Several clearly separated sections are present on the information bubbles:
- General property information (address, folio number, ownership, land use, square footage, floors, units, etc.)
- Parcel contour (on aerial map)
- Tax assessment information
- Value assessment information (comparative through last 3 years)
- Tax benefits information (if applicable)
- Full legal description
- Sale history
- Zoning information (with municipal zoning code)
- Individual building information for multi-building properties
- Extra features (pools, sprinklers, patios, elevators)
- Land use and governmental restrictions

**Google Earth integrated features:** The application takes advantage of available GE features:
- Available both on desktop and mobile
- Current position mapping
- 3D and aerial maps
- Street views

### Technologies
- Google Earth (main content delivery application)
- HTML (for description bubbles)
- CSS (for bubble styling)
- KML/XML (for pin positioning)
- SQL (MySQL server for data storage)
- HTTP (delivery protocol for KML and HTML)
- Apache Tomcat (Server is a web application hosted in a Tomcat container)
- CSV (County data is delivered in CSV format and has to be parsed and inserted in the SQL database)
- Java (Main language used on the web server)
- Javascript (Templating language on the web server)
- Spring / Spring Boot (Server is developed as a Spring Boot application)
- KML/XML/HTML templating (this is implemented through self-developed Javascript View Resolver)

### Objectives
- KML positioning of pins within Google Earth
- Dynamic reloading of pins upon map navigation
- Embedding HTML within KML descriptors
- Translating CSV tables into SQL tables
- Implementing GeoLocation search on database
- Integrate Spring / Spring Boot with Tomcat containers
- Logging within Tomcat container environment

## Views
<table>
  <tr>
    <td><img src="https://raw.githubusercontent.com/luigimercurio/GEApplication/master/Vertical%20panorama.jpg" width="150"/></td>
    <td><img src="https://raw.githubusercontent.com/luigimercurio/GEApplication/master/Property%20information.jpg" width="150"/></td>
    <td><img src="https://raw.githubusercontent.com/luigimercurio/GEApplication/master/Parcel%20Layout.jpg" width="150"/></td>
    <td><img src="https://raw.githubusercontent.com/luigimercurio/GEApplication/master/Assessment%20and%20value.jpg" width="150"/></td>
    <td><img src="https://raw.githubusercontent.com/luigimercurio/GEApplication/master/Legal%20description%20and%20Sales.jpg" width="150"/></td>
  </tr>
  <tr>
    <td>Vertical View</td>
    <td>Property Information</td>
    <td>Parcel Layout</td>
    <td>Assessment & Value Data</td>
    <td>Legal Description and Sales</td>
  </tr>
  <tr>
    <td colspan="2"><img src="https://raw.githubusercontent.com/luigimercurio/GEApplication/master/Horizontal%20panorama.jpg" width="300"/></td>
    <td><img src="https://raw.githubusercontent.com/luigimercurio/GEApplication/master/Additional%20Information.jpg" width="150"/></td>
    <td><img src="https://raw.githubusercontent.com/luigimercurio/GEApplication/master/Condo%20unit%20list.jpg" width="150"/></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2">Horizontal View</td>
    <td>Additional Information</td>
    <td>Condo Unit list</td>
    <td>&nbsp;</td>
  </tr>
</table>