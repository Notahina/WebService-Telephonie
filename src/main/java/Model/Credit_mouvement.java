package projet.web.Model;

import java.sql.Connection;

public class Credit_mouvement {
	
	int id_utilisateur;
	int types;
	double montant;
	String date_mouvement;
	
	public Credit_mouvement() {
		
	}

	
	
	public Credit_mouvement(int id_utilisateur, int types, double montant, String date_mouvement) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.types = types;
		this.montant = montant;
		this.date_mouvement = date_mouvement;
	}
	
	


	public int getId_utilisateur() {
		return id_utilisateur;
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



	public double getMontant() {
		return montant;
	}



	public void setMontant(double montant) {
		this.montant = montant;
	}



	public String getDate_mouvement() {
		return date_mouvement;
	}



	public void setDate_mouvement(String date_mouvement) {
		this.date_mouvement = date_mouvement;
	}

	public double get_solde_credit(int idUtilisateur,String dates,Connection c) throws Exception
	{
		double val=0;
		Model model=new Model();
		String req="SELECT SUM(TYPES*MONTANT) FROM CREDIT_MOUVEMENT WHERE ID_UTILISATEUR="+idUtilisateur+" AND DATE_MOUVEMENT<'"+dates+"' GROUP BY ID_UTILISATEUR";
		try
		{
			val=(double) model.getOne(req, "double", c);
		}
		catch(ClassCastException ex)
		{
			val=0;
		}
		return val;
	}

	//acheter credit par code
	public boolean acheter_credit_code(int id_utilisateur,String code,Connection c)throws Exception
	{
		//achat par code
		boolean val=false;
		Model model =new Model();
		Credit credit= new Credit().get_credit_by_code(code,c);
		if(credit!=null)
		{
			//type=entree
			int types=-1;
			double montant=credit.getMontant();
			String date_mouvement=model.getNow(c);
			Credit_mouvement cm=new Credit_mouvement(id_utilisateur,types,montant,date_mouvement);
			model.inserer("CREDIT_MOUVEMENT", null, cm, c);
			val=true;
		}
		
		return val;
	}
	
	//acheter credit par Mobile Money
	public boolean acheter_credit_mobile(int idUtilisateur,double montant,Connection c) throws Exception
	{
		boolean val=false;
		Model model=new Model();
		String dates=model.getNow(c);
		double solde=new Money_mouvement().get_solde_utilisateur(idUtilisateur,dates,c);
		if(solde>=montant)
		{
			//type=entree credit
			int types=1;
			String date_mouvement=model.getNow(c);
			Credit_mouvement cm=new Credit_mouvement(idUtilisateur,types,montant,date_mouvement);
			model.inserer("CREDIT_MOUVEMENT", null, cm,c);
			
			int id_mouvement=model.nextVal("MONEY_MOUVEMENT",c);
			Money_mouvement mm=new Money_mouvement(id_mouvement, idUtilisateur,-1,montant, date_mouvement,
					1);
			
			model.inserer("MONEY_MOUVEMENT", null, mm,c);
			val=true;
		}
		return val;
	}
	
	public boolean consommer_credit(int idUtilisateur,double depensee,String typeUtilisation,String specification,Connection c) throws Exception
	{
		Model model=new Model();
		Taux_credit tc=new Taux_credit();
		boolean val=false;
		String dates=model.getNow(c);
		double solde_credit=this.get_solde_credit(idUtilisateur, dates, c);
		double valeur_credit=tc.get_valeur_conso(depensee,typeUtilisation,specification,c);
		if(valeur_credit<=solde_credit)
		{
			int types=-1;
			Credit_mouvement cm=new Credit_mouvement(idUtilisateur,types,valeur_credit,dates);
			model.inserer("CREDIT_MOUVEMENT", null, cm, c);
			val=true;
		}
		return val;
	}
}
