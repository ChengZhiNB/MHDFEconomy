package cn.ChengZhiYa.MHDFEconomy.money;


import cn.ChengZhiYa.MHDFEconomy.Main;
import cn.ChengZhiYa.MHDFEconomy.apis.MHDFEconomy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Money implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        MHDFEconomy economy = Main.getUltiEconomy();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (sender instanceof Player) {
                    if (args.length == 0) {
                        sender.sendMessage(String.format(ChatColor.YELLOW + "[系统提示]你当前的余额为 %.2f 功勋。", economy.checkMoney(sender.getName())));
                        return;
                    }
                }

                if (Bukkit.getPlayer(args[0]) != null) {
                    sender.sendMessage(String.format(ChatColor.YELLOW + "[系统提示]" + Objects.requireNonNull(Bukkit.getPlayer(args[0])).getName() + "当前的余额为 %.2f 功勋。", economy.checkMoney(Objects.requireNonNull(Bukkit.getPlayer(args[0])).getName())));
                }
            }
        }.runTaskAsynchronously(Main.getInstance());
        return false;
    }
}