package projet.web.Model;

import java.sql.Connection;
import java.util.Date;

public class Utilisateur {
	int id_utilisateur;
	String nom;
	String prenom;
	String login;
	String telephone;
	String mdp;
	String types;
	public void CheckMotdepasse(Connection c,int iduser,String mdp) throws Exception{
		Model model=new Model();
		String requete="SELECT * FROM UTILISATEUR where id_utilisateur="+iduser+" and mdp=crypt('"+mdp+"',mdp)";
		Object[] oo=model.getResult(requete, new Utilisateur(),c );
		System.out.print("taille="+oo.length);
		if(oo.length==0) throw new Exception("Votre mot de passe est incorrect");
	}
	public void CreateUser(Connection c,Utilisateur user)throws Exception {
		Boolean b=false;
		if(c==null) {
			c=new Helper().getConnection();
			b=true;
		}
		try {
			String mdp=user.getMdp();
			mdp="crypt('"+mdp+"', gen_salt('bf', 8))";
			user.setMdp(mdp);
			user.setLogin(c, user.getLogin());
			Model m=new Model();
			String requete="INSERT INTO UTILISATEUR  VALUES (nextval('user_seq'),'"+user.getNom()+"','"+user.getPrenom()+"','"+user.getLogin()+"','"+user.getTelephone()+"',"+user.getMdp()+")";
			System.out.println(requete);
			m.Insert(c, requete);
		}catch(Exception e) {
			throw e;
		}finally {
			if(b==true) c.close();
		}
	}
	public String GetTokenUser(Connection c,int iduser)  throws Exception{
		Boolean b=false;
		if(c==null) {
			c=new Helper().getConnection();
			b=true;
		}
		try {
			Date datenow =new Date();
			Token token =new Token();
			Token token2 =new Token();
			token =token.VerifyToken(c, iduser, datenow);
			System.out.print("token" +token);
			String idtoken="";
			if(token!=null) {
				System.out.println("Existe Token =>"+token.getId_token());
				idtoken= token.getId_token();
			}else if(token==null) {			
				token2.CreateToken(c, iduser);
				token2 =token2.VerifyToken(c, iduser, datenow);
				System.out.println("Not Existe and Create token =>"+token2.getId_token());
				idtoken= token2.getId_token();
			}		
			return idtoken;
		}catch(Exception e) {
			throw e;
		}finally {
			if(b==true) c.close();
		}
	}
	public void setLogin(Connection c,String login)throws Exception {
		Model model=new Model();
		String requete="SELECT * FROM UTILISATEUR where login='"+login+"'";
		Object[] oo=model.getResult(requete, new Utilisateur(),c );
		System.out.print("taille="+oo.length);
		if(oo.length>0) throw new Exception("Ce login est deja utiliser");
		this.login=login;
	}
	public Utilisateur getUserAdmin(Connection c,String login,String pwd) throws Exception{
		try {
			Model model=new Model();
			String requete="SELECT * FROM UTILISATEUR where login='"+login+"' and mdp=crypt('"+pwd+"',mdp) and types='ADMIN'";
			Object[] oo=model.getResult(requete, new Utilisateur(),c );
			System.out.print("taille="+oo.length);
			if(oo.length==0) throw new Exception("Le login ou le mot de pass est incorrect");
			Utilisateur u=(Utilisateur) oo[0];
			System.out.print("Iduser="+u.getNom());
			return u;
		}catch(Exception e) {
			throw e;
		}
	}
	public Utilisateur getUtilisateur(Connection c,String login,String pwd) throws Exception{
		try {
			Model model=new Model();
			String requete="SELECT * FROM UTILISATEUR where login='"+login+"' and mdp=crypt('"+pwd+"',mdp) ";
			Object[] oo=model.getResult(requete, new Utilisateur(),c );
			System.out.print("taille="+oo.length);
			if(oo.length==0) throw new Exception("Le login ou le mot de pass est incorrect");
			Utilisateur u=(Utilisateur) oo[0];
			System.out.print("Iduser="+u.getNom());
			return u;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public Utilisateur get_by_num(Connection c,String numero) throws Exception{
		try {
			Model model=new Model();
			String requete="SELECT * FROM UTILISATEUR where telephone='"+numero+"'";
			Object[] oo=model.getResult(requete, new Utilisateur(),c );
			System.out.print("taille="+oo.length);
			if(oo.length==0) throw new Exception("numero inconnu");
			Utilisateur u=(Utilisateur) oo[0];
			
			return u;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public Utilisateur get_by_id(int idUtilisateur,Connection c) throws Exception
	{
		Utilisateur val=null;
		Model model=new Model();
		String req="SELECT*FROM UTILISATEUR WHERE ID_UTILISATEUR="+idUtilisateur;
		Object[]oo=model.getResult(req, new Utilisateur(), c);
		if(oo.length>0)
		{
			val=(Utilisateur)oo[0];
		}
		return val;
	}
	public Utilisateur() {
		
	}
	public int getId_utilisateur() {
		return id_utilisateur;
	}
	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
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
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public Utilisateur(int id_utilisateur, String nom, String prenom, String login, String telephone, String mdp,
			String types) {
		super();
		this.id_utilisateur = id_utilisateur;
		this.nom = nom;
		this.prenom = prenom;
		this.login = login;
		this.telephone = telephone;
		this.mdp = mdp;
		this.types = types;
	}
	
}
