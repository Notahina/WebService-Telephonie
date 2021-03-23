package projet.web.Model;


import java.util.HashMap;

public class JSend {
	int status;
	Object data;
	String message;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static HashMap<Integer,String> error(){
		HashMap<Integer,String> error = new HashMap();
		error.put(400,"Bad Request");
		error.put(401, "Unauthorized");
		error.put(403, "");
		return error;
	}
	
	public String getStatusPropriety() {
		HashMap<Integer,String> error = this.error();
		String value = error.get(this.status);
		return value;
	}
}
