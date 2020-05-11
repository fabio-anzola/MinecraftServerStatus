package responses;

import java.util.ArrayList;

public class MotdResponse {

    ArrayList<String> raw;

    ArrayList<String> clean;

    ArrayList<String> html;

    public MotdResponse(ArrayList<String> raw, ArrayList<String> clean, ArrayList<String> html) {
        this.raw = raw;
        this.clean = clean;
        this.html = html;
    }
}
