package com.company.dao.impl;

import java.sql.Date;

import com.company.entity.Country;
import com.company.entity.User;
import com.company.dao.inter.UserDaoInter;
import com.company.db.ConnMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDaoInter{

    private User getUser(ResultSet rs) throws Exception {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String profileDescription = rs.getString("profile_description");
        String address = rs.getString("address");
        int nationalityId = rs.getInt("nationality_id");
        int birthPlaceId = rs.getInt("birthplace_id");
        String nationalityStr = rs.getString("nationality");
        String birthPlaceStr = rs.getString("birthplace");
        Date birthDate = rs.getDate("birthdate");

        Country nationality = new Country(nationalityId,null,nationalityStr);
        Country birthPlace = new Country(birthPlaceId,birthPlaceStr,null);

        return new User(id,name,surname,email,phone,profileDescription,address,birthDate,nationality,birthPlace);
    }
    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
       try (Connection c = ConnMySql.getInstance().getConnection() ){
           Statement stmt = c.createStatement();
           stmt.execute("select " +
                   "    u.*," +
                   "    n.nationality ," +
                   "    c.name as birthplace " +
                   "from user u " +
                   "left join country n on u.nationality_id = n.id " +
                   "left join country c on u.birthplace_id = c.id ");
           ResultSet rs = stmt.getResultSet();

           while (rs.next()) {
               User u = getUser(rs);
               result.add(u);
           }
       } catch( Exception e) {
               System.out.println("Select error: " + e);
           }
        return result;
    }

    @Override
    public User getById(int userId) {
        User result = null;
        try {
            Connection c = ConnMySql.getInstance().getConnection();
            Statement stmt = c.createStatement();
            stmt.execute("select " +
                            "    u.*," +
                            "    n.nationality ," +
                            "    c.name as birthplace " +
                            "from user u " +
                            "left join country n on u.nationality_id = n.id " +
                            "left join country c on u.birthplace_id = c.id  where u.id = " + userId);

            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                result = getUser(rs);
            }

        } catch( Exception e) {
            System.out.println("Select error: " + e);
        }
        return result;
    }

    @Override
    public boolean updateUser(User u) {

        try {
            Connection c = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = c.prepareStatement("update user  set name = ?,surname = ?,email = ?,phone = ?,profile_description = ?,address = ?, birthdate = ?, birthplace_id = ?, nationality_id = ? where id = ?");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, u.getProfileDescription());
            stmt.setString(6, u.getAddress());
            stmt.setDate(7, u.getBirthDate());
            stmt.setInt(8, u.getBirthPlace().getId());
            stmt.setInt(9, u.getNationality().getId());
            stmt.setInt(10,u.getId());
            return stmt.execute();
        }catch (Exception e){
            System.out.println("Update Error: " + e);
            return false;
        }
    }

    @Override
    public boolean addUser(User u) {
        try {
            Connection c = ConnMySql.getInstance().getConnection();
            PreparedStatement stmt = c.prepareStatement("insert into user(name,surname,email,phone,profile_description,address,birthdate) values (?,?,?,?,?,?,?) ");
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5,u.getProfileDescription());
            stmt.setString(6,u.getAddress());
            stmt.setDate(7, u.getBirthDate());
            return stmt.execute();
        }catch (Exception e){
            System.out.println("Insert Error: " + e);
            return false;
        }
    }

    @Override
    public boolean removeUser(int id) {
        try {
            Connection c = ConnMySql.getInstance().getConnection();
            Statement stmt = c.createStatement();
            return stmt.execute("delete from user where id = " + id);
        }catch (Exception e){
            System.out.println("Delete Error: " + e);
            return false;
        }
    }

}
