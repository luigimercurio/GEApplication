package ge.models.data;

import ge.models.Property;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PropertyDao extends CrudRepository<Property, Integer> {
	@Query ("SELECT p FROM Property p WHERE (p.x BETWEEN :x1 AND :x2) AND (p.y BETWEEN :y1 AND :y2) AND p.parentFolio IS NULL")
	Property[] locate (@Param ("x1") double x1, @Param ("x2") double x2, @Param ("y1") double y1,
	                   @Param ("y2") double y2);
}
