package org.yuan_dev.yuanReconnect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public final class YuanReconnect extends Plugin implements Listener {

    private static final String LOBBY_SERVER = "lobby";
    private final Set<String> protectedMainServers = new HashSet<>(Arrays.asList(
            "skyblock",
            "skygen",
            "survival",
            "boxpvp",
            "smp",
            "practice",
            "anarchy"
    ));

    @Override
    public void onEnable() {
        getProxy().getPluginManager().registerListener(this, this);
        getLogger().info("YuanReconnect da duoc bat.");
    }

    @Override
    public void onDisable() {
        getLogger().info("YuanReconnect da duoc tat.");
    }

    @EventHandler
    public void onServerDisconnect(ServerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        String disconnectedServer = event.getTarget().getName();

        if (!protectedMainServers.contains(disconnectedServer.toLowerCase())) {
            return;
        }

        if (!player.isConnected()) {
            return;
        }

        ServerInfo lobby = getProxy().getServerInfo(LOBBY_SERVER);
        if (lobby == null) {
            getLogger().warning("Lobby server '" + LOBBY_SERVER + "'khong ton tai, khong the ket noi lai nguoi choi" + player.getName());
            return;
        }

        if (player.getServer() != null && LOBBY_SERVER.equalsIgnoreCase(player.getServer().getInfo().getName())) {
            return;
        }

        player.connect(lobby);
        getLogger().info("di chuyen" + player.getName() + "ve lobby, bi ngat ket noi tu" + disconnectedServer);
    }
}
