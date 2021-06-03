package com.company.dao.impl;

import com.company.dao.inter.CountryDaoInter;
import com.company.db.ConnMySql;
import com.company.entity.Country;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl implements CountryDaoInter {
    private Country getCountry(ResultSet rs) throws Exception{
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String nationality = rs.getString("nationality");

        return new Country(id,name,nationality);
    }
    @Override
    public List<Country> getAll() {
        List<Country> result = new ArrayList<>();
        try{
            Connection c = ConnMySql.getInstance().getConnection();
            Statement stmt = c.createStatement();
            stmt.execute("select * from country");
            ResultSet rs = stmt.getResultSet();
            while (rs.next()){
                Country country = getCountry(rs);
                result.add(country);
            }
        }catch( Exception e){
            System.out.println("select country error: " + e);
        }
        return result;
    }
}
