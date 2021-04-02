package projet.web.Model;

import java.sql.Connection;
import java.util.Date;

import projet.web.Service.Credit_service;
import projet.web.Service.Money_service;
import projet.web.Service.Offre_service;
import projet.web.Service.Utilisateur_service;


public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection c=new Helper().getConnection();
		
//			Credit credit=new Credit();
//			credit.setMontant(500);
//			credit.insertCreditGenere(credit, c);
//			System.out.println("code="+credit.getCode());
			
		try {
			boolean bb=Utilisateur_service.Logout("Bearer $2a$08$PAwNLxzeHls3Zjdh9timk.vnXIIxG95jbUojM.4k1qw3sxEPoH2C2");
		}finally {
			c.close();
		}
			
			
	}

}
