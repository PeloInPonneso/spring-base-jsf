package it.cabsb.faces.mb.request;

import it.cabsb.model.User;
import it.cabsb.persistence.service.IUserService;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

@RequestScoped
@ManagedBean(name="userMB")
public class UserManagedBean implements Serializable {
	
	private static final long serialVersionUID = 5022496589541294228L;
	
	@ManagedProperty(value="#{lazyUserDataModel}")
	private LazyDataModel<User> lazyModel;

	@ManagedProperty(value="#{userService}")
	private IUserService userService;

	public LazyDataModel<User> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<User> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
    
    public void remove(User user) {
    	userService.deleteUser(user.getId());
    	FacesMessage msg = new FacesMessage("User Removed", user.getUsername());
    	FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
