package WebGuestBook;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Messages {
    public static Boolean addMessage(Message msg) throws SQLException {
        Boolean rez = Boolean.FALSE;
        Statement statement = DBconnector.connection.createStatement();
        if (0 != statement.executeUpdate("INSERT INTO messages (threadid, datetime, userid, title, text) VALUES (" +
                ((msg.threadid == 0)?"id":msg.threadid.toString()) + ", '" + msg.datetime.toString() + "', " +
                msg.userid.toString() + ", '" + msg.title.trim() + "', '" + msg.text.trim() + "')")) {
            rez = Boolean.TRUE;
        }
        statement.close();
        return rez;
    }
    public static Boolean setMessage(Message msg) throws SQLException {
        Boolean rez = Boolean.FALSE;
        Statement statement = DBconnector.connection.createStatement();
        if (0 != statement.executeUpdate("UPDATE messages SET text = '" +
                msg.text + "' WHERE id = " + msg.id.toString())) {
            rez = Boolean.TRUE;
        }
        statement.close();
        return rez;
    }
    public static Boolean delMessage(Integer id) throws SQLException {
        Boolean rez = Boolean.FALSE;
        Statement statement = DBconnector.connection.createStatement();
        if (0 != statement.executeUpdate("DELETE FROM messages WHERE id = " + id.toString())) {
            rez = Boolean.TRUE;
        }
        statement.close();
        return rez;
    }
    public static Message getMessage(Integer id) throws SQLException {
        Message msg = null;
        PreparedStatement statement = DBconnector.connection.prepareStatement("SELECT * FROM messages, users " +
                "WHERE messages.id = ? AND messages.userid = users.id");
        statement.setString(1, id.toString());
        ResultSet result = statement.executeQuery();
        while (result.next()) {
            msg = new Message(result.getInt("messages.id"), result.getInt("messages.threadid"),
                    result.getTimestamp("messages.datetime"), result.getInt("messages.userid"),
                    result.getString("users.nickname"), result.getString("messages.title"),
                    result.getString("messages.text"));
        }
        result.close();
        statement.close();
        return msg;
    }
    public static ArrayList<Message> getMessages() throws SQLException {
        ArrayList<Message> msg = new ArrayList<Message>();
        PreparedStatement statement = DBconnector.connection.prepareStatement("SELECT * FROM messages, users " +
                "WHERE messages.threadid = 0 " +
                "AND messages.userid = users.id ORDER BY datetime DESC");
        ResultSet result = statement.executeQuery();
        int count = 0;
        while (result.next()) {
            msg.add(count++, new Message(result.getInt("messages.id"), result.getInt("messages.threadid"),
                    result.getTimestamp("messages.datetime"), result.getInt("messages.userid"),
                    result.getString("users.nickname"), result.getString("messages.title"),
                    result.getString("messages.text")));
        }
        result.close();
        statement.close();
        return msg;
    }
    public static ArrayList<Message> getMessagesUser(Integer userid) throws SQLException {
        ArrayList<Message> msg = new ArrayList<Message>();
        PreparedStatement statement = DBconnector.connection.prepareStatement("SELECT * FROM messages, users " +
                "WHERE messages.userid = ? AND messages.userid = users.id ORDER BY datetime DESC");
        statement.setString(1, userid.toString());
        ResultSet result = statement.executeQuery();
        int count = 0;
        while (result.next()) {
            msg.add(count++, new Message(result.getInt("messages.id"), result.getInt("messages.threadid"),
                    result.getTimestamp("messages.datetime"), result.getInt("messages.userid"),
                    result.getString("users.nickname"), result.getString("messages.title"),
                    result.getString("messages.text")));
        }
        result.close();
        statement.close();
        return msg;
    }
    public static ArrayList<Message> getMessagesDate(Date beginDate, Date endDate) throws SQLException {
        ArrayList<Message> msg = new ArrayList<Message>();
        PreparedStatement statement = DBconnector.connection.prepareStatement("SELECT * FROM messages, users " +
                "WHERE datetime >= ? AND datetime <= ? AND messages.userid = users.id ORDER BY datetime DESC");
        statement.setString(1, beginDate.toString());
        statement.setString(2, endDate.toString());
        ResultSet result = statement.executeQuery();
        int count = 0;
        while (result.next()) {
            msg.add(count++, new Message(result.getInt("messages.id"), result.getInt("messages.threadid"),
                    result.getTimestamp("messages.datetime"), result.getInt("messages.userid"),
                    result.getString("users.nickname"), result.getString("messages.title"),
                    result.getString("messages.text")));
        }
        result.close();
        statement.close();
        return msg;
    }
    public static ArrayList<Message> getMessagesThread(Integer threadid) throws SQLException {
        ArrayList<Message> msg = new ArrayList<Message>();
        PreparedStatement statement = DBconnector.connection.prepareStatement("SELECT * FROM messages, users " +
                "WHERE threadid = ? AND messages.userid = users.id ORDER BY datetime DESC");
        statement.setString(1, threadid.toString());
        ResultSet result = statement.executeQuery();
        int count = 0;
        while (result.next()) {
            msg.add(count++, new Message(result.getInt("messages.id"), result.getInt("messages.threadid"),
                    result.getTimestamp("messages.datetime"), result.getInt("messages.userid"),
                    result.getString("users.nickname"), result.getString("messages.title"),
                    result.getString("messages.text")));
        }
        result.close();
        statement.close();
        return msg;
    }
}

