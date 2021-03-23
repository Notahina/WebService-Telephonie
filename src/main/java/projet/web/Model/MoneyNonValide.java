package projet.web.Model;

import java.sql.Connection;

public class MoneyNonValide {
	int id_mouvement;
	int id_utilisateur;
	int types;
	String telephone;
	String date_transaction;
	double montant;
	int etat;
	public MoneyNonValide[] getMoneyNonValide(Connection c) throws Exception{
		try {
			String requete="SELECT * FROM MONEYNONVALIDE";
			Model model=new Model();
			Object[] o=model.getResult(requete,new MoneyNonValide(), c);
			MoneyNonValide[] mnv=new MoneyNonValide[o.length];
			for(int i=0;i<o.length;i++) {
				mnv[i]=(MoneyNonValide)o[i];
			}
			return mnv;
		}catch(Exception e) {
			throw e;
		}
	}
	public int getId_mouvement() {
		return id_mouvement;
	}
	public void setId_mouvement(int id_mouvement) {
		this.id_mouvement = id_mouvement;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public MoneyNonValide() {
		super();
	}
	public MoneyNonValide(int id_mouvement, int id_utilisateur, int types, String telephone, String date_transaction,
			double montant, int etat) {
		super();
		this.id_mouvement = id_mouvement;
		this.id_utilisateur = id_utilisateur;
		this.types = types;
		this.telephone = telephone;
		this.date_transaction = date_transaction;
		this.montant = montant;
		this.etat = etat;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public int getTypes() {
		return types;
	}
	public void setTypes(int types) {
		this.types = types;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getDate_transaction() {
		return date_transaction;
	}
	public void setDate_transaction(String date_transaction) {
		this.date_transaction = date_transaction;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public int getEtat() {
		return etat;
	}
	public void setEtat(int etat) {
		this.etat = etat;
	}
	
}
