package projet.web.Service;

import java.sql.Connection;

import projet.web.Model.Appel;
import projet.web.Model.Helper;
import projet.web.Model.Model;
import projet.web.Model.Token;
import projet.web.Model.Utilisateur;

public class Appel_service {
	public static Boolean appeler(Appel appel,String authHeader) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Model model=new Model();
			Token token=new Token().VerifyToken(c, authHeader);
			String req="SELECT appeler("+token.getId_utilisateur()+",'"+appel.getNumero2()+"',"+appel.getDuree()+",'"+appel.getDate_appel()+"')";
			boolean valiny=(boolean)model.getOne(req, "boolean", c);
			return valiny;
		}finally {
			c.close();
		}
		
	}

	public static Appel[] historique(String authHeader,String types,String dates) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Model model=new Model();
			Token token=new Token().VerifyToken(c, authHeader);
			 String req="SELECT historique_appel("+token.getId_utilisateur()+",'"+types+"','"+dates+"')";
			 Object[] oo=model.getResult(req, new Appel(),c);
			 Appel[] histo=new Appel[oo.length];
			 for(int i=0;i<oo.length;i++)
			 {
				 histo[i]=(Appel)oo[i];
			 }
			 return histo;
		}finally {
			c.close();
		}
	}

}
