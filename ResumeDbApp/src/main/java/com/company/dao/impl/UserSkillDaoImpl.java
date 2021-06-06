package com.company.dao.impl;

import com.company.dao.inter.UserSkillDaoInter;
import com.company.db.ConnMySql;
import com.company.entity.*;

import java.sql.*;
import java.util.*;

public class UserSkillDaoImpl implements UserSkillDaoInter {

    private UserSkill getUserSkill(ResultSet rs) throws Exception {
        int userId = rs.getInt("id");
        int userSkillId = rs.getInt("userSkillId");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");

        return new UserSkill(userSkillId,new User(userId),new Skill(skillId,skillName),power);
    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try {
            Connection c = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = c.prepareStatement("select " +
                    "    us.id as userSkillId," +
                    "    u.*," +
                    "    us.skill_id," +
                    "    s.name as skill_name," +
                    "    us.power " +
                    "  from " +
                    "    user_skill us " +
                    "    left join user u on us.user_id = u.id " +
                    "    left join skill s on us.skill_id = s.id " +
                    "  where " +
                    "    us.user_id=?;");
            stmt.setInt(1,userId);
            stmt.execute();
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                UserSkill u = getUserSkill(rs);
                result.add(u);
            }
        } catch( Exception e) {
            System.out.println("Select error UserSkill: " + e);
        }
        return result;
    }
    @Override
    public boolean updateUserSkill(UserSkill userSkill){
        try{
            Connection conn = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("update user_skill set power = ?, user_id = ?, skill_id = ? where id = ?");
            stmt.setInt(1, userSkill.getPower());
            stmt.setInt(2, userSkill.getUser().getId());
            stmt.setInt(3, userSkill.getSkill().getId());
            stmt.setInt(4, userSkill.getId());
            return stmt.execute();
        }catch(Exception e){
            System.out.println("Update UserSkill Error: " + e);
            return false;
        }
    }
    @Override
    public boolean addUserSkill(UserSkill userSkill){
        try{
            Connection c = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = c.prepareStatement("insert into user_skill(user_id,skill_id,power) values(?,?,?);" );
            stmt.setInt(1, userSkill.getUser().getId());
            stmt.setInt(2, userSkill.getSkill().getId());
            stmt.setInt(3, userSkill.getPower());
            return stmt.execute();
        }catch(Exception e){
            System.out.println("Insert UserSkill Error: " + e);
            return false;
        }
    }
    @Override
    public boolean deleteUserSkill(int id){
        try{
            Connection c = ConnMySql.getInstance().getConnection();
            
            PreparedStatement stmt = c.prepareStatement("delete from user_skill where id = ?");
            stmt.setInt(1, id);
            return stmt.execute();
        }catch(Exception e){
            System.out.println("Delete UserSkill Error: " + e);
            return false;
        }
    }
}
