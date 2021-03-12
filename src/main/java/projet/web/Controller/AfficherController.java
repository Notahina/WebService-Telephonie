package projet.web.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.JSend;
@RestController
public class AfficherController {
	
	@GetMapping("/afficher")
	public String  afficher() throws Exception {
		JSend send=new JSend();
		send.setData("TESTER");
		send.setStatus(200);
		return "mandeha";
	}
}
