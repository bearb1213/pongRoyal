package serveur;

import java.net.InetAddress;

public class PlayerInfo {
    public InetAddress address;
    public int port;

    public PlayerInfo(InetAddress address, int port) {
        this.address = address;
        this.port = port;
    }
}
