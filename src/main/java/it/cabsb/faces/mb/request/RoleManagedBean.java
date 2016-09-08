package it.cabsb.faces.mb.request;

import it.cabsb.model.Role;
import it.cabsb.persistence.service.IUserService;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;

@RequestScoped
@ManagedBean(name="roleMB")
public class RoleManagedBean implements Serializable {

	private static final long serialVersionUID = 6026289573531967248L;
	
	@ManagedProperty(value="#{lazyRoleDataModel}")
	private LazyDataModel<Role> lazyModel;

	@ManagedProperty(value="#{userService}")
	private IUserService userService;

	private String authority;
	
	public LazyDataModel<Role> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Role> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public void onRowEdit(RowEditEvent event) {
		Role role = (Role) event.getObject();
        FacesMessage msg = new FacesMessage("Role Edited", role.getAuthority());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        userService.updateRole(role);
    }
     
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Role) event.getObject()).getAuthority());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void remove(Role role) {
    	userService.deleteRole(role.getId());
    	FacesMessage msg = new FacesMessage("Role Removed", role.getAuthority());
    	FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
	public void save(ActionEvent event) {
		Role role = new Role();
		role.setAuthority(authority);
		userService.createRole(role);
		FacesMessage msg = new FacesMessage("Role Created", authority);
    	FacesContext.getCurrentInstance().addMessage(null, msg);
    	setAuthority(null);
	}
}
