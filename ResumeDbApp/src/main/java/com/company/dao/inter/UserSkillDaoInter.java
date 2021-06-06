package com.company.dao.inter;

import com.company.entity.UserSkill;

import java.util.List;

public interface UserSkillDaoInter {

    public List<UserSkill> getAllSkillByUserId(int userId);
    
    public boolean addUserSkill(UserSkill userSkill);
    
    public boolean deleteUserSkill(int id);
    
    public boolean updateUserSkill(UserSkill userSkill);
}
