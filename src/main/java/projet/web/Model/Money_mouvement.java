package projet.web.Model;

import java.sql.Connection;
import java.sql.Statement;

public class Money_mouvement {
	int id_mouvement;
	int id_utilisateur;
	int types;
	double montant;
	String date_transaction;
	int etat;
	
	public Money_mouvement() {}

	
	
		
	public Money_mouvement(int id_mouvement, int id_utilisateur, int types, double montant, String date_transaction,int etat) {
		super();
		this.id_mouvement = id_mouvement;
		this.id_utilisateur = id_utilisateur;
		this.types = types;
		this.montant = montant;
		this.date_transaction = date_transaction;
		this.etat = etat;
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




	public String getDate_transaction() {
		return date_transaction;
	}




	public void setDate_transaction(String date_transaction) {
		this.date_transaction = date_transaction;
	}




	public int getEtat() {
		return etat;
	}




	public void setEtat(int etat) {
		this.etat = etat;
	}




	public Money_mouvement get_by_id(int id,Connection c) throws Exception
	{
		Money_mouvement val=null;
		Model model=new Model();
		String req="SELECT*FROM MONEY_MOUVEMENT WHERE ID_MOUVEMENT="+id;
		Object[] oo=model.getResult(req, new Money_mouvement(), c);
		if(oo.length>0)
		{
			val=(Money_mouvement)oo[0];
		}
		return val;
	}
	public boolean update_all_transaction(Connection c)  throws Exception {
		String update="Update money_mouvement set etat =1 where types=1 and etat=0 ";
		Statement state= c.createStatement();
		state.execute(update);
		return true;
	}
	public boolean valider_transaction(int id_mouvement,Connection c) throws Exception
	{
		Model model=new Model();
		boolean val=false;
		if(id_mouvement==0) {
			 val=this.update_all_transaction(c);
		}else {
			Money_mouvement money=get_by_id(id_mouvement,c);
			money.setEtat(1);
			model.modifier("MONEY_MOUVEMENT", money,c);
			val=true;
		}
		return val;
	}

	//RECUPERER TOUS LES TRANSACTIONS
	public Money_mouvement[] get_all_transaction(Connection c) throws Exception
	{
		Model m=new Model();
		if(c==null)
		{
			c=new Helper().getConnection();
		}
		String req="SELECT*FROM MONEY_MOUVEMENT";
		Object[]oo=m.getResult(req, new Money_mouvement(), c);
		Money_mouvement[] mouv=new Money_mouvement[oo.length];
		for(int i=0;i<oo.length;i++ )
		{
			mouv[i]=(Money_mouvement)oo[i];
		}
		return mouv;
	}
	//TYPES
		//ENTREE=1
		//SORTIE=-1
	public double get_solde_utilisateur(int idUtilisateur,String dates,Connection c) throws Exception
	{
		Model model=new Model();
		double val=0;
		
		String req="SELECT SUM(MONTANT*TYPES) FROM MONEY_MOUVEMENT WHERE ID_UTILISATEUR="+idUtilisateur+" AND DATE_TRANSACTION<='"+dates+"' AND ETAT=1 GROUP BY ID_UTILISATEUR";
		val=(double) model.getOne(req, "double" , c);
		return val;
	}
	
	///////DEPOT
	
	public boolean deposer(int idUtilisateur,double montant,Connection c) throws Exception
	{
		Model model=new Model();
		int idMouvement=model.nextVal("MONEY_MOUVEMENT", c);
		int types=1;
		String dates=model.getNow(c);
		int etat=0;
		Money_mouvement mm=new Money_mouvement(idMouvement, idUtilisateur, types, montant, dates,etat);
		model.inserer("MONEY_MOUVEMENT", null, mm, c);
		return true;
		
	}
	
	public boolean retirer(int idUtilisateur,double montant,Connection c) throws Exception
	{
		boolean val=false;
		Model model=new Model();
		String dates=model.getNow(c);
		double solde=this.get_solde_utilisateur(idUtilisateur, dates, c);
		if(solde>=montant)
		{
			int idMouvement=model.nextVal("MONEY_MOUVEMENT", c);
			int types=1;
			int etat=1;
			Money_mouvement mm=new Money_mouvement(idMouvement, idUtilisateur, types, montant, dates,etat);
			model.inserer("MONEY_MOUVEMENT", null, mm, c);
			val=true;
		}
		return val;
	}
}
