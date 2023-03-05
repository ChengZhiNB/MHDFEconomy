package cn.ChengZhiYa.MHDFEconomy.money;

import cn.ChengZhiYa.MHDFEconomy.Main;
import cn.ChengZhiYa.MHDFEconomy.apis.MHDFEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Pay implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        MHDFEconomy economy = Main.getUltiEconomy();
        if (!"ultipay".equalsIgnoreCase(command.getName()) || args.length != 2) {
            sender.sendMessage(ChatColor.RED + "[警告]请输入正确的指令！");
            sender.sendMessage(ChatColor.RED + "用法：/ultipay 玩家名 数字");
            return true;
        }
        double amount;
        try {
            amount = Double.parseDouble(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED + "[警告]请输入正确的指令！");
            sender.sendMessage(ChatColor.RED + "用法：/ultipay 玩家名 数字");
            return true;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (amount < 0) {
                    sender.sendMessage(ChatColor.RED + "[警告]转账数额必须大于0！");
                    return;
                }
                if (!economy.transferMoney(sender.getName(), args[0], amount)) {
                    sender.sendMessage(ChatColor.RED + "转账失败！");
                    return;
                }
                sender.sendMessage(String.format(ChatColor.GOLD + "你已转账%s给%s！", args[1], args[0]));
                Player receiver = Bukkit.getPlayerExact(args[0]);
                if (receiver != null && receiver.isOnline()) {
                    receiver.sendMessage(String.format(ChatColor.GOLD + "你收到一笔来自%s的%s！", sender.getName(), args[1]));
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> tabCommands = new ArrayList<>();
            for (OfflinePlayer offlinePlayer : Main.getInstance().getServer().getOfflinePlayers()) {
                tabCommands.add(offlinePlayer.getName());
            }
            return tabCommands;
        }
        return null;
    }
}
