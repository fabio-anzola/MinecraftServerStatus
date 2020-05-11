package responses;

public class Response {

    boolean online;

    String ip;

    int port;

    DebugResponse debug;

    MotdResponse motd;

    PlayerResponse players;

    String version;

    int protocol;

    String hostname;

    String icon;

    String software;

    String map;

    PluginResponse plugins;

    ModsResponse mods;

    InfoResponse info;

    public Response(boolean online, String ip, int port, DebugResponse debug, MotdResponse motd, PlayerResponse players, String version, int protocol, String hostname, String icon, String software, String map, PluginResponse plugins, ModsResponse mods, InfoResponse info) {
        this.online = online;
        this.ip = ip;
        this.port = port;
        this.debug = debug;
        this.motd = motd;
        this.players = players;
        this.version = version;
        this.protocol = protocol;
        this.hostname = hostname;
        this.icon = icon;
        this.software = software;
        this.map = map;
        this.plugins = plugins;
        this.mods = mods;
        this.info = info;
    }
}