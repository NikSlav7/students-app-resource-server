package com.example.ResourceServer.rowMappers;

import com.example.ResourceServer.domains.UploadWithAvg;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UploadWithAvgMapper implements RowMapper<UploadWithAvg> {
    private final JdbcTemplate jdbcTemplate;

    public UploadWithAvgMapper(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public UploadWithAvg mapRow(ResultSet rs, int rowNum) throws SQLException {
        UploadWithAvg uploadWithAvg = new UploadWithAvg();
        uploadWithAvg.setUploadDate(rs.getDate("upload_date"));
        Double avg = jdbcTemplate.queryForObject(" WITH temp AS (SELECT * FROM mark WHERE upload_upload_id = ?) SELECT AVG(mark_avg) FROM temp;",
                Double.class, rs.getString("upload_id"));
        uploadWithAvg.setOverallAvg(avg);
        uploadWithAvg.setUploadId(rs.getString("upload_id"));
        return uploadWithAvg;
    }
}
