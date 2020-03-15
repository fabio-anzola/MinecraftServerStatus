import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MinecraftServerStaus {

    /**
     * Stores the address of the Minecraft server
     */
    private String serverAddress;

    /**
     * Stores the address of the api service (is a constant)
     */
    final private String API_ADDRESS = "https://api.mcsrvstat.us/2/";

    /**
     * Stores the path in which the program should store the files
     */
    private Path workingPath;

    /**
     * Stores all of the data from the server
     */
    private String[] data = new String[12];
    //state, ip, port, ping, query, srv, query mismatch, motd animated, motd, nr of players, playernames, version

    /**
     * Constructor (String serverAddress, String folderPath)
     *
     * @param serverAddress The address of the Minecraft server as a String
     * @param folderPath The folder in which the program should store the files as a String
     */
    public MinecraftServerStaus(String serverAddress, String folderPath) {
        this.serverAddress = serverAddress;
        this.workingPath = Paths.get(folderPath);
    }

    /**
     * Constructor (String serverAddress, Path folderPath)
     *
     * @param serverAddress The address of the Minecraft server as a String
     * @param folderPath The folder in which the program should store the files as a Path
     */
    public MinecraftServerStaus(String serverAddress, Path folderPath) {
        this.serverAddress = serverAddress;
        this.workingPath = folderPath;
    }

    /**
     * Downloads the information of the given server from the api and stores it locally
     *
     * @throws IOException If an error occurs
     */
    public void downloadJson () throws IOException {
        try (
                InputStream in = new URL(this.API_ADDRESS + this.serverAddress).openStream();
                OutputStream out = Files.newOutputStream(Paths.get(this.workingPath + "/download.json"))

        ) {
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * Parses the data from the json file to the array
     *
     * @throws IOException If an error occurs
     */
    public void parseData () throws IOException {
        try (
                BufferedReader in = Files.newBufferedReader(Paths.get(workingPath + "/download.json"), StandardCharsets.UTF_8);

        ) {
            String line;
            String allData = "";

            while ((line = in.readLine()) != null) {
                allData += line;
            }

            allData = allData.replaceAll("\\{", "");
            allData = allData.replaceAll("}", "");
            String[] dataTest = allData.split(",");

            if (dataTest[0].substring(6, dataTest[0].length() - 1).length() < 8) {
                if (dataTest[dataTest.length-1].substring(9, dataTest[dataTest.length-1].length()).equals("false")) {
                    this.data[0] = String.valueOf(false);
                }
            } else {
                //static values
                this.data[0] = String.valueOf(true); //state
                this.data[1] = dataTest[0].substring(6, dataTest[0].length() - 1); //ip
                this.data[2] = dataTest[1].substring(7, dataTest[1].length()); //port
                this.data[3] = dataTest[2].substring(15, dataTest[2].length()); //ping
                this.data[4] = dataTest[3].substring(8, dataTest[3].length()); //query
                this.data[5] = dataTest[4].substring(6, dataTest[4].length()); //srv
                this.data[6] = dataTest[5].substring(16, dataTest[5].length()); //query mismatch
                this.data[7] = dataTest[7].substring(15, dataTest[7].length()); //animated motd
            }

            //variable values
            for (int i = 0; i < dataTest.length; i++) {
                if (dataTest[i].contains("\"clean\":")) { //motd
                    if (!dataTest[i+1].contains("\"html\":")) {
                        this.data[8] = dataTest[i].substring(10, dataTest[i].length() - 1);
                        this.data[8] += ' ' + dataTest[i+1].substring(1, dataTest[i+1].length() - 1);
                    } else {
                        this.data[8] = dataTest[i].substring(10, dataTest[i].length() - 2);
                    }
                }
                if (dataTest[i].contains("\"players\":\"online\":")) { //nr of players
                    this.data[9] = dataTest[i].substring(19, dataTest[i].length());
                }
                if (dataTest[i].contains("\"version\":")) { //version
                    this.data[11] = dataTest[i].substring(11, dataTest[i].length() - 1);
                }
                if (dataTest[i].contains("\"list\":")) { //players
                    this.data[10] = dataTest[i].substring(9, dataTest[i].length() - 1);
                    int j = 1;
                    while (!dataTest[i + j].contains("\"version\"")) {
                        this.data[10] += ", " + dataTest[i + j].substring(1, dataTest[i + j].length() - 1);
                        j++;
                    }
                }
            }
        }
    }
}
