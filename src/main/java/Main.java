import org.apache.commons.io.FileUtils;
import sun.jvm.hotspot.debugger.SymbolLookup;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;


public class Main {

    private final String TAG = "MAIN ";
    private String path = "/Users/tkallinich/DashboardProjectResources/";



    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

        Fetcher fetcher = new Fetcher();
        // fetcher.fetchAllDataFromAmeco();

        // unzip file
        Zipper zipper = new Zipper();
        // zipper.unZipFileFrom("dashboard_ameco1");

        // all zip files
        String file = "";
        String baseName = "dashboard_ameco";

        for(int i = 1; i < 19; i++){
            file = baseName + i;
            System.out.println("MAIN File: " + file);

            zipper.unZipFileFrom(file);

        }

        String tablename = "AMECO";
        for(int i = 1; i < 19; i++){
            CsvConverter csvConverter = new CsvConverter(tablename + i);
        }
    }

    /**
     * check if directory content is unziped and converted
     * @return
     */
    private boolean checkDirectory(){
        Boolean result = true;

        // get directory and count files
        File fileX = new File(path);

        String name = fileX.getName();
        int details = fileX.list().length;
        File [] ex = fileX.listFiles();

        System.out.println(TAG + "Name  : "+  name);
        System.out.println(TAG + "Lenght: "+  details);

        for(int i = 0; i < ex.length; i++){
            System.out.println(TAG + "Directory file: " + "[" +i+ "]" +  ex[i]);

            // check if files needs to be unzipped or converted to csv

        }

        return result;
    }
}
