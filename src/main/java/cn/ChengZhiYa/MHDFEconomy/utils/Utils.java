package cn.ChengZhiYa.MHDFEconomy.utils;

import cn.ChengZhiYa.MHDFEconomy.Main;
import cn.ChengZhiYa.MHDFEconomy.apis.MHDFEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Utils {

    public static boolean setMoney(CommandSender commandSender, String[] strings) {
        MHDFEconomy economy = Main.getUltiEconomy();
        double amount;
        try {
            amount = Double.parseDouble(strings[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "格式错误");
            return false;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (amount < 0) {
                    commandSender.sendMessage(ChatColor.RED + "[警告]设置数额必须大于0！");
                    return;
                }
                if (!economy.setMoney(strings[0], amount)) {
                    commandSender.sendMessage(ChatColor.RED + "操作失败！");
                    return;
                }
                commandSender.sendMessage(String.format(ChatColor.GOLD + "你已将%s的现金余额设置为%.2f！", strings[0], amount));
            }
        }.runTaskAsynchronously(Main.getInstance());
        return true;
    }

    public static boolean giveMoney(CommandSender commandSender, String[] strings) {
        MHDFEconomy economy = Main.getUltiEconomy();
        double amount;
        try {
            amount = Double.parseDouble(strings[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "格式错误");
            return false;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (amount < 0) {
                    commandSender.sendMessage(ChatColor.RED + "[警告]转账数额必须大于0！");
                    return;
                }
                if (!economy.addTo(strings[0], amount)) {
                    commandSender.sendMessage(ChatColor.RED + "转账失败！");
                    return;
                }
                commandSender.sendMessage(String.format(ChatColor.GOLD + "你已转账%.2f给%s！", amount, strings[0]));
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (strings[0].equals(players.getName())) {
                        players.sendMessage(String.format(ChatColor.GOLD + "你收到一笔%s转给你的%.2f！", commandSender.getName(), amount));
                    }
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
        return true;
    }

    public static boolean takeMoney(CommandSender commandSender, String[] strings) {
        MHDFEconomy economy = Main.getUltiEconomy();
        double amount;
        try {
            amount = Double.parseDouble(strings[1]);
        } catch (NumberFormatException e) {
            commandSender.sendMessage(ChatColor.RED + "格式错误");
            return false;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (amount < 0) {
                    commandSender.sendMessage(ChatColor.RED + "[警告]数额必须大于0！");
                    return;
                }
                if (economy.takeFrom(strings[0], amount)) {
                    commandSender.sendMessage(String.format(ChatColor.GOLD + "你已从%s夺取%.2f！", strings[0], amount));
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (strings[0].equals(players.getName())) {
                            players.sendMessage(String.format(ChatColor.GOLD + "你被没收了%.2f！", amount));
                        }
                    }
                } else if (economy.takeFromBank(strings[0], amount)) {
                    commandSender.sendMessage(String.format(ChatColor.GOLD + "你已从%s的银行里夺取%.2f！", strings[0], amount));
                    for (Player players : Bukkit.getOnlinePlayers()) {
                        if (strings[0].equals(players.getName())) {
                            players.sendMessage(String.format(ChatColor.GOLD + "你的银行账户被没收了%.2f！", amount));
                        }
                    }
                } else {
                    commandSender.sendMessage(ChatColor.GOLD + "没收失败！可能是对方货币数量不足。");
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
        return true;
    }
}
