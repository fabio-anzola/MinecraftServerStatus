package responses;

import java.util.ArrayList;

public class InfoResponse {

    ArrayList<String> raw;

    ArrayList<String> clean;

    ArrayList<String> html;

    public InfoResponse(ArrayList<String> raw, ArrayList<String> clean, ArrayList<String> html) {
        this.raw = raw;
        this.clean = clean;
        this.html = html;
    }
}
