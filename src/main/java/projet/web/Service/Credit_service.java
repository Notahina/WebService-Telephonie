package projet.web.Service;

import java.sql.Connection;

import projet.web.Model.Credit;
import projet.web.Model.Helper;

public class Credit_service {
	public static boolean GenereCodeCredit(Credit credit) throws Exception{
		Connection c=new Helper().getConnection();
		boolean val=false;
		try {
			val=credit.insertCredit(credit, c);	
		}catch(Exception e) {
			throw e;
		}finally {
			c.close();
		}
		return val;
	}
}
