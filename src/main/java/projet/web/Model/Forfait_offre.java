package projet.web.Model;

import java.sql.Connection;

public class Forfait_offre {
	int id_forfait_offre;
	int id_offre;
	int id_utilisation;
	double valeur;
	public void insertForfaitOffre(Forfait_offre fo,Connection c) throws Exception {
		Model model= new Model();
		fo.setId_forfait_offre(model.nextVal("FORFAIT_OFFRE", c));
		model.inserer("FORFAIT_OFFRE", null, fo,c);
	}
	public Forfait_offre() {
		super();
	}
	public Forfait_offre(int id_forfait_offre, int id_offre, int id_utilisation, double valeur) {
		super();
		this.id_forfait_offre = id_forfait_offre;
		this.id_offre = id_offre;
		this.id_utilisation = id_utilisation;
		this.valeur = valeur;
	}
	public int getId_forfait_offre() {
		return id_forfait_offre;
	}
	public void setId_forfait_offre(int id_forfait_offre) {
		this.id_forfait_offre = id_forfait_offre;
	}
	public int getId_offre() {
		return id_offre;
	}
	public void setId_offre(int id_offre) {
		this.id_offre = id_offre;
	}
	public int getId_utilisation() {
		return id_utilisation;
	}
	public void setId_utilisation(int id_utilisation) {
		this.id_utilisation = id_utilisation;
	}
	public double getValeur() {
		return valeur;
	}
	public void setValeur(double valeur) {
		this.valeur = valeur;
	}
}
