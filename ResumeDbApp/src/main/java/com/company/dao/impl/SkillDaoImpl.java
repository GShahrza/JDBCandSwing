package com.company.dao.impl;

import com.company.dao.inter.SkillDaoInter;
import com.company.db.ConnMySql;
import com.company.entity.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl implements SkillDaoInter {
    private Skill getSkill(ResultSet rs) throws Exception{
        int id = rs.getInt("id");
        String skill = rs.getString("name");

        return new Skill(id,skill);
    }
    @Override
    public List<Skill> getAll() {
        List<Skill> result = new ArrayList<>();
        try{
            Connection c = ConnMySql.getInstance().getConnection();
            Statement stmt = c.createStatement();
            stmt.execute("select * from skill");
            ResultSet rs = stmt.getResultSet();
            while(rs.next()){
                Skill s = getSkill(rs);
                result.add(s);
            }
        }catch(Exception e){
            System.out.println("Select Skill Error: " + e);
        }
        return result;
    }

    @Override
    public boolean addSkill(Skill skill) {
        try{
            Connection conn = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement("insert into skill(name) values(?)",Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, skill.getName());
            boolean b = stmt.execute();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
              if(generatedKeys.next()){
                    skill.setId(generatedKeys.getInt(1));
                }  
            return b;
        }catch(Exception e){
            System.out.println("Insert Skill Error: " + e);
            return false;
        }
    }
    
}
