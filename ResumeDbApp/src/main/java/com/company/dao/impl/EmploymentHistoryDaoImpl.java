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
        int id = rs.getInt("id");
        String header = rs.getString("header");
        String jobDescription = rs.getString("job_description");
        Date beginDate = rs.getDate("begin_date");
        Date endDate = rs.getDate("end_date");
        int userId = rs.getInt("user_id");

        return new EmploymentHistory(id,header,beginDate,endDate,jobDescription,new User(userId));
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

    @Override
    public boolean addEmploymentHistory(EmploymentHistory employmentHistory) {
        try{
            Connection conn = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into employment_history(header,begin_date,end_date,job_description,user_id) values(?,?,?,?,?);");
            stmt.setString(1, employmentHistory.getHeader());
            stmt.setDate(2, employmentHistory.getBeginDate());
            stmt.setDate(3, employmentHistory.getEndDate());
            stmt.setString(4, employmentHistory.getJobDescription());
            stmt.setInt(5, employmentHistory.getUser().getId());
            return stmt.execute();
        }catch(Exception e){
            System.out.println("Insert EmploymentHistory Error: " + e);
            return false;
        }
    }
    @Override
    public boolean deleteEmploymentHistory(int id) {
        try{
            Connection conn= ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("delete from employment_history where id = ?");
            stmt.setInt(1, id);
            return stmt.execute();
        }catch(Exception e){
            System.out.println("Delete Employment History Error : " + e);
            return false;
        }
    }
    @Override
    public boolean updateEmploymentHistory(EmploymentHistory employmentHistory) {
        try{
            Connection conn = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("update employment_history set header = ?, begin_date = ?, end_date = ?, job_description = ?, user_id = ? where id = ?");
            stmt.setString(1, employmentHistory.getHeader());
            stmt.setDate(2, employmentHistory.getBeginDate());
            stmt.setDate(3, employmentHistory.getEndDate());
            stmt.setString(4, employmentHistory.getJobDescription());
            stmt.setInt(5, employmentHistory.getUser().getId());
            stmt.setInt(6, employmentHistory.getId());
            return stmt.execute();
                   
        }catch(Exception e){
            System.out.println("Insert EmploymentHistory Error: " + e);
            return false;
        }
    }
}
