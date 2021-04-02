package projet.web.Controller;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.Appel;
import projet.web.Model.Forfait_offre;
import projet.web.Model.Getsoldeforfait;
import projet.web.Model.JSend;
import projet.web.Model.Offre;
import projet.web.Model.Stat_offre;
import projet.web.Service.Appel_service;
import projet.web.Service.Credit_service;
import projet.web.Service.Offre_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/offre")
public class OffreController {
	//ADMIN
	@PostMapping("/insertforfait")
	public JSend insertForfaitoffre(@RequestBody Forfait_offre[] fo,@RequestHeader(name = "Authorization") String token) {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			System.out.println("Tailler "+fo.length);
			boolean valiny =Offre_service.insertForfait(fo,token);
			send.setData(valiny);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
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
	@GetMapping("/statistique/{periode}/{dates}/{id_offre}")
	public JSend ge_statistique(@RequestHeader(name = "Authorization") String token,@PathVariable String periode, @PathVariable String dates, @PathVariable int id_offre) throws Exception {
		JSend js=new JSend();
		js.setStatus(200);
		try {
			Stat_offre[] get= Offre_service.get_stat(periode, dates, id_offre);
			js.setData(get);
		}catch(Exception e) {
			js.setStatus(400);
			js.setData(e.getMessage());
		}
		return js;
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
	
	@PostMapping("/achat_offre_credit")
	public JSend achat_offre_credit(@RequestBody Map<String,String> args,@RequestHeader(name = "Authorization") String token)throws Exception {
		JSend send = new JSend();
		send.setStatus(200);
		try {
			System.out.println("okkkkkkkkkkkk");
			int id_offre= new Integer(args.get("id_offre"));
			String type_achat=args.get("type_achat");
			String dates=args.get("dates");
			System.out.println("datesss"+dates);
			boolean val =Offre_service.acheter_offre(token, id_offre, type_achat, dates);
			send.setData(val);
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		return send;
	}
	
	@GetMapping("/offre_utilisateur/{dates}")
	public JSend offre_utilisateur(@PathVariable String dates,@RequestHeader(name = "Authorization") String token)throws Exception{
		JSend send = new JSend();
		send.setStatus(200);
		try {
			Offre[] offre=Offre_service.offre_of_utilisateur(token, dates);
			send.setData(offre);
		}catch(Exception e) {
			send.setMessage(e.getMessage());
			send.setStatus(400);
		}
		return send;
	}
	@GetMapping("/soldeforfait/{dates}/{idoffre}")
	public JSend soldeforfait(@RequestHeader(name = "Authorization") String token,@PathVariable String dates,@PathVariable String idoffre) {
		JSend send=new JSend();
		try {
			send.setStatus(200);
			Getsoldeforfait[] get=Offre_service.soldeforfaituser(token, dates, idoffre);
			send.setData(get);
		}catch(Exception e) {
			send.setStatus(400);
		}
		return send;
	}
//	@PostMapping("/achat_offre_money")
//	public JSend achat_offre_money(@RequestBody Map<String,String> args)throws Exception {
//		JSend send = new JSend();
//		send.setStatus(200);
//		try {
//			int id_utilisateur=new Integer(args.get("id_utilisateur"));
//			int id_offre= new Integer(args.get("id_offre"));
//			boolean val =Offre_service.achat_offre_money(id_utilisateur, id_offre);
//			send.setData(val);
//		}catch(Exception e) {
//			send.setStatus(400);
//			send.setMessage(e.getMessage());
//		}
//		return send;
//	}
}
