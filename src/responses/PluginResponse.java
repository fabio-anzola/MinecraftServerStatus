package responses;

import java.util.ArrayList;

public class PluginResponse {

    ArrayList<String> names;

    ArrayList<String> raw;

    public PluginResponse(ArrayList<String> names, ArrayList<String> raw) {
        this.names = names;
        this.raw = raw;
    }
}
