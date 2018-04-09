package com.springjpa.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CustomerRepositoryImpl implements PermissionRetrieval {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<String> retrievePermissions(int roleId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String queryString = "SELECT prmsns.descr\n" + "FROM roles_prmsns\n"
				+ "INNER JOIN prmsns ON prmsns.perm_id = roles_prmsns.perm_id\n"
				+ "WHERE roles_prmsns.perm_id = prmsns.perm_id AND roles_prmsns.role_id = ?";

		return jdbcTemplate.query(queryString, new RowMapper<String>() {
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString(1);
			}
		}, roleId);

	}

}
