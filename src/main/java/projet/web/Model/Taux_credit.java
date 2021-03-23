package projet.web.Model;

import java.sql.Connection;

public class Taux_credit {
	
	int id_taux;
	double valeur;
	String type_utilisation;
	String specification;
	
	Taux_credit(){}

	
	
	public Taux_credit(int id_taux, double valeur, String type_utilisation, String specification) {
		super();
		this.id_taux = id_taux;
		this.valeur = valeur;
		this.type_utilisation = type_utilisation;
		this.specification = specification;
	}
	
	


	public int getId_taux() {
		return id_taux;
	}



	public void setId_taux(int id_taux) {
		this.id_taux = id_taux;
	}



	public double getValeur() {
		return valeur;
	}



	public void setValeur(double valeur) {
		this.valeur = valeur;
	}



	public String getType_utilisation() {
		return type_utilisation;
	}



	public void setType_utilisation(String type_utilisation) {
		this.type_utilisation = type_utilisation;
	}



	public String getSpecification() {
		return specification;
	}



	public void setSpecification(String specification) {
		this.specification = specification;
	}



	public Taux_credit taux_by_specification_id(String typeUtilisation,String specification,Connection c) throws Exception
	{
		Taux_credit val=null;
		Model model=new Model();
		String req="SELECT*FROM TAUX_CREDIT WHERE TYPE_UTILISATION='"+typeUtilisation+"' AND  SPECIFICATION='"+specification+"'";
		Object[] oo=model.getResult(req, new Taux_credit(), c);
		if(oo.length>0)
		{
			val=(Taux_credit)oo[0];
		}
		return val;
	}
	//depensee=60s
	//specification=meme(operateur)
	//valeur en Ariary
	//100 s= 100 Ariay
	public double get_valeur_conso(double depensee,String typeUtilisation,String Specification,Connection c) throws Exception
	{
		//taux=5Ar/s
		//1s--->5Ar
		//60s--->?
		//valeur=depensee*valeur_taux;
		double valeur=0;
		Taux_credit tauxCredit=this.taux_by_specification_id(typeUtilisation,Specification, c);
		valeur=depensee*tauxCredit.getValeur();
		return valeur;
	}

}
