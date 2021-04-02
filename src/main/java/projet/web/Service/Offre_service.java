package projet.web.Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import projet.web.Model.Credit_mouvement;
import projet.web.Model.Forfait_offre;
import projet.web.Model.Getsoldeforfait;
import projet.web.Model.Helper;
import projet.web.Model.Model;
import projet.web.Model.Offre;
import projet.web.Model.Offre_mouvement;
import projet.web.Model.Stat_offre;
import projet.web.Model.Token;

public class Offre_service {
	public static Boolean insertForfait(Forfait_offre[] fo,String authHEader) throws SQLException {
		Connection c=new Helper().getConnection();
		try {
			Forfait_offre fot=new Forfait_offre();
			for(int i=0;i<fo.length;i++) {
				fot.insertForfaitOffre(fo[i], c);
			} 
			return true;
		}catch(Exception e) {
			return false;
		}finally{
			c.close();
		}
	}
	public static Boolean insertOffre(Offre offre) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			boolean valiny=new Offre().insertOffre(offre, c);
			return valiny;
		}finally {
			c.close();
		}
		
	}
	public static Stat_offre[] get_stat(String periode,String dates,int idoffre) throws Exception {
		Connection c=new Helper().getConnection();
		try {
			Stat_offre[] stat=new Stat_offre().get_stat_offre(periode, dates,idoffre, c);
			if(stat.length==0) {
				throw new Exception("Aucune donne");
			}
		
			return stat;
		}finally {
			c.close();
		}
		
	}
	public static Offre[] getOffre()throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Offre[] getOffre=new Offre().get_all_offre(c);
			return getOffre;
		}finally {
			c.close();
		}
		
	}

	public static boolean acheter_offre(String authHeader,int idOffre,String typeAchat,String dates) throws Exception
	{
		Connection c=new Helper().getConnection();
		try {
			Token token=new Token().VerifyToken(c, authHeader);
			String req="SELECT acheter_offre("+token.getId_utilisateur()+","+idOffre+",'"+typeAchat+"','now()')";
			boolean status=(boolean) new Model().getOne(req, "boolean", c);
			if(status==false) {
				throw new Exception("Votre solde "+typeAchat.toLowerCase()+" est insuffisant");
			}
			return status;
		}finally {
			c.close();
		}
	}
	
	public static Offre[] offre_of_utilisateur(String autHeader,String dates)throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Token token=new Token().VerifyToken(c, autHeader);
			String req="select *from get_offre_utilisateur("+token.getId_utilisateur()+",'now()')";
			Object[] ob=new Model().getResult(req, new Offre(), c);
			Offre[] offre=new Offre[ob.length];
			for(int i=0;i<offre.length;i++)
			{
				offre[i]=(Offre)ob[i];
			}
			return offre;
		}finally {
			c.close();
		}
		
	}
	public static Getsoldeforfait[] soldeforfaituser(String authHeader,String date,String idoffre) throws Exception{
		Connection c=new Helper().getConnection();
		try {
			Model model=new Model();
			Token token=new Token().VerifyToken(c, authHeader);
			String requete="select * from get_solde_forfait_join("+token.getId_utilisateur()+","+idoffre+",'now()')";
			Object[] o=model.getResult(requete, new Getsoldeforfait(), c);
			Getsoldeforfait[] get=new Getsoldeforfait[o.length];
			for(int i=0;i<get.length;i++) {
				get[i]=(Getsoldeforfait)o[i];
			}
			return get;
		}finally {
			c.close();
		}
	}
	/*public static boolean achat_offre_credit(int id_utilisateur,int id_offre) throws Exception
	{
		Connection c=new Helper().getConnection();
		try {
			boolean status=new Offre_mouvement().acheter_offre_credit(id_utilisateur, id_offre, c);
			return status;
		}finally {
			c.close();
		}
	}
	
	public static boolean achat_offre_money(int id_utilisateur,int id_offre) throws Exception
	{
		Connection c=new Helper().getConnection();
		try {
			boolean status=new Offre_mouvement().acheter_offre_mobile(id_utilisateur, id_offre, c);
			return status;
		}finally {
			c.close();
		}
	}*/
}
