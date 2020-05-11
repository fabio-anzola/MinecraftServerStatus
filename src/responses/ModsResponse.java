package responses;

import java.util.ArrayList;

public class ModsResponse {

    ArrayList<String> names;

    ArrayList<String> raw;

    public ModsResponse(ArrayList<String> names, ArrayList<String> raw) {
        this.names = names;
        this.raw = raw;
    }
}
