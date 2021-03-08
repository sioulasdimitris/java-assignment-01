package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteHelper {
    private static SQLiteHelper sqLiteHelper = new SQLiteHelper();//singleton design pattern - instance will be created at load time
    private static Connection connection;
    private String jdbcUrl = "jdbc:sqlite:"+System.getProperty("user.dir")+"/res/blocksdb.db";//database path

    public void setConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(jdbcUrl);
    }

    public void insertToDb(Block block) throws SQLException {
        String insertQuery = "INSERT INTO blocks(hash, previoushash, data, timeStamp) values ('"+block.getHash()+"','"+block.getPreviousHash()+"','"+block.getData()+"','"+block.getTimeStamp()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(insertQuery);
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public static SQLiteHelper getSQLiteHelper(){
        return sqLiteHelper;
    }


}
