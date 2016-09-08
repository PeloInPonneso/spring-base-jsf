package it.cabsb.faces.ldm;

import it.cabsb.model.Rule;
import it.cabsb.persistence.service.IRuleService;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LazyRuleDataModel extends LazyDataModel<Rule> {

	private static final long serialVersionUID = 7675179313932032957L;

	@Autowired
	private IRuleService ruleService;
	
	public void setRuleService(IRuleService ruleService) {
		this.ruleService = ruleService;
	}

	@Override
	public Rule getRowData(String rowKey) {
		return ruleService.findRuleById(Long.valueOf(rowKey));
	}
	
	@Override
	public Long getRowKey(Rule rule) {
		return rule.getId();
	}
	
	@Override
	public List<Rule> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		this.setPageSize(pageSize);
		this.setRowCount(ruleService.countRules());
		return ruleService.loadRules(first, pageSize);
	}
	
}
