package projet.web.Model;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

public class Appel {
	int id_appel;
	String numero1;
	String numero2;
	double duree;
	String date_appel;
	

	public Appel[] get_historique(int idUtilisateur,String types,String dates,Connection c) throws Exception
	{
		Model model=new Model();
		String req="SELECT historique_appel("+idUtilisateur+","+types+","+dates+")";
		
		Object[] ob=model.getResult(req,new Appel(), c);
		Appel[] appel=new Appel[ob.length];
		for(int i=0;i<ob.length;i++)
		{
			appel[i]=(Appel)ob[i];
		}
		return appel;
	}
	
	
	
	public boolean appeler(int idUtilisateur,String numero2,double duree,String dates,Connection c) throws Exception
	{
		boolean val=false;
		String req="appeler("+idUtilisateur+","+numero2+","+duree+","+dates+")";
		return val;
	}
	
	public double duree_appel_effectuable(int idUtilisateur,int numero2,String daty,Connection c) throws Exception
	{
		Model model=new Model();
		String req="duree_credit_forfait("+idUtilisateur+","+numero2+",'"+daty+"')";
		double duree=(double)model.getOne(req, "double", c);
		return duree;
	}
	public Appel() {}
	
	public Appel(int id_appel, String numero1, String numero2, double duree, String date_appel) {
		super();
		this.id_appel = id_appel;
		this.numero1 = numero1;
		this.numero2 = numero2;
		this.duree = duree;
		this.date_appel = date_appel;
	}

	public int getId_appel() {
		return id_appel;
	}

	public void setId_appel(int id_appel) {
		this.id_appel = id_appel;
	}

	public String getNumero1() {
		return numero1;
	}

	public void setNumero1(String numero1) {
		this.numero1 = numero1;
	}

	public String getNumero2() {
		return numero2;
	}

	public void setNumero2(String numero2) {
		this.numero2 = numero2;
	}

	public double getDuree() {
		return duree;
	}

	public void setDuree(double duree) {
		this.duree = duree;
	}

	public String getDate_appel() {
		return date_appel;
	}

	public void setDate_appel(String date_appel) {
		this.date_appel = date_appel;
	}

	///
	//numero1= numero miantso
	//numero2= numero antsoina
	
	//OFFRE->CREDIT
	
	public String get_specification(String numero)
	{
		String specification="AUTRE_OPERATEUR";
		if(numero.startsWith("034"))
		{
			specification="MEME_OPERATEUR";
		}
		return specification;
	}
	
	public boolean consommer_offre_credit(int idUtilisateur,double duree,String specification,Connection c) throws Exception
	{
		HashMap<Integer,Double> conso_offre=new Offre_utilisateur().share_to_offre(idUtilisateur,duree,specification,c);
		
		for(Map.Entry<Integer, Double> entry : conso_offre.entrySet())
		{
			int idOffre=entry.getKey();
			double consommation=entry.getValue();
			if(idOffre!=-1 && consommation>0)
			{
				double duree_offre=new Taux_offre().get_duree_offre(idOffre, consommation, specification, c);
				boolean status_offre=new Offre_mouvement().consommer_offre(idUtilisateur,idOffre,specification,duree_offre,c);
			}
			if(idOffre==-1 && consommation>0)
			{
				System.out.println("tafiditra");
				double duree_credit=new Taux_credit().get_duree_credit("APPEL", consommation, specification, c);
				boolean status_credit=new Credit_mouvement().consommer_credit(idUtilisateur,duree_credit,"APPEL",specification,c);
			}	
		}
		return true;
	}
	
	public boolean appeler(int idUtilisateur,String numero2,double duree,Connection c) throws Exception
	{
		boolean val=false;
		Model model=new Model();
		String specification=get_specification(numero2);
		Utilisateur utilisateur=new Utilisateur().get_by_id(idUtilisateur, c);
		
		//duree appel effectuable
		double duree_effectuable=0;
		double appel_effectuable_offre=new Offre_utilisateur().duree_with_all_offre(idUtilisateur, specification, c);
		double appel_effectuable_credit=new Taux_credit().duree_appel_effectuable(idUtilisateur,specification,c);
		duree_effectuable=appel_effectuable_offre+appel_effectuable_credit;
		//

		int id_appel=model.nextVal("APPEL", c);
		String numero1=utilisateur.getTelephone();
		String dates=model.getNow(c);
		Appel appel=new Appel(id_appel,numero1,numero2,duree,dates);
		Offre_utilisateur[] ou=new Offre_utilisateur().get_offre_appel(idUtilisateur, c);
		if(duree_effectuable>=duree)
		{
			consommer_offre_credit(idUtilisateur,duree,specification,c);
			model.inserer("APPEL", null, appel, c);
			val=true;
		} 
		return val;
	}
}
