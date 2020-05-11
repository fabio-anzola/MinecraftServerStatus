package responses;

import java.util.ArrayList;

public class PlayerResponse {

    int online;

    int max;

    ArrayList<String> list;

    public PlayerResponse(int online, int max, ArrayList<String> list) {
        this.online = online;
        this.max = max;
        this.list = list;
    }
}
