package projet.web.Controller;

import java.sql.Connection;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.Appel;
import projet.web.Model.JSend;
import projet.web.Model.MoneyNonValide;
import projet.web.Model.Offre;
import projet.web.Service.Appel_service;
import projet.web.Service.Money_service;
import projet.web.Service.Offre_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/appel")
public class AppelController {
	@PostMapping("/appeler")
	public JSend appeler(@RequestBody Appel appel)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			boolean val =Appel_service.appeler(appel);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	@GetMapping("/historique/{idUtilisateur}/{types}/{dates}")
	public JSend getHistorique(@PathVariable int idUtilisateur, @PathVariable String types, @PathVariable String dates) throws Exception {
		JSend js=new JSend();
		js.setStatus(200);
		try {
			Appel[] get=Appel_service.historique(idUtilisateur, types, dates);
			js.setData(get);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
	}
	
}
