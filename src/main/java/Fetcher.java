import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Fetcher {

    private final String TAG = "FETCHER ";

    public void fetchAllDataFromAmeco(){
        String baseUrlasString = "http://ec.europa.eu/economy_finance/db_indicators/ameco/documents/ameco";
        String baseDestination = "/Users/tkallinich/DashboardProjectResources/dashboard_ameco";
        String zip = ".zip";
        StringBuilder sb = new StringBuilder();

        String urlAsString = "";
        String destination = "";

        int elementsToFetch = 19;
        for(int i = 1; i < elementsToFetch; i++){

            try {

                urlAsString = baseUrlasString + i + zip;
                System.out.println("URL        : " + urlAsString);

                destination = baseDestination + i + zip;
                System.out.println("DESTINATION: " + destination);

                URL url = new URL(urlAsString);
                File file = new File(destination);

                FileUtils.copyURLToFile(url, file);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("fetched all data");
    }
}
/**
 Population and Employment
 href="http://ec.europa.eu/economy_finance/db_indicators/ameco/documents/ameco1.zip"

 Consumption
 href="http://ec.europa.eu/economy_finance/db_indicators/ameco/documents/ameco2.zip"

 **/

// String destination = "/Users/tkallinich/DashboardProjectResources/dashboard_ameco1.zip";
