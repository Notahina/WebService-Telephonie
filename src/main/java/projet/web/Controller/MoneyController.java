package projet.web.Controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.JSend;
import projet.web.Model.MoneyNonValide;
import projet.web.Model.Money_mouvement;
import projet.web.Service.Credit_service;
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
	
	@PostMapping("/deposer")
	public JSend deposer(@RequestBody Map<String,String> args)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			int id_utilisateur=new Integer(args.get("id_utilisateur"));
			double montant= Double.parseDouble(args.get("montant"));
			boolean val =Money_service.deposer(id_utilisateur, montant);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	
	@PostMapping("/retirer")
	public JSend retirer(@RequestBody Map<String,String> args)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			int id_utilisateur=new Integer(args.get("id_utilisateur"));
			double montant= Double.parseDouble(args.get("montant"));
			boolean val =Money_service.retirer(id_utilisateur, montant);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	
	@GetMapping("/solde/{id_utilisateur}/{dates}")
	public JSend getSolde(@PathVariable int id_utilisateur, @PathVariable String dates) throws Exception {
		JSend js=new JSend();
		js.setStatus(200);
		try {
			
			double solde=Money_service.getSolde(id_utilisateur, dates);
			js.setData(solde);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
	}
}
