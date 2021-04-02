package projet.web.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.Appel;
import projet.web.Model.Credit;
import projet.web.Model.JSend;
import projet.web.Service.Appel_service;
import projet.web.Service.Credit_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/credit")
public class CreditController {
	
	@GetMapping("/solde/{dates}")
	public JSend getsoldecredit( @PathVariable String dates,@RequestHeader(name = "Authorization") String authHeader) throws Exception {
		JSend js=new JSend();
		js.setStatus(200);
		try {
			double solde=Credit_service.get_solde_credit( dates,authHeader);
			js.setData(solde);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
	}
	
	@PostMapping("/generer/{montant}")
	public JSend generer(@PathVariable String montant,@RequestHeader(name = "Authorization") String authHeader) throws Exception{
		JSend send = new JSend();
		send.setStatus(200);
		try {
			int total=(int)Double.parseDouble(montant);
			System.out.print("montant genere"+total);
			boolean val=Credit_service.generecredit(total,authHeader);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
			
		}
		return send;
	}
	@PostMapping("/achat_credit_code")
	public JSend achat_credit_code(@RequestBody Map<String,String> args,@RequestHeader(name = "Authorization") String authHeader)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			String code=args.get("code");
			boolean val =Credit_service.achat_credit_code(code,authHeader);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage("le code que vous avez entrer est incorrect");
		}
		return send;
	}
	
	@PostMapping("/achat_credit_money")
	public JSend achat_credit_money(@RequestBody Map<String,String> args,@RequestHeader(name = "Authorization") String authHeader)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			double montant= Double.parseDouble(args.get("montant"));
			boolean val =Credit_service.achat_credit_money(montant,authHeader);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	@GetMapping("liste")
	public JSend listecredit()throws Exception{
		JSend send = new JSend();
		send.setStatus(200);
		try {
			Credit[] liste=Credit_service.getCredit();
			send.setData(liste);
		}catch(Exception e){
			send.setStatus(400);
			send.setMessage(e.getMessage());
		} return send;
	}
}
