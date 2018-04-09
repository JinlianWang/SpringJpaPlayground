package com.springjpa.repo;

import java.util.List;

public interface PermissionRetrieval {
	List<String> retrievePermissions(int roleId);
}
