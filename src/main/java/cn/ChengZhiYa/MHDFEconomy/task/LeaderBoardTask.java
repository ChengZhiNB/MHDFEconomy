package cn.ChengZhiYa.MHDFEconomy.task;

import cn.ChengZhiYa.MHDFEconomy.Main;
import cn.ChengZhiYa.MHDFEconomy.apis.MHDFEconomy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class LeaderBoardTask extends BukkitRunnable {
    private static LinkedList<Map.Entry<String, Double>> list;

    public static Map.Entry<String, Double> getPlayer(int position) {
        if (position > list.size() || position <= 0) {
            return null;
        }
        return list.get(list.size() - position);
    }

    @Override
    public void run() {
        MHDFEconomy economy = Main.getUltiEconomy();
        Map<String, Double> leaderboard = new HashMap<>();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            String playerName = player.getName();
            Double money = economy.checkMoney(playerName);
            Double bank = economy.checkBank(playerName);
            Double total = money + bank;
            leaderboard.put(playerName, total);
        }
        list = new LinkedList<>(leaderboard.entrySet());
        list.sort(Map.Entry.comparingByValue());
    }
}
