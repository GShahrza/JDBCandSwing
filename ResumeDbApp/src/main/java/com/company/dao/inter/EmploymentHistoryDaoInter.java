package com.company.dao.inter;

import com.company.entity.EmploymentHistory;
import com.company.entity.UserSkill;

import java.util.List;

public interface EmploymentHistoryDaoInter {
    
    public boolean addEmploymentHistory(EmploymentHistory employmentHistory);
    
    public boolean deleteEmploymentHistory(int id);
    
    public boolean updateEmploymentHistory(EmploymentHistory employmentHistory);
    
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId);
}
