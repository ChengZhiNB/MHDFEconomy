package cn.ChengZhiYa.MHDFEconomy.apis;

import cn.ChengZhiYa.MHDFEconomy.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MHDFEconomy implements MHDFEconomyAPI {

    public File getPlayerFile(String player_name) {
        return new File(Main.getInstance().getDataFolder() + "/playerData", player_name + ".yml");
    }

    private YamlConfiguration loadConfig(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    private Boolean playerFileExists(String player_name) {
        return getPlayerFile(player_name).exists();
    }

    public Double checkMoney(String player_name) {
        if (playerFileExists(player_name)) {
            YamlConfiguration config = loadConfig(getPlayerFile(player_name));
            return config.getDouble("money");
        }
        return -1.0;
    }

    public Double checkBank(String player_name) {
        if (playerFileExists(player_name)) {
            YamlConfiguration config = loadConfig(getPlayerFile(player_name));
            return config.getDouble("bank");
        }
        return -1.0;
    }

    public Boolean setMoney(String player_name, Double amount) {
        try {
            assert amount >= 0;
        } catch (AssertionError e) {
            System.out.println("数额异常:" + e);
            return false;
        }
        if (!playerFileExists(player_name)) {
            return false;
        }
        YamlConfiguration config = loadConfig(getPlayerFile(player_name));
        config.set("money", amount);
        try {
            config.save(getPlayerFile(player_name));
            return true;
        } catch (IOException e) {
            System.out.println(ChatColor.RED + "[WARNING]保存数据异常：" + ChatColor.WHITE + e);
            return false;
        }
    }

    public Boolean addTo(String player_name, Double amount) {
        try {
            assert amount >= 0;
        } catch (AssertionError e) {
            System.out.println("数额异常:" + e);
            return false;
        }
        if (!playerFileExists(player_name)) {
            return false;
        }
        YamlConfiguration config = loadConfig(getPlayerFile(player_name));
        config.set("money", checkMoney(player_name) + amount);
        try {
            config.save(getPlayerFile(player_name));
            return true;
        } catch (IOException e) {
            System.out.println(ChatColor.RED + "[WARNING]保存数据异常：" + ChatColor.WHITE + e);
            return false;
        }
    }

    public Boolean takeFrom(String player_name, Double amount) {
        try {
            assert amount >= 0;
        } catch (AssertionError e) {
            System.out.println("数额异常:" + e);
            return false;
        }
        if (checkMoney(player_name) < amount) {
            return false;
        }
        YamlConfiguration config = loadConfig(getPlayerFile(player_name));
        config.set("money", checkMoney(player_name) - amount);
        try {
            config.save(getPlayerFile(player_name));
            return true;
        } catch (IOException e) {
            System.out.println(ChatColor.RED + "[WARNING]保存数据异常：" + ChatColor.WHITE + e);
            return false;
        }
    }

    public Boolean takeFromBank(String player_name, Double amount) {
        try {
            assert amount >= 0;
        } catch (AssertionError e) {
            System.out.println("数额异常:" + e);
            return false;
        }

        if (!(playerFileExists(player_name) && checkBank(player_name) >= amount)) {
            return false;
        }
        YamlConfiguration config = loadConfig(getPlayerFile(player_name));
        config.set("bank", checkBank(player_name) - amount);
        try {
            config.save(getPlayerFile(player_name));
            return true;
        } catch (IOException e) {
            System.out.println(ChatColor.RED + "[WARNING]保存数据异常：" + ChatColor.WHITE + e);
            return false;
        }
    }

    public Boolean transferMoney(String payer, String payee, Double amount) {
        try {
            assert amount >= 0;
        } catch (AssertionError e) {
            System.out.println(ChatColor.RED + "[WARNING]保存数据异常：" + ChatColor.WHITE + e);
            return false;
        }
        if (!(playerFileExists(payer) && playerFileExists(payee) && checkMoney(payer) >= amount)) {
            return false;
        }
        return takeFrom(payer, amount) && addTo(payee, amount);
    }

}
