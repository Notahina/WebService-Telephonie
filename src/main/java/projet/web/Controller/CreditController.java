package projet.web.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.Credit;
import projet.web.Model.JSend;
import projet.web.Model.UserAdmin;
import projet.web.Service.Credit_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/credit")
public class CreditController {
	@PostMapping("/generer/{montant}")
	public JSend  afficher(@PathVariable int montant) throws Exception {
		JSend send=  new JSend();
		send.setStatus(200);
		try {
			Credit credit=new Credit();
			credit.setMontant(montant);
			boolean valiny=Credit_service.GenereCodeCredit(credit);
			send.setData(valiny);
			
		}catch(Exception e) {
			send.setStatus(400);
		}
		return send;
	}
}
