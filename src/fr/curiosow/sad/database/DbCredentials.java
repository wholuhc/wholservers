package fr.curiosow.sad.database;

public class DbCredentials
{

    private String host;
    private String user;
    private String pass;
    private String dbName;
    private int port;

    public DbCredentials(String host, String user, String pass, String dbName, int port)
    {
        this.host = host;
        this.user = user;
        this.pass = pass;
        this.dbName = dbName;
        this.port = port;
    }

    public String toURL()
    {
        final StringBuilder sb = new StringBuilder();

        sb.append("jdbc:mysql://").append(host).append(":").append(port).append("/").append(dbName).append("?characterEncoding=utf8");

        return sb.toString();
    }

    public String getUser()
    {
        return user;
    }

    public String getPass()
    {
        return pass;
    }

}
