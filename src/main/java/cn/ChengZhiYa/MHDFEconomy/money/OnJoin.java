package cn.ChengZhiYa.MHDFEconomy.money;

import cn.ChengZhiYa.MHDFEconomy.Main;
import cn.ChengZhiYa.MHDFEconomy.apis.MHDFEconomy;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class OnJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {
        Player player = event.getPlayer();
        MHDFEconomy economy = Main.getUltiEconomy();
        double money = Main.getInstance().getConfig().getDouble("initial_money");
        File file = economy.getPlayerFile(player.getName());
        if (!file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            config.set("money", money);
            config.save(file);
        }
    }
}
