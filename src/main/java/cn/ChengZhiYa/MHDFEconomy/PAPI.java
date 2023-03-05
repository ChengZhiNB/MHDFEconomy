package cn.ChengZhiYa.MHDFEconomy;

import cn.ChengZhiYa.MHDFEconomy.apis.MHDFEconomy;
import cn.ChengZhiYa.MHDFEconomy.task.LeaderBoardTask;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.util.Map;

public class PAPI extends PlaceholderExpansion {
    @Override
    public String getIdentifier() {
        return "mhdf";
    }

    @Override
    public String getAuthor() {
        return "ChengZhiYa";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("money")) {
            MHDFEconomy economy = Main.getUltiEconomy();
            return String.format("%.2f", economy.checkMoney(player.getName()));
        }
        return null;
    }
}
