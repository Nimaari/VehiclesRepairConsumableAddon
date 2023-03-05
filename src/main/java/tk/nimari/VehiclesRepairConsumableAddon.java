package tk.nimari;

import es.pollitoyeye.vehicles.VehiclesMain;
import es.pollitoyeye.vehicles.interfaces.Vehicle;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class VehiclesRepairConsumableAddon extends JavaPlugin {

    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        long currentTime = System.currentTimeMillis();

        new Metrics(this, 16800);

        config.addDefault("sendMessageOnNotEnoughMoney", true);
        config.addDefault("sendMessageOnSuccessfulRepair", true);
        config.addDefault("sendMessageOnFullHealthVehicle", true);
        config.addDefault("sendMessageOnInvalidVehicle", true);
        config.addDefault("notEnoughMoneyMessage", "&8[&eVehiclesRepairConsumableAddon&8] &cYou do not have enough money!");
        config.addDefault("successfulRepairMessage", "&8[&eVehiclesRepairConsumableAddon&8] &aSuccessful repair!");
        config.addDefault("alreadyFullHealthVehicleMessage", "&8[&eVehiclesRepairConsumableAddon&8] &cThis vehicle is already at full health!");
        config.addDefault("invalidVehicleMessage", "&8[&eVehiclesRepairConsumableAddon&8] &cYou are not riding a vehicle!");
        config.addDefault("repairPrice", 0.0);
        config.addDefault("itemName", "Repair Tool");
        config.addDefault("itemMaterial", "GOLDEN_PICKAXE");
        config.addDefault("deleteRepairItemOnRepair", false);
        config.options().copyDefaults(true);
        saveConfig();

        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        getLogger().log(Level.INFO, "Successful startup! Took " + (System.currentTimeMillis() - currentTime) + "ms.");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "Leaving so soon? Drive safe!");
    }

    public class Listeners implements Listener {

        @EventHandler
        public void interacts(EntityDamageByEntityEvent e) {
            if (!(e.getDamager() instanceof Player)) return;

            Player player = (Player) e.getDamager();

            if (player.getInventory().getItemInMainHand() == null) return;
            if (!player.getInventory().getItemInMainHand().hasItemMeta()) return;
            if (player.getInventory().getItemInMainHand().getType() != Material.matchMaterial(config.getString("itemMaterial")))
                return;
            if (!player.getInventory().getItemInMainHand().getItemMeta().hasDisplayName()) return;
            if (!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().contains(config.getString("itemName")))
                return;

            e.setCancelled(true);

            try {
                Vehicle vehicle = VehiclesMain.getPlugin().getPlayerVehicle(player);

                if (vehicle.getMainStand().getHealth() == vehicle.getMainStand().getMaxHealth()) {
                    if (config.getBoolean("sendMessageOnFullHealthVehicle"))
                        player.sendMessage(config.getString("alreadyFullHealthVehicleMessage").replace("&", "§"));
                    return;
                }


                if (config.getDouble("repairPrice") != 0.0) {
                    EconomyResponse er = getServer().getServicesManager().getRegistration(Economy.class)
                            .getProvider().withdrawPlayer(player, config.getDouble("repairPrice"));

                    if (!er.transactionSuccess()) {
                        if (config.getBoolean("sendMessageOnNotEnoughMoney"))
                            player.sendMessage(config.getString("notEnoughMoneyMessage").replace("&", "§"));
                        return;
                    }
                }

                if (config.getBoolean("deleteRepairItemOnRepair"))
                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                if (config.getBoolean("sendMessageOnSuccessfulRepair"))
                    player.sendMessage(config.getString("successfulRepairMessage").replace("&", "§"));

                vehicle.setHealth(vehicle.getMainStand().getMaxHealth());

            } catch (Exception ex) {
                if (config.getBoolean("sendMessageOnInvalidVehicle"))
                    player.sendMessage(config.getString("invalidVehicleMessage").replace("&", "§"));
            }

        }
    }
}