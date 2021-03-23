package projet.web.Model;

import java.sql.Connection;

public class Stat_offre {
	String dates;
	double valeur;
	int id_offre;
	String nom_offre;
	
	public Stat_offre() {}
	
	
	
	public Stat_offre(String dates, double valeur, int id_offre, String nom_offre) {
		super();
		this.dates = dates;
		this.valeur = valeur;
		this.id_offre = id_offre;
		this.nom_offre = nom_offre;
	}

	

	public String getDates() {
		return dates;
	}



	public void setDates(String dates) {
		this.dates = dates;
	}



	public double getValeur() {
		return valeur;
	}



	public void setValeur(double valeur) {
		this.valeur = valeur;
	}



	public int getId_offre() {
		return id_offre;
	}



	public void setId_offre(int id_offre) {
		this.id_offre = id_offre;
	}



	public String getNom_offre() {
		return nom_offre;
	}



	public void setNom_offre(String nom_offre) {
		this.nom_offre = nom_offre;
	}


	
	public Stat_offre[] get_stat_offre(String periode,String dates,int idoffre,Connection c) throws Exception
	{
		Model model=new Model();
		String req="";
		if(idoffre==0) {
			req="SELECT*FROM STATISTIQUE_OFFRE_"+periode+" WHERE  DATES>='"+dates+"'";
		}else {
			req="SELECT*FROM STATISTIQUE_OFFRE_"+periode+" WHERE id_offre="+idoffre+" and DATES>='"+dates+"'";
		}
		
		Object[] oo=model.getResult(req, new Stat_offre(), c);
		Stat_offre[] val=new Stat_offre[oo.length];
		for(int i=0;i<oo.length;i++)
		{
			val[i]=(Stat_offre)oo[i];
			String date_sub=val[i].getDates().substring(0,11);
			val[i].setDates(date_sub);
		}
		return val;
	}
}
