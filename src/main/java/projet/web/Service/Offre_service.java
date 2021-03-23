package projet.web.Service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import projet.web.Model.Helper;
import projet.web.Model.Offre;
import projet.web.Model.Stat_offre;

public class Offre_service {
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
}