package cn.ChengZhiYa.MHDFEconomy;

import cn.ChengZhiYa.MHDFEconomy.CMDs.MoneyOperateCommands;
import cn.ChengZhiYa.MHDFEconomy.apis.MHDFEconomy;
import cn.ChengZhiYa.MHDFEconomy.money.Money;
import cn.ChengZhiYa.MHDFEconomy.money.OnJoin;
import cn.ChengZhiYa.MHDFEconomy.money.Pay;
import cn.ChengZhiYa.MHDFEconomy.money.baltop;
import cn.ChengZhiYa.MHDFEconomy.register.CommandRegister;
import cn.ChengZhiYa.MHDFEconomy.task.LeaderBoardTask;
import cn.ChengZhiYa.MHDFEconomy.vault.EconomyImplementer;
import cn.ChengZhiYa.MHDFEconomy.vault.VaultHook;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class Main extends JavaPlugin {

    private static final MHDFEconomy ultiEconomy = new MHDFEconomy();
    private static Main plugin;
    public EconomyImplementer economyImplementer;
    private VaultHook vaultHook;

    public static MHDFEconomy getUltiEconomy() {
        return ultiEconomy;
    }

    public static Main getInstance() {
        return plugin;
    }


    private boolean setupEconomy() {
        return getServer().getPluginManager().getPlugin("Vault") != null;
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            getLogger().warning("[MHDFEconomy] 未找到Vault前置插件，关闭中...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        plugin = this;
        economyImplementer = new EconomyImplementer();
        vaultHook = new VaultHook();
        vaultHook.hook();
        File folder = new File(String.valueOf(getDataFolder()));
        File playerDataFolder = new File(getDataFolder() + "/playerData");
        File config_file = new File(getDataFolder(), "config.yml");
        if (!folder.exists() || !config_file.exists()) {
            saveDefaultConfig();
        }
        if (!playerDataFolder.exists()) {
            playerDataFolder.mkdirs();
        }

        new LeaderBoardTask().runTaskTimerAsynchronously(this, 0, 200L);
        getServer().getPluginManager().registerEvents(new OnJoin(), this);
        Objects.requireNonNull(this.getCommand("givemoney")).setExecutor(new MoneyOperateCommands());
        Objects.requireNonNull(this.getCommand("takemoney")).setExecutor(new MoneyOperateCommands());
        Objects.requireNonNull(this.getCommand("setmoney")).setExecutor(new MoneyOperateCommands());
        Objects.requireNonNull(this.getCommand("money")).setExecutor(new Money());
        Objects.requireNonNull(this.getCommand("baltop")).setExecutor(new baltop());
        CommandRegister.registerCommand(this, new Pay(), "付款指令", "ultipay", "upay", "pay");

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PAPI().register();
        }

        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "经济插件已加载！");
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "作者ChengZhiYa");
    }

    @Override
    public void onDisable() {
        vaultHook.unhook();
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "经济插件已卸载！");
    }
}