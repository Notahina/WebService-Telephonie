package projet.web.Service;

import java.sql.Connection;

import projet.web.Model.Appel;
import projet.web.Model.Credit_mouvement;
import projet.web.Model.Helper;
import projet.web.Model.Utilisateur;

public class Credit_service {
	public static Boolean achat_credit_code(int id_utilisateur,String code) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Credit_mouvement cm=new Credit_mouvement();
			boolean valiny=cm.acheter_credit_code(id_utilisateur,code, c);
			return valiny;
		}finally {
			c.close();
		}
		
	}
	
	public static Boolean achat_credit_money(int id_utilisateur,double montant) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Credit_mouvement cm=new Credit_mouvement();
			boolean valiny=cm.acheter_credit_mobile(id_utilisateur, montant, c);
			return valiny;
		}finally {
			c.close();
		}
		
	}

}
