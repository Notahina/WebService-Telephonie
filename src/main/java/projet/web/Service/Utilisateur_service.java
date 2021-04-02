package projet.web.Service;

import java.sql.Connection;
import java.sql.Statement;

import projet.web.Model.Helper;
import projet.web.Model.Token;
import projet.web.Model.UserAdmin;
import projet.web.Model.Utilisateur;

public class Utilisateur_service {
	public static Boolean Logout(String token) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Token t=new Token();
			String tok=t.BearerToken(token);
			String requete="DELETE FROM token where id_token='"+tok+"'";
			System.out.println(requete);
			Statement state= c.createStatement();
			state.execute(requete);
			return true;
		}catch(Exception e) {
			throw e;
		}finally {
			c.close();
		}	
	}
	public static String LoginAdminToken(String login,String pwd)throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Utilisateur user=new Utilisateur();
			user=user.getUserAdmin(c, login, pwd);
			System.out.print("Admin id===="+user.getId_utilisateur());
			String idtoken=user.GetTokenUser(c, user.getId_utilisateur());
			return idtoken;
		}catch(Exception e) {
			throw e;
		}finally {
			c.close();
		}	
	}
	public static String  LoginUserToken(String login,String pwd) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Utilisateur user=new Utilisateur();
			user=user.getUtilisateur(c, login, pwd);
			String idtoken=user.GetTokenUser(c, user.getId_utilisateur());
			return idtoken;
		}catch(Exception e) {
			throw e;
		}finally {
			c.close();
		}
	}
	public static String CreteUser(Utilisateur user) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			String mdp=user.getMdp();
			user.CreateUser(c, user);
			user=user.getUtilisateur(c, user.getLogin(),mdp);
			String idtoken=user.GetTokenUser(c, user.getId_utilisateur());
			return idtoken;
		}catch(Exception e) {
			throw e;
		}finally {
			c.close();
		}
	}
}
