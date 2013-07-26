package WebGuestBook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Users {
    public static String getName (Integer userid) throws SQLException {
        String nickname = null;
        PreparedStatement statement = DBconnector.connection.prepareStatement("SELECT nickname FROM users WHERE id = ?");
        statement.setString(1, userid.toString());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            nickname = result.getString("name");
        }
        result.close();
        statement.close();
        return nickname;
    }
    public static Integer getUserID (String nickname) throws SQLException {
        Integer userid = null;
        PreparedStatement statement = DBconnector.connection.prepareStatement("SELECT id FROM users WHERE nickname = ?");
        statement.setString(1, nickname);
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            userid = result.getInt("id");
        }
        result.close();
        statement.close();
        return userid;
    }
    public static Boolean addUser (String nickname, Integer hashPass) throws SQLException {
        Boolean rez = Boolean.FALSE;
        Statement statement = DBconnector.connection.createStatement();
        if (0 != statement.executeUpdate("INSERT INTO users (nickname, hashpass) VALUES ('" +
                nickname + "', " + hashPass.toString() + ")", 1)) {
            rez = Boolean.TRUE;
        }
        statement.close();
        return rez;
    }
    public static Boolean setPass (Integer userid, Integer oldHashPass, Integer newHashPass) throws SQLException {
        Boolean rez = Boolean.FALSE;
        Statement updater = DBconnector.connection.createStatement();
        if (0 != updater.executeUpdate("UPDATE users SET hashpass = " +
                newHashPass.toString() + " WHERE id = " +
                userid.toString() + "AND hashpass = " + oldHashPass.toString())) {
            rez = Boolean.TRUE;
        }
        updater.close();
        return rez;
    }

    public static Integer checkPass (String nickname, Integer hashPass) throws SQLException {
        Integer userid = null;
        PreparedStatement statement = DBconnector.connection.prepareStatement("SELECT id FROM users WHERE nickname = ?" +
                " AND hashPass = ?");
        statement.setString(1, nickname);
        statement.setString(2, hashPass.toString());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            userid = result.getInt("id");
        }
        result.close();
        statement.close();
        return userid;
    }
}

