import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zipper {

    private final String TAG = "ZIPPER ";
    private String path = "/Users/tkallinich/DashboardProjectResources/";

    public Zipper() {
    }

    /**
     * zip file to directory
     *
     * @param name name of file "text" without suffix
     * @throws IOException
     */
    public void zipToFile(String name) throws IOException {

        String sourceFile =  path + name + ".txt";
        FileOutputStream fos = new FileOutputStream(path + name + ".zip");

        System.out.println(TAG + "File: " + name);
        System.out.println(TAG + "Path from: " + sourceFile);
        System.out.println(TAG + "Path to  : " + sourceFile.replace("txt", "zip"));

        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
        FileInputStream fis = new FileInputStream(fileToZip);

        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);

        final byte[] bytes = new byte[1024];
        int length;

        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);


        }
        zipOut.close();
        fis.close();
        fos.close();

        System.out.println(TAG + " File: " + name + ".txt zipped");

    }

    /**
     * unzip file to project resource directory
     *
     * @param name name with out suffix
     * @throws IOException
     */
    public void unZipFileFrom(String name) throws IOException {

        String fileZip = path + name + ".zip";
        byte[] buffer = new byte[1024];

        ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip));
        ZipEntry zipEntry = zis.getNextEntry();

        while(zipEntry != null){
            String fileName = zipEntry.getName();
            File newFile = new File(path + fileName);
            FileOutputStream fos = new FileOutputStream(newFile);

            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();

        System.out.println(TAG + "File: " + name);
        System.out.println(TAG + "Path from: " + fileZip);
        System.out.println(TAG + "Path to  : " + fileZip.replace("zip", "txt"));

        // filezip is a String with full path
        convertToCsv(fileZip);

    }

    /**
     * convert to csv from txt
     */
    private void convertToCsv(String pathAsString){

        // String nameTxt = "AMECO1.txt";
        String name = getTableNameFromPath(pathAsString);

        if(!fileExists(name)){
            // final Path path = Paths.get("path", "to", "folder");
            final Path path = Paths.get(  this.path);

            final Path txt = path.resolve(name);
            final Path csv = path.resolve(name + ".csv");

            try (
                    final Stream<String> lines = Files.lines(txt);
                    final PrintWriter pw = new PrintWriter(Files.newBufferedWriter(csv, StandardOpenOption.CREATE_NEW))) {

                lines.map((line) -> line.split("\\|")).
                        map((line) -> Stream.of(line).collect(Collectors.joining(","))).
                        forEach(pw::println);

                System.out.println(TAG + "new file created");

            } catch (Exception e) {
               // e.printStackTrace();
            }

        } else {

            System.out.println(TAG + "file exsits");

        }

    }

    /**
     * check if a file existis in path directory
     *
     **/
    private Boolean fileExists(String fileName){
        fileName += ".csv";

        Boolean result = new File(this.path, fileName).exists();

        System.out.println(TAG + "Check    : " + path + fileName + " result: " + result);

        return result;
    }

    /**
     * split path and extract table name
     *
     * @param pathAsString
     * @return table name
     */
    private String getTableNameFromPath(String pathAsString){

        String []x = pathAsString.split("_");
        pathAsString = x[1].toUpperCase();
        pathAsString = pathAsString.replaceAll(".ZIP",".txt");

        System.out.println(TAG + "TableName: " + pathAsString);

        return pathAsString;
    }

}
