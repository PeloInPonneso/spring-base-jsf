package it.cabsb.faces.mb.view;

import it.cabsb.i18n.Iso;
import it.cabsb.i18n.Iso.Country;
import it.cabsb.model.Role;
import it.cabsb.model.User;
import it.cabsb.persistence.service.IUserService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.context.i18n.LocaleContextHolder;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

@ViewScoped
@ManagedBean(name="addEditUserMB")
public class AddEditUserManagedBean implements Serializable {

	private static final long serialVersionUID = 666550442942901019L;
	
	@ManagedProperty(value="#{userService}")
	private IUserService userService;
	
	@ManagedProperty(value="#{iso}")
	private Iso iso;
	
	private List<Country> countries;
	
	private List<Role> roles;
	
	private String id;
	private List<String> authorities;
	
	private User user;
	
	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public Iso getIso() {
		return iso;
	}

	public void setIso(Iso iso) {
		this.iso = iso;
	}

	public List<Country> getCountries() {
		Locale currentLocale = LocaleContextHolder.getLocale();
		return iso.getCountries().get(currentLocale.getLanguage());
	}

	public void setCountries(List<Country> countries) {
		this.countries = countries;
	}

	public List<Role> getRoles() {
		return userService.getAllRoles();
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@PostConstruct
	public void init() {
		Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		id = requestParameterMap.get("userId");
		if(id!=null && !"".equals(id)) {
			user = userService.findUserById(Long.valueOf(id));
			List<String> authorities = new ArrayList<String>();
			for(Role role: user.getAuthorities()) {
				authorities.add(role.getId().toString());
			}
			setAuthorities(authorities);
		} else {
			user = new User();
		}
	}
	
	public String save() {
		user.setPassword(Hashing.sha256().hashString(user.getFormPasswordOne(), Charsets.UTF_8).toString());
		
		Set<Role> roles = new HashSet<Role>();
		for (String authoritiy : authorities) {
			roles.add(userService.findRoleById(Long.valueOf(authoritiy)));
		}
		user.setAuthorities(roles);
		
		FacesMessage msg = null;
		if(id!=null && !"".equals(id)) {
			userService.updateUser(user);
			msg = new FacesMessage("User Updated", user.getUsername());
		} else {
			userService.createUser(user);
			msg = new FacesMessage("User Created", user.getUsername());
		}
    	FacesContext.getCurrentInstance().addMessage(null, msg);
    	return "/control_panel/users/index.xhtml?faces-redirect=true";
	}
}
