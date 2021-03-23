package projet.web.Service;

import java.sql.Connection;

import projet.web.Model.Helper;
import projet.web.Model.MoneyNonValide;
import projet.web.Model.Money_mouvement;

public class Money_service {
	public static MoneyNonValide[] getMoneyNonValide() throws Exception{
		Connection c=new Helper().getConnection();
		try {
			MoneyNonValide mn=new MoneyNonValide();
			MoneyNonValide[] valiny=mn.getMoneyNonValide(c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
	public static Boolean Validation(int id_mouvement)throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Boolean valiny=new Money_mouvement().valider_transaction(id_mouvement, c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
}
