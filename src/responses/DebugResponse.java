package responses;

public class DebugResponse {

    boolean ping;

    boolean query;

    boolean srv;

    boolean querymismatch;

    boolean ipinsrv;

    boolean animatedmotd;

    boolean proxypipe;

    long cachetime;

    public DebugResponse(boolean ping, boolean query, boolean srv, boolean querymismatch, boolean ipinsrv, boolean animatedmotd, boolean proxypipe, long cachetime) {
        this.ping = ping;
        this.query = query;
        this.srv = srv;
        this.querymismatch = querymismatch;
        this.ipinsrv = ipinsrv;
        this.animatedmotd = animatedmotd;
        this.proxypipe = proxypipe;
        this.cachetime = cachetime;
    }
}