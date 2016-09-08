package it.cabsb.faces.ldm;

import it.cabsb.model.Role;
import it.cabsb.persistence.service.IUserService;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LazyRoleDataModel extends LazyDataModel<Role> {

	private static final long serialVersionUID = 7675179313932032957L;

	@Autowired
	private IUserService userService;
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public Role getRowData(String rowKey) {
		return userService.findRoleById(Long.valueOf(rowKey));
	}
	
	@Override
	public Long getRowKey(Role role) {
		return role.getId();
	}
	
	@Override
	public List<Role> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		this.setPageSize(pageSize);
		this.setRowCount(userService.countRoles());
		return userService.loadRoles(first, pageSize);
	}
	
}
