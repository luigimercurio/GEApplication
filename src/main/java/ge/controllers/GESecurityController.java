package ge.controllers;

import ge.GESecurity;
import ge.models.RegisteredUser;
import ge.models.data.RegisteredUserDao;
import org.merkury.jst.JSTProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping ("")
public class GESecurityController {

	@Autowired
	protected JSTProperties env;

	@Autowired
	protected GESecurity config;

	@Autowired
	RegisteredUserDao userDao;

	@GetMapping ("register")
	public String register (Model model, CsrfToken _csrf) {
		model.addAttribute ("user", new RegisteredUser ());
		model.addAttribute ("_csrf", _csrf);
		return "html/register";
	}

	@PostMapping ("register")
	public String register (@ModelAttribute ("user") @Valid RegisteredUser user,
	                        Errors errors, Model model, CsrfToken _csrf) {
		JdbcUserDetailsManager userManager     = config.userManager ();
		PasswordEncoder        passwordEncoder = config.passwordEncoder ();

		if (userDao.existsById (user.getUsername ())) {
			model.addAttribute ("userExists", true);
			user.setPassword ("");
			model.addAttribute ("_csrf", _csrf);
			return "html/register";
		}
		if (errors.hasErrors ()) {
			user.setPassword ("");
			model.addAttribute ("_csrf", _csrf);
			return "html/register";
		}
		try {
			userManager.createUser (
					User.withUsername (user.getUsername ())
					    .password (passwordEncoder.encode (user.getPassword ()))
					    .roles ("USER")
					    .build ());
		}
		catch (Throwable t) {
			throw new Error (t);
		}
		return "redirect:/login";
	}

	@RequestMapping ("login")
	public String login (Model model,
	                     @RequestParam (required = false) String error,
	                     @RequestParam (required = false) String logout,
	                     CsrfToken _csrf) {
		model.addAttribute ("error", error);
		model.addAttribute ("logout", logout);
		model.addAttribute ("_csrf", _csrf);
		return "html/login";
	}
}
