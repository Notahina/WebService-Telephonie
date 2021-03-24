package projet.web.Model;

import java.sql.Connection;

public class Taux_offre {
	

	int id_taux;
	int id_offre;
	double valeur;
	String specification;
	
	public Taux_offre() {}

	public Taux_offre(int id_taux, int id_offre, double valeur, String specification) {
		super();
		this.id_taux = id_taux;
		this.id_offre = id_offre;
		this.valeur = valeur;
		this.specification = specification;
	}

	public int getId_taux() {
		return id_taux;
	}

	public void setId_taux(int id_taux) {
		this.id_taux = id_taux;
	}

	public int getId_offre() {
		return id_offre;
	}

	public void setId_offre(int id_offre) {
		this.id_offre = id_offre;
	}

	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}
	
	public Taux_offre taux_by_specification_id(int idOffre,String specification,Connection c) throws Exception
	{
		Taux_offre val=null;
		Model model=new Model();
		String req="SELECT*FROM TAUX_OFFRE WHERE ID_OFFRE="+idOffre+" AND SPECIFICATION='"+specification+"'";
		Object[] oo=model.getResult(req, new Taux_offre(), c);
		if(oo.length>0)
		{
			val=(Taux_offre)oo[0];
		}
		return val;
	}
	//depensee=60s
	//specification=meme(operateur)
	//valeur en Ariary
	//100 s= 100 Ariay
	public double get_valeur_conso(int idOffre,double depensee,String Specification,Connection c) throws Exception
	{
		//taux=5Ar/s
		//1s--->5Ar
		//60s--->?
		//valeur=depensee*valeur_taux;
		double valeur=0;
		Taux_offre tauxOffre=this.taux_by_specification_id(idOffre, Specification, c);
		valeur=depensee*tauxOffre.getValeur();
		return valeur;
		
	}
	
	public double get_duree_offre(int idOffre,double valeur,String specification,Connection c) throws Exception
	{
		Taux_offre taux=this.taux_by_specification_id(idOffre, specification, c);
		double duree=valeur/taux.getValeur();
		return duree;
	}

	
	}
