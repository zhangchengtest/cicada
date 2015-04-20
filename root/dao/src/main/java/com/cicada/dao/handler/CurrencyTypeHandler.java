package com.cicada.dao.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.cicada.core.model.Currency;

public class CurrencyTypeHandler extends BaseTypeHandler<Currency> {

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Currency parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getName());
	}

	@Override
	public Currency getNullableResult(ResultSet rs, String columnName) throws SQLException {
		String columnValue = rs.getString(columnName);
		return this.getCurrency(columnValue);
	}

	@Override
	public Currency getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		String columnValue = rs.getString(columnIndex);
		return this.getCurrency(columnValue);
	}

	@Override
	public Currency getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		String columnValue = cs.getString(columnIndex);
		return this.getCurrency(columnValue);
	}

	public Currency getCurrency(String columnValue) {
		return Currency.parseByName(columnValue);
	}
}