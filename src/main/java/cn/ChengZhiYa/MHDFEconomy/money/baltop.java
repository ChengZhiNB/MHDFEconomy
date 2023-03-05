package cn.ChengZhiYa.MHDFEconomy.money;

import cn.ChengZhiYa.MHDFEconomy.task.LeaderBoardTask;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class baltop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        sender.sendMessage(ChatColor.YELLOW + "[系统提示]土豪榜正在统计中,请稍等...");
        sender.sendMessage(ChatColor.YELLOW +
                "=========================== 梦回东方土豪榜 ===========================\n" +
                "============= 排名 ============= 玩家 ============= 财富 =============\n" + ChatColor.GREEN +
                "              NO.1              " + TopName(1) + TopMoney(1) + "\n" +
                "              NO.2              " + TopName(2) + TopMoney(2) + "\n" +
                "              NO.3              " + TopName(3) + TopMoney(3) + "\n" +
                "              NO.4              " + TopName(4) + TopMoney(4) + "\n" +
                "              NO.5              " + TopName(5) + TopMoney(5) + "\n" +
                "              NO.6              " + TopName(6) + TopMoney(6) + "\n" +
                "              NO.7              " + TopName(7) + TopMoney(7) + "\n" +
                "              NO.8              " + TopName(8) + TopMoney(8) + "\n" +
                "              NO.9              " + TopName(9) + TopMoney(9) + "\n" +
                "              NO.10              " + TopName(10) + TopMoney(10) + "\n");
        return false;
    }

    public String TopName(int top) {
        if (getTop(top)[0] == null && getNull(getTop(top)[0]) == null) {
            return "无";
        }
        return getTop(top)[0] + getNull(getTop(top)[0]);
    }

    public String TopMoney(int top) {
        if (getTop(top)[1] == null) {
            return "无";
        }
        return getTop(top)[1];
    }

    public String[] getTop(int top) {
        try {
            Map.Entry<String, Double> entry = LeaderBoardTask.getPlayer(top);
            if (entry == null) {
                return null;
            }
            return new String[]{String.format("%s", entry.getKey()), String.format("%.2f", entry.getValue())};
            } catch (Exception e) {
                return null;
            }
    }

    public String getNull(String Name) {
        int NullTime = 21-Name.length();
        if (NullTime <= 3) {
            return "   ";
        }
        StringBuilder NullString = new StringBuilder();
        for (int i=0;i<NullTime;i++) {
            NullString.append(" ");
        }
        return NullString.toString();
    }
}
