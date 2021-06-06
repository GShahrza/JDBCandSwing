package com.company.db;


import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
public class ConnMySql {

    private static ConnMySql instance;
    private Connection connection;

    public static ConnMySql getInstance() {
        if(instance == null ) {
            instance = new ConnMySql();
        }
        return instance;
    }
    public ConnMySql() {
        try{
            connection = DriverManager
                    .getConnection(
                        "jdbc:mysql://localhost:3306/resume",
                        "root",
                        "123456"
                    );
        }catch(SQLException e){
            System.out.println("Error Connection: " + e);
        }
    }
}
