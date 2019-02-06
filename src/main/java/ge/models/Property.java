package ge.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Property {

	@Id
	private int id;

	@Size (min=3, max=30)
	private String name;

	@Size(min=1, message = "Description must not be empty")
	private String description;

	/*
	@ManyToOne
	private Category category;

	@ManyToMany (mappedBy="cheeses")
	private List<Menu> menus;
	*/

	public Property() {
	}

	double x,y,objectid,folio,ttrrss,x_coord,y_coord,true_site_addr,true_site_unit,true_site_city,true_site_zip_code,true_mailing_addr1,true_mailing_addr2,true_mailing_addr3,true_mailing_city,true_mailing_state,true_mailing_zip_code,true_mailing_country,true_owner1,true_owner2,true_owner3,condo_flag,parent_folio,dor_code_cur,dor_desc,subdivision,bedroom_count,bathroom_count,half_bathroom_count,floor_count,unit_count,building_actual_area,building_heated_area,lot_size,year_built,assessment_year_cur,assessed_val_cur,dos_1,price_1,legal,pid;
}
