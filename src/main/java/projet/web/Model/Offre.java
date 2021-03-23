package projet.web.Model;

import java.sql.Connection;
import java.util.Date;

public class Offre {
	int id_offre;
	String nom_offre;
	String date_offre;
	int validite;
	String type_offre;
	double prix;
	
	public Offre() {}
	
	public Offre(int id_offre, String nom_offre, String date_offre, int validite, String type_offre, double prix) {
		super();
		this.id_offre = id_offre;
		this.nom_offre = nom_offre;
		this.date_offre = date_offre;
		this.validite = validite;
		this.type_offre = type_offre;
		this.prix = prix;
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



	public String getDate_offre() {
		return date_offre;
	}



	public void setDate_offre(String date_offre) {
		this.date_offre = date_offre;
	}



	public int getValidite() {
		return validite;
	}



	public void setValidite(int validite) {
		this.validite = validite;
	}



	public String getType_offre() {
		return type_offre;
	}



	public void setType_offre(String type_offre) {
		this.type_offre = type_offre;
	}



	public double getPrix() {
		return prix;
	}



	public void setPrix(double prix) {
		this.prix = prix;
	}

	public Boolean insertOffre(Offre offre ,Connection c) throws Exception {
		try {
			Date date=new Date();
			offre.setDate_offre(AuthService.DateToString(date));
			Model model= new Model();
			offre.setId_offre(model.nextVal("OFFRE", c));
			model.inserer("OFFRE", null, offre,c);
			return true;
		}catch(Exception e) {
			throw e;
		}
	}

	public Offre get_by_id(int idOffre,Connection c) throws Exception
	{
		Offre val=null;
		Model model=new Model();
		String req="SELECT*FROM OFFRE WHERE ID_OFFRE="+idOffre;
		Object[]oo=model.getResult(req, new Offre(), c);
		if(oo.length>0)
		{
			val=(Offre)oo[0];
		}
		return val;
	}
	
	public Offre[] get_all_offre(Connection c) throws Exception
	{
		Model m=new Model();
		if(c==null)
		{
			c=new Helper().getConnection();
		}
		String req="SELECT*FROM OFFRE";
		Object[]oo=m.getResult(req, new Offre(), c);
		Offre[] offre=new Offre[oo.length];
		for(int i=0;i<oo.length;i++ )
		{
			offre[i]=(Offre)oo[i];
		}
		return offre;
	}
	
	
}
