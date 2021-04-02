package projet.web.Model;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Token {
	String id_token;
	int id_utilisateur;
	String date_expiration;
	public String BearerToken(String token) throws Exception{
		String[] bearer=token.split(" ");
		System.out.println("lentgh bearer"+bearer.length);
		return bearer[1];
	}
	public Token VerifyToken(Connection c,String idtoken)throws Exception {
		String tokenBearer=this.BearerToken(idtoken);
		Date now=new Date();
		String date=AuthService.DateToString(now);
		String requete="SELECT * FROM Token WHERE Id_token='"+tokenBearer+"' and date_expiration>'"+date+"' order by date_expiration desc ";
		Model m=new Model();
		Object[] o=m.getResult(requete, new Token(), c);
		System.out.println("length token"+o.length);
		if(o.length==0) throw new Exception("999");
		Token token=(Token) o[0];
		return token;
	}
	public Token VerifyToken(Connection c,int iduser,Date date)throws Exception {
		try {
			String datenow=AuthService.DateToString(date);
			String requete="SELECT * FROM Token WHERE ID_UTILISATEUR="+iduser+" and date_expiration>'"+datenow+"' order by date_expiration desc ";
			Model m=new Model();
			Object[] o=m.getResult(requete, new Token(), c);
			if(o.length==0) {
				return null;
			}else {
				Token token=(Token) o[0];
				return token;
			}
			
		}catch(Exception e) {
			throw e;
		}		
	}
	public void CreateToken(Connection c,int iduser)throws Exception {
		Date date = new Date();
		String idtoken=""+date.getTime()+":/:"+iduser+"%ù";
		idtoken="crypt('"+idtoken+"', gen_salt('bf', 8))";
		AuthService s=new AuthService();
		Date nextdate=s.AddHoursOfDate(date, 3);
		String expiration= AuthService.DateToString(nextdate);
		Token token=new Token(idtoken,iduser,expiration);
		System.out.println("date="+token.getDate_expiration()+"--idtoken="+token.getId_token());
		try {
			Model m=new Model();
			String requete="INSERT INTO TOKEN  VALUES ("+idtoken+","+iduser+",'"+expiration+"')";
			System.out.print(requete);
			m.Insert(c, requete);
		}catch(Exception e) {
			throw e;
		}
		
	}
	public Token() {
		
	}
	public Token(String id_token, int id_utilisateur, String date_expiration) {
		super();
		this.id_token = id_token;
		this.id_utilisateur = id_utilisateur;
		this.date_expiration = date_expiration;
	}
	public String getId_token() {
		return id_token;
	}
	public void setId_token(String id_token) {
		this.id_token = id_token;
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}
	public String getDate_expiration() {
		return date_expiration;
	}
	public void setDate_expiration(String date_expiration) {
		this.date_expiration = date_expiration;
	}
}
