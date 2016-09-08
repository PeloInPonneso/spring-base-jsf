package it.cabsb.faces.ldm;

import it.cabsb.model.User;
import it.cabsb.persistence.service.IUserService;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LazyUserDataModel extends LazyDataModel<User> {

	private static final long serialVersionUID = 7675179313932032957L;

	@Autowired
	private IUserService userService;
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public User getRowData(String rowKey) {
		return userService.findUserById(Long.valueOf(rowKey));
	}
	
	@Override
	public Long getRowKey(User user) {
		return user.getId();
	}
	
	@Override
	public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
		this.setPageSize(pageSize);
		this.setRowCount(userService.countUsers());
		return userService.loadUsers(first, pageSize);
	}
	
}
