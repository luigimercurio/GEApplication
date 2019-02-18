package ge.models.data;

import ge.models.RegisteredUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RegisteredUserDao extends CrudRepository<RegisteredUser, String> {
}
