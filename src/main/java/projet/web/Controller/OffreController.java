package projet.web.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.JSend;
import projet.web.Model.Offre;
import projet.web.Model.Stat_offre;
import projet.web.Service.Offre_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/offre")
public class OffreController {
	@PostMapping("/insert")
	public JSend insert(@RequestBody Offre offre)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			boolean val =Offre_service.insertOffre(offre);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	@PostMapping("/statistique")
	public JSend  afficher(@RequestBody Offre offre) throws Exception {
		System.out.println("Date="+offre.getDate_offre());
		JSend send = new JSend();
		send.setStatus(200);
		try {
			Stat_offre[] data=Offre_service.get_stat(offre.getType_offre(), offre.getDate_offre(),offre.getId_offre());
			send.setData(data);
		}catch(Exception e) {
			send.setStatus(400);
			send.setData(e.getMessage());
		}
		return send;
	}
	@GetMapping("/liste")
	public JSend getoffre()throws Exception{
		JSend send = new JSend();
		send.setStatus(200);
		try {
			Offre[] offre=Offre_service.getOffre();
			send.setData(offre);
		}catch(Exception e) {
			send.setMessage(e.getMessage());
			send.setStatus(400);
		}
		return send;
	}
}
