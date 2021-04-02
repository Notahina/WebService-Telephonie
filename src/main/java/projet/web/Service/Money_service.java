package projet.web.Service;

import java.sql.Connection;
import java.util.Date;

import projet.web.Model.AuthService;
import projet.web.Model.Helper;
import projet.web.Model.Model;
import projet.web.Model.MoneyNonValide;
import projet.web.Model.Money_mouvement;
import projet.web.Model.Token;
import projet.web.Model.Utilisateur;

public class Money_service {
	public static MoneyNonValide[] getMoneyNonValide(String idtoken) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Token token=new Token().VerifyToken(c, idtoken);
			MoneyNonValide mn=new MoneyNonValide();
			MoneyNonValide[] valiny=mn.getMoneyNonValide(c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
	public static Boolean Validation(int id_mouvement,String authHeader)throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Token token=new Token().VerifyToken(c, authHeader);
			String req="SELECT valider_transaction("+id_mouvement+")";
			System.out.println("req"+req);
			Boolean valiny=(boolean) new Model().getOne(req, "boolean", c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
	public static Boolean deposer(double montant,String mdp,String authHeader)throws Exception{
		Connection c=new Helper().getConnection();
		try {

			Token token=new Token().VerifyToken(c, authHeader);
			Utilisateur user=new Utilisateur();
			user.CheckMotdepasse(c, token.getId_utilisateur(), mdp);
			String req="SELECT depot_money("+token.getId_utilisateur()+","+montant+",'now()')";
			System.out.println("requte "+req);
			Boolean valiny=(boolean) new Model().getOne(req, "boolean", c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
	public static Boolean retirer(int idUtilisateur,double montant,String authHeader)throws Exception{
		Connection c=new Helper().getConnection();
		try {

			Token token=new Token().VerifyToken(c, authHeader);
			String req="SELECT retrait_money("+token.getId_utilisateur()+","+montant+",'now()')";
			Boolean valiny=(boolean)new Model().getOne(req, "boolean", c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
	
	public static double getSolde(String authHeader)throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Token token=new Token().VerifyToken(c, authHeader);
			Model model=new Model();
			String req="SELECT get_solde_money("+token.getId_utilisateur()+",'now()')";
			double valiny=(double)model.getOne(req, "double", c);
			return valiny;
		}catch(Exception e) {
			throw e;
		}finally{
			c.close();	
		}
	}
}
