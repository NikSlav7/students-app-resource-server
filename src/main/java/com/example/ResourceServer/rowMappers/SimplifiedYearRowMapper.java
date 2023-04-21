package com.example.ResourceServer.rowMappers;

import com.example.ResourceServer.domains.Year;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimplifiedYearRowMapper implements RowMapper<Year> {
    @Override
    public Year mapRow(ResultSet rs, int rowNum) throws SQLException {
        Year year = new Year();
        year.setYearName(rs.getString("year_name"));
        return year;
    }
}
