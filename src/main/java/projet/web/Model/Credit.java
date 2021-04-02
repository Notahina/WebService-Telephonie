package projet.web.Model;

import java.sql.Connection;
import java.util.Date;

public class Credit {
	
	int id_credit;
	String code;
	double montant;
	String date_expiration;
	
	public Credit() {
		
	}
	
	public Credit(int id_credit, String code, double montant, String date_expiration) {
		this.id_credit = id_credit;
		this.code = code;
		this.montant = montant;
		this.date_expiration = date_expiration;
	}

	

	public int getId_credit() {
		return id_credit;
	}

	public void setId_credit(int id_credit) {
		this.id_credit = id_credit;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getDate_expiration() {
		return date_expiration;
	}

	public void setDate_expiration(String date_expiration) {
		this.date_expiration = date_expiration;
	}
	public Credit[] get_credit(Connection c) throws Exception
	{
		Model m=new Model();
		
		String req="SELECT*FROM CREDIT limit 15";
		Object[] oo=m.getResult(req, new Credit(), c);
		Credit[] val=new Credit[oo.length];
		for(int i=0;i<val.length;i++)
		{
			val[i]=(Credit)oo[i];
		}
		return val;
	}
	public Credit get_credit_by_code(String code,Connection c) throws Exception
	{
		Model m=new Model();
		Credit val=new Credit();
		String req="SELECT*FROM CREDIT WHERE CODE='"+code+"' AND DATE_EXPIRATION>NOW()";
		Object[] oo=m.getResult(req, new Credit(), c);
		if(oo.length>0)
		{
			val=(Credit)oo[0];
		}
		return val;
	}
	public String genererate_code()
	{
		Double a = Math.random();
		String code=a.toString();
		String sub_code=code.substring(2, 16);
		return sub_code;
	}
	public boolean insertCreditGenere(Credit credit,Connection c) throws Exception
	{
		Date date=new Date();
		String dte=AuthService.DateToString(date);
		Model model=new Model();
		int id=model.nextVal("CREDIT", c);
		credit.setId_credit(id);
		String code=this.genererate_code();
		credit.setCode(code);
		credit.setDate_expiration(dte);
		System.out.println("crediittttttt");
		model.inserer("CREDIT", null, credit, c);
		System.out.println("insert");
		return true;
	}
	

}
