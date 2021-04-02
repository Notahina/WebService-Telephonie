package projet.web.Model;

import java.sql.Connection;

public class Offre_mouvement {
	
	int id_mouvement;
	int id_offre;
	double valeur_offre;
	int id_utilisateur;
	int type_mouvement;
	String date_mouvement;
	
	public Offre_mouvement() {}

	
	
	public Offre_mouvement(int id_mouvement, int id_offre, double valeur_offre, int id_utilisateur, int type_mouvement,
			String date_mouvement) {
		super();
		this.id_mouvement = id_mouvement;
		this.id_offre = id_offre;
		this.valeur_offre = valeur_offre;
		this.id_utilisateur = id_utilisateur;
		this.type_mouvement = type_mouvement;
		this.date_mouvement = date_mouvement;
	}

	public int getId_mouvement() {
		return id_mouvement;
	}



	public void setId_mouvement(int id_mouvement) {
		this.id_mouvement = id_mouvement;
	}



	public int getId_offre() {
		return id_offre;
	}



	public void setId_offre(int id_offre) {
		this.id_offre = id_offre;
	}



	public double getValeur_offre() {
		return valeur_offre;
	}



	public void setValeur_offre(double valeur_offre) {
		this.valeur_offre = valeur_offre;
	}



	public int getId_utilisateur() {
		return id_utilisateur;
	}



	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}



	public int getType_mouvement() {
		return type_mouvement;
	}



	public void setType_mouvement(int type_mouvement) {
		this.type_mouvement = type_mouvement;
	}



	public String getDate_mouvement() {
		return date_mouvement;
	}



	public void setDate_mouvement(String date_mouvement) {
		this.date_mouvement = date_mouvement;
	}



	public boolean acheter_offre(int idUtilisateur,int idOffre,String typeAchat,String dates,Connection c) throws Exception
	{
		boolean val=false;
		Model model=new Model();
		String req="acheter_offre("+idUtilisateur+","+idOffre+",'"+typeAchat+"','"+dates+"')";
		val=(boolean)model.getOne(req, "boolean", c);
		return val;
	}
	public double get_solde_offre(int idUtilisateur,int idOffre,Connection c) throws Exception
	{
		Model model=new Model();
		double val=0;
		String req="SELECT SUM(VALEUR_OFFRE*TYPE_MOUVEMENT) AS VALEUR FROM OFFRE_VALIDE WHERE ID_UTILISATEUR="+idUtilisateur+" AND ID_OFFRE="+idOffre+" GROUP BY ID_UTILISATEUR";
		Object ob=model.getOne(req, "double", c);
		try
		{
			val=(Double)model.getOne(req, "double", c);
		}
		catch(ClassCastException ex)
		{
			val=0;
		}
		if(val<0)
		{
			val=0;
		}
		return val;
	}
	
	public boolean acheter_offre_credit(int idUtilisateur,int idOffre,Connection c) throws Exception
	{
		boolean val=false;
		Model model=new Model();
		String dates=model.getNow(c);
		double solde_credit=new Credit_mouvement().get_solde_credit(idUtilisateur,dates,c);
		
		Offre offre=new Offre().get_by_id(idOffre,c);
		if(offre.getPrix_offre()<=solde_credit)
		{
			//offre mouvement
			int id_mouvement=model.nextVal("Offre_mouvement", c);
			int type_mouvement=1;
			double prix=offre.getPrix_offre();
			Offre_mouvement om=new Offre_mouvement(id_mouvement,idOffre,prix,idUtilisateur,type_mouvement,dates);
			model.inserer("OFFRE_MOUVEMENT", null, om, c);
			
			//credit mouvement
			int types_mouvement_credit=-1;
			double montant=offre.getPrix_offre();
			Credit_mouvement cm=new Credit_mouvement(idUtilisateur,types_mouvement_credit,montant,dates);
			model.inserer("CREDIT_MOUVEMENT", null,cm, c);
			val =true;
		}
		return val;
	}
	
	public boolean acheter_offre_mobile(int idUtilisateur,int idOffre,Connection c) throws Exception
	{
		boolean val=false;
		Model model=new Model();
		String dates=model.getNow(c);
		double solde_money=new Money_mouvement().get_solde_utilisateur(idUtilisateur,dates,c);
		
		Offre offre=new Offre().get_by_id(idOffre,c);
		
		if(offre.getPrix_offre()<=solde_money)
		{
			//offre mouvement
			int id_mouvement=model.nextVal("Offre_mouvement", c);
			int type_mouvement=1;
			double prix=offre.getPrix_offre();
			Offre_mouvement om=new Offre_mouvement(id_mouvement,idOffre,prix,idUtilisateur,type_mouvement,dates);
			model.inserer("OFFRE_MOUVEMENT", null, om, c);
			
			//money mouvement
			int id_money_mouvement=model.nextVal("MONEY_MOUVEMENT", c);
			int types=-1;
			double montant=offre.getPrix_offre();
			int etat=1;
			
			Money_mouvement mm=new Money_mouvement(id_money_mouvement,idUtilisateur,types,montant,dates,etat);
			model.inserer("MONEY_MOUVEMENT", null,mm, c);
			val=true;
		}
		return val;
	}
	//consommer_offre
	//offre=TELMA MORA
	//idUtilisateur=1
	//SPECIFICATION=MEME_OPERATEUR
	//depensee=60 s
	public boolean consommer_offre(int idUtilisateur,int idOffre,String specification,double depensee,Connection c) throws Exception
	{
		boolean val=false;
		Model model=new Model();
		double solde_offre=get_solde_offre(idUtilisateur,idOffre,c);
		Taux_offre to=new Taux_offre();
		int id_mouvement=model.nextVal("OFFRE_MOUVEMENT", c);
		double valeur_offre=to.get_valeur_conso(idOffre,depensee,specification,c);
		int type_mouvement=-1;
		String date_mouvement=model.getNow(c);
		Offre_mouvement om=new Offre_mouvement(id_mouvement,idOffre, valeur_offre,idUtilisateur,type_mouvement,date_mouvement);
		if(solde_offre>=valeur_offre)
		{
			model.inserer("OFFRE_MOUVEMENT", null, om, c);
			val=true;
		}
		return val;
	}
	
}
