package projet.web.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.JSend;
import projet.web.Model.MoneyNonValide;
import projet.web.Model.Money_mouvement;
import projet.web.Service.Money_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/money")
public class MoneyController {	
	@GetMapping("/nonvalide")
	public JSend GetDepotAValider() throws Exception {
		JSend js=new JSend();
		js.setStatus(200);
		try {
			
			MoneyNonValide[] get=Money_service.getMoneyNonValide();
			js.setData(get);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
	}
	
	@PutMapping("/valide/{idmouvement}")
	public JSend SetValidation(@PathVariable int idmouvement)throws Exception{
		JSend js=new JSend();
		js.setStatus(200);
		try {
			Boolean valiny=Money_service.Validation(idmouvement);
			js.setData(valiny);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
		
	}
}
