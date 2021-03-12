package Model;

import java.sql.Connection;

public class Etudiant {
	/**
	 * 
	 */
	String idetu;
	String  nom;
	String  prenom;
	String datenaissance;
	
	public Etudiant(String idetu, String nom, String prenom, String datenaissance) {
		super();
		this.idetu = idetu;
		this.nom = nom;
		this.prenom = prenom;
		this.datenaissance = datenaissance;
	}

	public static Etudiant[]getEtudiant() throws Exception
	{
		Model model=new Model();
		Connection connect=new Helper().getConnection();
		Object[] oo=model.getResult("SELECT*FROM ETUDIANT", new Etudiant(),connect );
		Etudiant[] allEtudiant=new Etudiant[oo.length];
		for(int i=0;i<oo.length;i++)
		{
			allEtudiant[i]=(Etudiant)oo[i];
		}
		connect.close();
		return allEtudiant;
	}
	
	public String getIdetu() {
		return idetu;
	}


	public void setIdetu(String idetu) {
		this.idetu = idetu;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getDatenaissance() {
		return datenaissance;
	}


	public void setDatenaissance(String datenaissance) {
		this.datenaissance = datenaissance;
	}


	
	public Etudiant() {}
	
	
	
	
}
