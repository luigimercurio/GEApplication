package ge;

import org.merkury.json.JSONWriter;
import org.merkury.jst.JST;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Repository
public class DB {
	protected static final Logger     logger     = LoggerFactory.getLogger (DB.class);
	protected static       Connection connection = null;

	@Autowired
	DataSourceProperties env;

	@PersistenceContext
	EntityManager entityManager;

	@PostConstruct
	public void getMetamodel () {
		for (EntityType<?> entity : entityManager.getMetamodel ().getEntities ()) {
			logger.info ("Discovered entity: " + entity.getName ());
			//JSONWriter.dump (entity);
		}
	}

	/*
	static {
		new DB ();
	}
	*/

	@PostConstruct
	public void connect ()
	throws SQLException {
		connection = env.initializeDataSourceBuilder ().build ().getConnection ();
		logger.info ("Connection established");
	}

	public static Connection connection ()
	throws SQLException {
		return connection;
	}
}







