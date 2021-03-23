package projet.web.Model;

public class Appel {
	int id_appel;
	String types;
	String numero1;
	String login;
	String numero2;
	int duree;
	String date_appel;
	
	public Appel[] getHistoriqueAppel() {
		return null;
	}
	public Appel() {
	}
	
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public int getId_appel() {
		return id_appel;
	}
	public void setId_appel(int id_appel) {
		this.id_appel = id_appel;
	}
	public String getTypes() {
		return types;
	}
	public void setTypes(String types) {
		this.types = types;
	}
	public String getNumero1() {
		return numero1;
	}
	public void setNumero1(String numero1) {
		this.numero1 = numero1;
	}
	public String getNumero2() {
		return numero2;
	}
	public void setNumero2(String numero2) {
		this.numero2 = numero2;
	}
	public int getDuree() {
		return duree;
	}
	public void setDuree(int duree) {
		this.duree = duree;
	}
	public String getDate_appel() {
		return date_appel;
	}
	public void setDate_appel(String date_appel) {
		this.date_appel = date_appel;
	}
	
}
