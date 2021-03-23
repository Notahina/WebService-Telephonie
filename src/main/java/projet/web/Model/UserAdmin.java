package projet.web.Model;

import java.sql.Connection;

public class UserAdmin {
	int id_admin;
	String login;
	String mdp;
	public UserAdmin() {
		
	}
	public UserAdmin(int id_admin, String login, String mdp) {
		super();
		this.id_admin = id_admin;
		this.login = login;
		this.mdp = mdp;
	}
	public UserAdmin getAdminUser(Connection c,String login,String mdp)throws Exception {
		try {
			Model m=new Model();
			String requete="SELECT * FROM USERADMIN where login='"+login+"' and mdp=crypt('"+mdp+"',mdp)";
			Object[] oo=m.getResult(requete, new UserAdmin(), c);
			System.out.print("taille="+oo.length);
			if(oo.length==0) throw new Exception("Le login ou le mot de pass est incorrect");
			UserAdmin u=(UserAdmin) oo[0];
			//System.out.print("Iduser="+u.getNom());
			return u;
		}catch(Exception e) {
			throw e;
		}
	}
	public int getId_admin() {
		return id_admin;
	}
	public void setId_admin(int id_admin) {
		this.id_admin = id_admin;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
}
