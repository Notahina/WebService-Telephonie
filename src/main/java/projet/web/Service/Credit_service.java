package projet.web.Service;

import java.sql.Connection;

import projet.web.Model.Appel;
import projet.web.Model.Credit;
import projet.web.Model.Credit_mouvement;
import projet.web.Model.Helper;
import projet.web.Model.Model;
import projet.web.Model.Token;
import projet.web.Model.Utilisateur;

public class Credit_service {
	public static double get_solde_credit(String dates,String auth) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Model model=new Model();
			Token token=new Token().VerifyToken(c, auth);
			String req="SELECT get_solde_credit("+token.getId_utilisateur()+",'"+dates+"')";
			double valiny=(double)model.getOne(req,"double",c);
			return valiny;
		}finally {
			c.close();
		}
		
	}

	public static Boolean achat_credit_code(String code,String auth) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Model model=new Model();
			Token token=new Token().VerifyToken(c, auth);
			Credit_mouvement cm=new Credit_mouvement();
			boolean valiny=cm.acheter_credit_code(token.getId_utilisateur(),code, c);
			return valiny;
		}finally {
			c.close();
		}
		
	}
	
	public static Boolean achat_credit_money(double montant,String authHeader) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Model model=new Model();
			Token token=new Token().VerifyToken(c, authHeader);
			Credit_mouvement cm=new Credit_mouvement();
			boolean valiny=cm.acheter_credit_mobile(token.getId_utilisateur(), montant, c);
			return valiny;
		}finally {
			c.close();
		}
		
	}
	public static Boolean generecredit(int valeur,String authHeader)throws Exception {
		Connection c=new Helper().getConnection();
		try {
			Credit credit=new Credit();
			credit.setMontant(valeur);
			boolean valiny=credit.insertCreditGenere(credit, c);
			return valiny;
		}finally {
			c.close();
		}
	}
	public static Credit[] getCredit() throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Credit credaka=new Credit();
			Credit[] p=credaka.get_credit( c);
			return p;
		}finally {
			c.close();
		}
	}
}
