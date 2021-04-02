package projet.web.Service;

import java.sql.Connection;

import projet.web.Model.Forfait;
import projet.web.Model.Helper;

public class Forfait_service {
	public static Forfait[] getForfait() throws Exception{
		Connection c= new Helper().getConnection();
		try {
			Forfait[] forf=new Forfait().getAllforfait(c);
			return forf;
		}catch(Exception e) {
			throw e;
		}finally {
			c.close();
		}
	}
}
