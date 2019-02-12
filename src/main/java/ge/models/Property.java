package ge.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table (name = "Properties",
		indexes = { @Index (columnList = "x"), @Index (columnList = "y"), @Index (columnList = "parentFolio") })
public class Property {

	@Id
	private int id;

	private double x;
	private double y;

	@Size (min = 13, max = 13)
	private String folio;

	@Size (min = 13, max = 13)
	private String parentFolio;

	@Size (max = 100)
	private String address;

	public double getX () {
		return x;
	}

	public void setX (double x) {
		this.x = x;
	}

	public double getY () {
		return y;
	}

	public void setY (double y) {
		this.y = y;
	}

	public String getFolio () {
		return folio;
	}

	public void setFolio (String folio) {
		this.folio = folio;
	}

	public String getParentFolio () {
		return parentFolio;
	}

	public void setParentFolio (String parentFolio) {
		this.parentFolio = parentFolio;
	}

	public String getAddress () {
		return address;
	}

	public void setAddress (String address) {
		this.address = address;
	}

	/*
	@ManyToOne
	private Category category;

	@ManyToMany (mappedBy="cheeses")
	private List<Menu> menus;
	*/

	public Property () {}

	public Property (int id, double x, double y, String folio, String parentFolio, String address) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.folio = folio;
		this.parentFolio = parentFolio;
		this.address = address;
	}

	/*
	double x, y, objectid, folio, ttrrss, x_coord, y_coord, true_site_addr, true_site_unit, true_site_city,
			true_site_zip_code, true_mailing_addr1, true_mailing_addr2, true_mailing_addr3, true_mailing_city,
			true_mailing_state, true_mailing_zip_code, true_mailing_country, true_owner1, true_owner2, true_owner3,
			condo_flag, parent_folio, dor_code_cur, dor_desc, subdivision, bedroom_count, bathroom_count,
			half_bathroom_count, floor_count, unit_count, building_actual_area, building_heated_area, lot_size,
			year_built, assessment_year_cur, assessed_val_cur, dos_1, price_1, legal, pid;
	*/
}
