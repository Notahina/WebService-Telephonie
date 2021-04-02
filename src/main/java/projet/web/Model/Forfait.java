package projet.web.Model;

import java.sql.Connection;

public class Forfait {
	int id_forfait_offre;
	String nom_offre;
	String nom_utilisation;
	double valeur;
	String unite;
	public  Forfait[] getAllforfait(Connection c) throws Exception {
		try {
			String requete="SELECT * From Forfait";
			Model m=new Model();
			Object[] o= m.getResult(requete, new Forfait(), c);
			Forfait[] f=new Forfait[o.length];
			for(int i=0;i<o.length;i++){
				f[i]=(Forfait)o[i];
			}
			return f;
		}catch(Exception e) {
			throw e;
		}
	}
	public Forfait(int id_forfait_offre, String nom_offre, String nom_utilisation, double valeur, String unite) {
		super();
		this.id_forfait_offre = id_forfait_offre;
		this.nom_offre = nom_offre;
		this.nom_utilisation = nom_utilisation;
		this.valeur = valeur;
		this.unite = unite;
	}
	public Forfait() {
		
	}
	public int getId_forfait_offre() {
		return id_forfait_offre;
	}
	public void setId_forfait_offre(int id_forfait_offre) {
		this.id_forfait_offre = id_forfait_offre;
	}
	public String getNom_offre() {
		return nom_offre;
	}
	public void setNom_offre(String nom_offre) {
		this.nom_offre = nom_offre;
	}
	public String getNom_utilisation() {
		return nom_utilisation;
	}
	public void setNom_utilisation(String nom_utilisation) {
		this.nom_utilisation = nom_utilisation;
	}
	public double getValeur() {
		return valeur;
	}
	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
	public String getUnite() {
		return unite;
	}
	public void setUnite(String unite) {
		this.unite = unite;
	}
}
