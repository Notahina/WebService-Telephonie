package projet.web.Model;

import java.sql.Connection;
import java.util.Date;

import projet.web.Service.Offre_service;


public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection c=new Helper().getConnection();
		try
		{
			//Etudiant etu=new Etudiant("E6","Njanah","Njanah","1999-12-02");
			//Model m=new Model();
			//m.inserer("Etudiant",null, etu,c);
			/*Double a = Math.random();
			System.out.println("rand="+a);
			String code=a.toString();
			String sub_code=code.substring(2, 16);
			System.out.println("sub_code="+sub_code);
			System.out.println("sub_code_length="+sub_code.length());*/
			Credit credit=new Credit();
			credit.setMontant(5000);
			boolean valiny=credit.insertCredit(credit, c);
			System.out.println("sub_code ="+valiny);
		}
		catch(Exception ex)
		{
			throw ex;
		}finally {
			c.close();
		}
		
	}

}
