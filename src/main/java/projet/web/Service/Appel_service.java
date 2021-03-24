package projet.web.Service;

import java.sql.Connection;

import projet.web.Model.Appel;
import projet.web.Model.Helper;
import projet.web.Model.Utilisateur;

public class Appel_service {
	public static Boolean appeler(Appel appel) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Utilisateur util=new Utilisateur().get_by_num(c, appel.getNumero1());
			boolean valiny=new Appel().appeler(util.getId_utilisateur(),appel.getNumero2(),appel.getDuree(),c);
			return valiny;
		}finally {
			c.close();
		}
		
	}
	public static Appel[] historique(int idUtilisateur,String types,String dates) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			 Appel[] histo=new Appel().get_historique(idUtilisateur,types,dates,c);
			 return histo;
		}finally {
			c.close();
		}
		
	}

}
