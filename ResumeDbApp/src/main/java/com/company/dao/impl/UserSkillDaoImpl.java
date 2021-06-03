package com.company.dao.impl;

import com.company.dao.inter.UserSkillDaoInter;
import com.company.db.ConnMySql;
import com.company.entity.*;

import java.sql.*;
import java.util.*;

public class UserSkillDaoImpl implements UserSkillDaoInter {

    private UserSkill getUserSkill(ResultSet rs) throws Exception {
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");

        return new UserSkill(null,new User(userId),new Skill(skillId,skillName),power);
    }

    @Override
    public List<UserSkill> getAllSkillByUserId(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try {
            Connection c = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = c.prepareStatement("select " +
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
}
