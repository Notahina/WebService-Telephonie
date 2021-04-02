package projet.web.Model;

public class Getsoldeforfait {
	int id_utilisateur;
	int id_utilisation;
	double valeur_forait;
	String unite;
	String nom_utilisation;
	public Getsoldeforfait() {
		
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
	public double getValeur_forait() {
		return valeur_forait;
	}
	public void setValeur_forait(double valeur_forait) {
		this.valeur_forait = valeur_forait;
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
	public Getsoldeforfait(int id_utilisateur, int id_utilisation, double valeur_forait, String unite,
			String nom_utilisation) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.id_utilisation = id_utilisation;
		this.valeur_forait = valeur_forait;
		this.unite = unite;
		this.nom_utilisation = nom_utilisation;
	}
}
