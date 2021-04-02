package projet.web.Controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.Forfait_offre;
import projet.web.Model.JSend;
import projet.web.Model.MoneyNonValide;
import projet.web.Model.Money_mouvement;
import projet.web.Service.Credit_service;
import projet.web.Service.Money_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/money")
public class MoneyController {	
	@PostMapping("")
	public JSend InsertForfait(@RequestBody Forfait_offre[]forfait,@RequestHeader(name = "Authorization") String authHeader) throws Exception{
		JSend js=new JSend();
		js.setStatus(200);	
		try {
			
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
	}
	@GetMapping("/nonvalide")
	public JSend GetDepotAValider(@RequestHeader(name = "Authorization") String authHeader) throws Exception {
		JSend js=new JSend();
		js.setStatus(200);
		try {
			
			MoneyNonValide[] get=Money_service.getMoneyNonValide(authHeader);
			js.setData(get);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
	}
	
	@PutMapping("/valide/{idmouvement}")
	public JSend SetValidation(@PathVariable String idmouvement,@RequestHeader(name = "Authorization") String authHeader)throws Exception{
		System.out.print("token === "+authHeader);
		JSend js=new JSend();
		js.setStatus(200);
		try {
			int id=Integer.parseInt(idmouvement);
			Boolean valiny=Money_service.Validation(id,authHeader);
			js.setData(valiny);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
		
	}
	
	@PostMapping("/deposer")
	public JSend deposer(@RequestBody Map<String,String> args,@RequestHeader(name = "Authorization") String authHeader)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			System.out.println("okk mety");
			double montant= Double.parseDouble(args.get("montant"));
			System.out.println("montant "+montant);
			String mdp=args.get("mdp").toString();
			boolean val =Money_service.deposer(montant,mdp,authHeader);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	
	@PostMapping("/retirer")
	public JSend retirer(@RequestBody Map<String,String> args,@RequestHeader(name = "Authorization") String authHeader)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			int id_utilisateur=new Integer(args.get("id_utilisateur"));
			double montant= Double.parseDouble(args.get("montant"));
			boolean val =Money_service.retirer(id_utilisateur, montant,authHeader);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	
	@GetMapping("/solde")
	public JSend getSolde(@RequestHeader(name = "Authorization") String authHeader) throws Exception {
		JSend js=new JSend();
		js.setStatus(200);
		try {
			
			double solde=Money_service.getSolde(authHeader);
			js.setData(solde);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
	}
}
 