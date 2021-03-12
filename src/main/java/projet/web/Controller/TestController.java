package projet.web.Controller;

import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import Model.JSend;
import Model.Etudiant;

@RestController
@RequestMapping("/lycee")
public class TestController {
	@GetMapping("/etudiant")
	public JSend etudiants() throws Exception {
		Etudiant  etu=new Etudiant();
		Etudiant[] val=new Etudiant[0];
		 val=Etudiant.getEtudiant();

		JSend jsend = new JSend();
		jsend.setStatus(200);
		jsend.setData(val);
		return jsend;
	}
	
	@PostMapping("etudiant")
	public JSend  etudiants(@RequestBody Etudiant etudiant) throws Exception{
		JSend jsend = new JSend();
		jsend.setStatus(200);
		Etudiant data = null;
		try {
			data = etudiant;
			jsend.setData(data);
		}catch(Exception e) {
			jsend.setStatus(400);
		}
		return jsend;
	}
	
}
