package it.cabsb.faces.mb.request;

import it.cabsb.model.Rule;
import it.cabsb.persistence.service.IRuleService;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;

@RequestScoped
@ManagedBean(name="ruleMB")
public class RuleManagedBean implements Serializable {
	
	private static final long serialVersionUID = 7036055808546080650L;

	@ManagedProperty(value="#{lazyRuleDataModel}")
	private LazyDataModel<Rule> lazyModel;

	@ManagedProperty(value="#{ruleService}")
	private IRuleService ruleService;
	
	public LazyDataModel<Rule> getLazyModel() {
		return lazyModel;
	}

	public void setLazyModel(LazyDataModel<Rule> lazyModel) {
		this.lazyModel = lazyModel;
	}

	public IRuleService getRuleService() {
		return ruleService;
	}

	public void setRuleService(IRuleService ruleService) {
		this.ruleService = ruleService;
	}
	
	public void remove(Rule rule) {
		ruleService.deleteRule(rule.getId());
    	FacesMessage msg = new FacesMessage("Rule Removed", rule.getName());
    	FacesContext.getCurrentInstance().addMessage(null, msg);
    }
	
}
