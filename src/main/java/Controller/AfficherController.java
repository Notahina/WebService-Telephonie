package Controller;

import org.springframework.web.bind.annotation.GetMapping;

import Model.JSend;

public class AfficherController {
	@GetMapping("afficher")
	public JSend etudiants() throws Exception {
		JSend send=new JSend();
		send.setData("TESTER");
		send.setStatus(200);
		return send;
	}
}
