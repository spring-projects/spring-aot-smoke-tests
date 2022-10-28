package com.example.jdbc.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

record Author(long id, String name) {
	enum Mapper implements RowMapper<Author> {

		INSTANCE;

		@Override
		public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Author(rs.getInt("id"), rs.getString("name"));
		}

	}
}
