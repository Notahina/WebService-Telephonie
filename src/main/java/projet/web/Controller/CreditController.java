package projet.web.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.Appel;
import projet.web.Model.JSend;
import projet.web.Service.Appel_service;
import projet.web.Service.Credit_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/credit")
public class CreditController {
	
	@PostMapping("/achat_credit_code")
	public JSend achat_credit_code(@RequestBody Map<String,String> args)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			int id_utilisateur=new Integer(args.get("id_utilisateur"));
			String code=args.get("code");
			boolean val =Credit_service.achat_credit_code(id_utilisateur, code);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	
	@PostMapping("/achat_credit_money")
	public JSend achat_credit_money(@RequestBody Map<String,String> args)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			int id_utilisateur=new Integer(args.get("id_utilisateur"));
			double montant= Double.parseDouble(args.get("montant"));
			boolean val =Credit_service.achat_credit_money(id_utilisateur, montant);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}

}
