package it.cabsb.faces.mb.view;

import it.cabsb.model.Rule;
import it.cabsb.persistence.service.IRuleService;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.drools.io.ResourceFactory;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ViewScoped
@ManagedBean(name="addEditRuleMB")
public class AddEditRuleManagedBean implements Serializable {

	private static final long serialVersionUID = 666550442942901019L;
	
	private static Logger _log = LoggerFactory.getLogger(AddEditRuleManagedBean.class);
	
	@ManagedProperty(value="#{ruleService}")
	private IRuleService ruleService;
	
	private String id;
	private UploadedFile file;
	
	private Rule rule;
	
	public IRuleService getRuleService() {
		return ruleService;
	}

	public void setRuleService(IRuleService ruleService) {
		this.ruleService = ruleService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Rule getRule() {
		return rule;
	}

	public void setRule(Rule rule) {
		this.rule = rule;
	}

	@PostConstruct
	public void init() {
		Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		id = requestParameterMap.get("ruleId");
		if(id!=null && !"".equals(id)) {
			rule = ruleService.findRuleById(Long.valueOf(id));
		} else {
			rule = new Rule();
		}
	}
	
	public String save() {
		FacesMessage msg = null;
		StringBuffer drl = null;
		try {
    		SpreadsheetCompiler sc = new SpreadsheetCompiler();
    		drl = new StringBuffer(sc.compile(file.getInputstream(), InputType.XLS));
    		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
    		kbuilder.add(ResourceFactory.newReaderResource((Reader) new StringReader(drl.toString())), ResourceType.DRL);
    		if(kbuilder.hasErrors()) {
    			for (KnowledgeBuilderError error : kbuilder.getErrors()) {
    				_log.error(error.getMessage());
    			}
    			msg = new FacesMessage("Rule Updated error", rule.getName());
    			FacesContext.getCurrentInstance().addMessage(null, msg);
    			return null;
    		}
    		rule.setContent(drl.toString());
    		if(id!=null && !"".equals(id)) {
    			ruleService.updateRule(rule);
    			msg = new FacesMessage("Rule Updated", rule.getName());
    		} else {
    			ruleService.createRule(rule);
    			msg = new FacesMessage("Rule Created", rule.getName());
    		}
    		return "/control_panel/rules/index.xhtml?faces-redirect=true";
		} catch (Exception e) {
			_log.error(e.getMessage());
			msg = new FacesMessage("Rule Updated error", rule.getName());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return null;
		}
	}
}
