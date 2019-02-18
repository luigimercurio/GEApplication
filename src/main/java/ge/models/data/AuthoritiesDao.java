package ge.models.data;

import ge.models.Authorities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface AuthoritiesDao extends CrudRepository<Authorities, Integer> {
}