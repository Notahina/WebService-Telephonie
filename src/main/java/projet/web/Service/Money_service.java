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
	public static Boolean deposer(int idUtilisateur,double montant)throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Boolean valiny=new Money_mouvement().deposer(idUtilisateur, montant, c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
	public static Boolean retirer(int idUtilisateur,double montant)throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Boolean valiny=new Money_mouvement().retirer(idUtilisateur, montant, c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
	
	public static double getSolde(int idUtilisateur,String dates)throws Exception{
		Connection c=new Helper().getConnection();
		try {
			double valiny=new Money_mouvement().get_solde_utilisateur(idUtilisateur, dates, c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
}
