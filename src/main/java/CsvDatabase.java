import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class CsvDatabase {
    private String port = "3306";
    private String dbName = "alg_1";
    private String userName = "root";
    private String password = "root";
    private Connection con;

    private final String TAG = "CSV-DATABASE ";


    // name of database: myDashboardDatabase
    // user: root
    // password to mysql database:  1234

    // create database
    // sudo mysql -u root -p -e "create database myDashboardDatabase"

    // Access priv to create database
    // sudo chown -R mysql:mysql /usr/local/mysql*

    // start databse
    // brew services start mysql

    // restart database
    // brew services restart mysql

    /**
     * create database
     *
     * @param port
     * @param dbName
     * @param userName
     * @param password
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public CsvDatabase(String port, String dbName, String userName, String password) throws ClassNotFoundException, SQLException{
        this.port = port;
        this.dbName = dbName;
        this.userName = userName;
        this.password = password;
        System.out.println(TAG + " DB name:     " + dbName);
        System.out.println(TAG + " DB port:     " + port);
        System.out.println(TAG + " DB username: " + userName);
        System.out.println(TAG + " DB password: " + password);

        connect();
    }

    /**
     * connect to database
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String sslOff = "?autoReconnect=true&useSSL=false";
        String timeZone = "&serverTimezone=Europe/Moscow";
        con = DriverManager.getConnection("jdbc:mysql://localhost:" + port + "/" + dbName + sslOff + timeZone, userName, password);

        System.out.println(TAG + " connected to database");

    }

    /**
     * add data to table in database
     *
     * @param tableName
     * @param columns
     * @param values
     * @throws SQLException
     */
    public void addData(String tableName,String columns, String values) throws SQLException{

        String case1 = "'FTE's'";

        values = values.replaceAll("'NA'", "'0'")
                .replace("'NA", "'0");

        values = values.replace("'s'", "s'");

        String query = "insert into " + tableName + "(ID," + columns + ")";
        query += "values(NULL,"+ values + ");";

        System.out.println("Query: " + query);

        Statement stm = (Statement) con.createStatement();
        stm.executeUpdate(query);

        System.out.println("inserted");
    }

    /**
     * create a table in database
     *
     * @param tableName
     * @param columns
     * @throws SQLException
     */
    public void createTable(String tableName, String columns) throws SQLException {

        // System.out.println("Tablename: " + tableName);
        // System.out.println("Columns: " + columns);

        String[] cols = columns.split(",");
        System.out.println("--------------");
        System.out.println("SQL Statement:");

        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + tableName + "(`ID` INTEGER NOT NULL AUTO_INCREMENT, ";
            for(int i = 0; i < cols.length; i++){

                String trimmed = cols[i].replaceAll("\\s+","").replace("-","");

                sqlCreate += trimmed + checkType(cols[i]) + ", ";

            }

        sqlCreate += "PRIMARY KEY (ID))";
        System.out.println(sqlCreate + "\n--------------\n");

        Statement stmt = con.createStatement();
        stmt.execute(sqlCreate);
    }

    /**
     * check type of to insert data
     * @param columnName
     * @return
     */
    public String checkType(String columnName){

        String result = " VARCHAR(255)";

        Scanner sc = new Scanner(columnName);

        if(!sc.hasNextInt()) {
            result =  " VARCHAR(255)";
        } else {
            result = " INT";
        }

        return result;
    }

}
