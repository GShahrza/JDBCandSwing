package com.company.dao.impl;

import com.company.dao.inter.EmploymentHistoryDaoInter;
import com.company.db.ConnMySql;
import com.company.entity.EmploymentHistory;
import com.company.entity.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmploymentHistoryDaoImpl implements EmploymentHistoryDaoInter {

    private EmploymentHistory getEmploymentHistory(ResultSet rs) throws Exception {
        String header = rs.getString("header");
        String jobDescription = rs.getString("jobDescription");
        Date beginDate = rs.getDate("begin_date");
        Date endDate = rs.getDate("end_date");
        int userId = rs.getInt("user_id");

        return new EmploymentHistory(null,header,beginDate,endDate,jobDescription,new User(userId));
    }

    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        List<EmploymentHistory> result = new ArrayList<>();
        try {
            Connection c = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = c.prepareStatement("select * from employment_history where user_id = ?");
            stmt.setInt(1,userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                EmploymentHistory u = getEmploymentHistory(rs);
                result.add(u);
            }
        } catch( Exception e) {
            System.out.println("Select error UserSkill: " + e);
        }
        return result;
    }
}
