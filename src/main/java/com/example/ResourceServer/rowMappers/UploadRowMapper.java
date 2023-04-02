package com.example.ResourceServer.rowMappers;

import com.example.ResourceServer.domains.Upload;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UploadRowMapper implements RowMapper<Upload> {
    @Override
    public Upload mapRow(ResultSet rs, int rowNum) throws SQLException {
        Upload upload = new Upload();


        return upload;
    }
}
