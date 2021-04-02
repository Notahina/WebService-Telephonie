package projet.web.Service;

import java.sql.Connection;

import projet.web.Model.Helper;
import projet.web.Model.Utilisation;

public class Utilisation_service {
	public static Utilisation[] getUtilisation() throws Exception {
		Connection c = new Helper().getConnection();
		try {
			Utilisation[] u=new Utilisation().getUtilisation(c);
			return u;
		}catch(Exception e) {
			throw e;
		}finally {
			c.close();
		}
	}
}
