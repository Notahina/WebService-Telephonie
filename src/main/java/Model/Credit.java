package projet.web.Model;

import java.sql.Connection;

public class Credit {
	
	int id_credit;
	String code;
	double montant;
	String date_expiration;
	
	public Credit() {
		this.setDate_expiration("2040-02-02");	
	}
	
	public Credit(int id_credit, String code, double montant, String date_expiration) {
		super();
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

	public Credit get_credit_by_code(String code,Connection c) throws Exception
	{
		Model m=new Model();
		Credit val=new Credit();
		String req="SELECT*FROM CREDIT WHERE CODE='"+code+"' ";
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
	public boolean insertCredit(Credit credit,Connection c) throws Exception
	{
		Model model=new Model();
		int id=model.nextVal("CREDIT", c);
		credit.setId_credit(id);
		String code=this.genererate_code();
		credit.setCode(code);
		model.inserer("CREDIT", null, credit,c);
		return true;
	}
	

}
