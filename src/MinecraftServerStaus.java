import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
}
