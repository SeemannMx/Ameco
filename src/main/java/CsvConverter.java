
    import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;


public class CsvConverter  {

    private final String TAG = "CSV-CONVERTER ";
    private static String fileName = "alg_1.csv";

    // mysql> FLUSH PRIVILEGES
    // -> ALTER USER 'root'@'localhost' IDENTIFIED BY '1234'
    //         -> \q
    //         Bye

    /**
     * get csv / text file from directory and create database from file
     * @param fName name without suffix
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     */
    public CsvConverter(String fName) throws SQLException, ClassNotFoundException, FileNotFoundException {
        String path = "/Users/tkallinich/DashboardProjectResources/";
        fileName = path + fName + ".txt" + ".csv";

        String tableName = fileName.substring(44, fileName.length()-8);
        System.out.println(TAG + " table name: " + tableName);
        System.out.println(TAG + " file name : " + fileName);

        // Creating database
        CsvDatabase db = new CsvDatabase("3306", "myDashboardDatabase", "root", "serena" );

        //CSV Reader
        Scanner inputReader = new Scanner(new File(fileName));

        //Getting column names from first line / adjust statement
        String columns = "`" +
                (inputReader.nextLine()).replace(";", ",")
                .replace("-","")
                .replaceAll("\\s+","")
                .trim();
        columns = columns.replaceAll(",", "`,`");
        columns = columns.substring(0,columns.length() - 2);


        //Check the table if does not exist, create a table
        db.createTable(tableName, columns);

        //Inserting data to database
        while(inputReader.hasNextLine()){
            db.addData(tableName, columns, generateRow(inputReader.nextLine()));
        }

        System.out.println(TAG + " database created from csv / txt");

    }

    //Generate suitable row for entering SQL Query
    public static String generateRow(String row){
        String rowForSQL = "";
        String[] cols = row.split(";");
        for(int i = 0; i < cols.length; i++){
            rowForSQL += "'"+cols[i]+"'" + ( i != (cols.length-1) ? "," : "");
        }
        return rowForSQL;
    }

}


