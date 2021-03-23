package projet.web.Controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projet.web.Model.JSend;
import projet.web.Model.UserAdmin;
import projet.web.Service.Utilisateur_service;

@CrossOrigin(origins="*",allowedHeaders="*")
@RestController
@RequestMapping("/admin")
public class AdminController {
	@PostMapping("/login")
	public JSend  afficher(@RequestBody UserAdmin user) throws Exception {
		JSend send=new JSend();
		try {
			send.setStatus(200);
			System.out.println("login="+user.getLogin()+"mdp=>"+user.getMdp());
			String token=Utilisateur_service.LoginAdminToken(user.getLogin(),user.getMdp());
			send.setData(token);
			send.setMessage("Mandeha");
		}catch(Exception e) {
			send.setStatus(400);
			send.setMessage(e.getMessage());
		}
		
		return send;
	}
}
