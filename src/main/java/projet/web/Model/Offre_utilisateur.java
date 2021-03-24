package projet.web.Model;

import java.sql.Connection;
import java.util.HashMap;

public class Offre_utilisateur {
	double valeur;
	int id_offre;
	String nom_offre;
	int id_utilisateur;
	String type_offre;
	public Offre_utilisateur() {}
	public Offre_utilisateur(double valeur, int id_offre, String nom_offre, int id_utilisateur, String type_offre) {
		this.valeur = valeur;
		this.id_offre = id_offre;
		this.nom_offre = nom_offre;
		this.id_utilisateur = id_utilisateur;
		this.type_offre = type_offre;
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
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public String getType_offre() {
		return type_offre;
	}
	public void setType_offre(String type_offre) {
		this.type_offre = type_offre;
	}
	
	public Offre_utilisateur[] get_offre_appel(int idUtilisateur,Connection c) throws Exception
	{
		Model model=new Model();
		String req="SELECT*FROM OFFRE_UTILISATEUR WHERE TYPE_OFFRE='APPEL' AND ID_UTILISATEUR="+idUtilisateur+" ORDER BY DATE";
		Object[]oo=model.getResult(req, new Offre_utilisateur(), c);
		Offre_utilisateur[] ou=new Offre_utilisateur[oo.length];
		for(int i=0;i<oo.length;i++)
		{
			ou[i]=(Offre_utilisateur)oo[i];
		}
		return ou;
	}
	public double duree_with_all_offre(int idUtilisateur,String specification,Connection c) throws Exception
	{
		double duree=0;
		Offre_utilisateur[] ou=this.get_offre_appel(idUtilisateur, c);
		for(int i=0;i<ou.length;i++)
		{
			//taux=5Ar/s
			//TelmaMora=500
			//duree=500/5
			Taux_offre taux=new Taux_offre().taux_by_specification_id(ou[i].getId_offre(),specification,c);
			duree=duree+ou[i].getValeur()/taux.getValeur();
		}
		return duree;		
	}
	public HashMap<Integer,Double> share_to_offre(int idUtilisateur,double duree,String specification,Connection c) throws Exception
	{
		Model model=new Model();
		HashMap<Integer,Double> val=new HashMap<Integer,Double>();
		Offre_utilisateur[] ou=this.get_offre_appel(idUtilisateur, c);
		double dureeTemp=duree;
		int  i=0;
		while(dureeTemp>0)
		{
			double dureeOffre=new Taux_offre().get_duree_offre(ou[i].getId_offre(),ou[i].getValeur(),specification,c);
			if(duree<=dureeOffre)
			{
				dureeOffre=duree;
			}
			double valeur_conso=new Taux_offre().get_valeur_conso(ou[i].getId_offre(), dureeOffre, specification, c);
//			System.out.println("valeur_conso="+valeur_conso);
//			System.out.println("duree_offre="+dureeOffre);
			val.put(ou[i].getId_offre(), valeur_conso);
			dureeTemp=dureeTemp-dureeOffre;
			if(i==ou.length-1)
			{
				double solde_credit=new Credit_mouvement().get_solde_credit(idUtilisateur,model.getNow(c),c);
				double duree_credit=new Taux_credit().get_duree_credit("APPEL",solde_credit,specification,c);
				if(dureeTemp<=duree_credit)
				{
					duree_credit=dureeTemp;
				}
				double valeur_credit=new Taux_credit().get_valeur_conso(duree_credit,"APPEL",specification,c);
				val.put(-1, valeur_credit);
				break;
			}
			i++;
		}
		return val;
	}
}
