package projet.web.Model;

import java.sql.Connection;

public class Solde_forfait {
	
	int id_utilisateur;
	int id_utilisation;
	double valeur_forfait;
	String unite;
	String nom_utilisation;
	public Solde_forfait() {}
	
	public Solde_forfait(int id_utilisateur, int id_utilisation, double valeur_forfait, String unite,
			String nom_utilisation) {
		this.id_utilisateur = id_utilisateur;
		this.id_utilisation = id_utilisation;
		this.valeur_forfait = valeur_forfait;
		this.unite = unite;
		this.nom_utilisation = nom_utilisation;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public int getId_utilisation() {
		return id_utilisation;
	}

	public void setId_utilisation(int id_utilisation) {
		this.id_utilisation = id_utilisation;
	}

	public double getValeur_forfait() {
		return valeur_forfait;
	}

	public void setValeur_forfait(double valeur_forfait) {
		this.valeur_forfait = valeur_forfait;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}

	public String getNom_utilisation() {
		return nom_utilisation;
	}

	public void setNom_utilisation(String nom_utilisation) {
		this.nom_utilisation = nom_utilisation;
	}
	
	public Solde_forfait[] get_solde_forfait(int idUtilisateur,int idOffre,String daty,Connection c) throws Exception
	{
		Model model= new Model();
		String req="select*from get_solde_forfait_join("+idUtilisateur+","+idOffre+",'"+daty+"')";
		Object[] oo= model.getResult(req, new Solde_forfait(), c) ;
		Solde_forfait[] sf=new Solde_forfait[oo.length];
		for(int i=0;i<sf.length;i++)
		{
			sf[i]=(Solde_forfait) oo[i];
		}
		return sf;
	}
	
}
