package Model;

import java.sql.Connection;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Connection c=new Helper().getConnection();
		try
		{
			Etudiant etu=new Etudiant("E6","Njanah","Njanah","1999-12-02");
			Model m=new Model();
			m.inserer("Etudiant",null, etu,c);
		}
		catch(Exception ex)
		{
			ex.getStackTrace();
		}finally {
			c.close();
		}
		
	}

}
