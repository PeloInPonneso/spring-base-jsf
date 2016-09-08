package it.cabsb.faces.mb.session;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@SessionScoped
@ManagedBean(name="applicationBaseMB")
public class ApplicationBaseManagedBean implements Serializable {

	private static final long serialVersionUID = 7086465655679321327L;
	
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@PostConstruct
	public void init() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    String username = auth.getName();
	    setUsername(username);
	}
}
