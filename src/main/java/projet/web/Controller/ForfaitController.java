package projet.web.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.Forfait;
import projet.web.Model.JSend;
import projet.web.Model.Utilisation;
import projet.web.Service.Forfait_service;
import projet.web.Service.Utilisation_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/offreforfait")
public class ForfaitController {
	
	@GetMapping("/liste")
	public JSend getforfait()throws Exception{
		JSend send = new JSend();
		send.setStatus(200);
		try {
			Forfait[] u=Forfait_service.getForfait();
			send.setData(u);
		}catch(Exception e) {
			send.setMessage(e.getMessage());
			send.setStatus(400);
		}
		return send;
	}
}
