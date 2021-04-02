package projet.web.Model;

import java.sql.Connection;

public class Utilisation {
	int id_utilisation;
	String nom_utilisation;
	String unite;
	
	public Utilisation() {
		
	}
	public int getId_utilisation() {
		return id_utilisation;
	}
	public void setId_utilisation(int id_utilisation) {
		this.id_utilisation = id_utilisation;
	}
	public String getNom_utilisation() {
		return nom_utilisation;
	}
	public void setNom_utilisation(String nom_utilisation) {
		this.nom_utilisation = nom_utilisation;
	}
	public String getUnite() {
		return unite;
	}
	public void setUnite(String unite) {
		this.unite = unite;
	}
	public Utilisation(int id_utilisation, String nom_utilisation, String unite) {
		super();
		this.id_utilisation = id_utilisation;
		this.nom_utilisation = nom_utilisation;
		this.unite = unite;
	}
	public Utilisation[] getUtilisation(Connection c) throws Exception {
		Model model=new Model();
		String requete="SELECT * FROM UTILISATION";
		Object[] oo=model.getResult(requete, new Utilisation(),c );
		Utilisation[] u=new Utilisation[oo.length];
		for(int i=0;i<u.length;i++) {
			u[i]=(Utilisation) oo[i];
		}
		return u;
	}
	
}
