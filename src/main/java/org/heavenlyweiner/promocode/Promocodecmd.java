package org.heavenlyweiner.promocode;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Promocodecmd implements CommandExecutor {
    private final Map<Player, CommandState> commandStates;
    private FileConfiguration commandDataConfig;

    public Promocodecmd() {
        commandStates = new HashMap<>();
        loadCommandData();
    }
    private void loadCommandData() {
        File commandDataFile = new File("plugins/promocode/commands.yml");
        if (!commandDataFile.exists()) {
            try {
                commandDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        commandDataConfig = YamlConfiguration.loadConfiguration(commandDataFile);

    }

    private void saveCommandData() {
        File commandDataFile = new File("plugins/promocode/commands.yml");
        try {
            commandDataConfig.save(commandDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        if (!(sender instanceof Player)) {
            // использование команды некорректно
            return true;
        }
        Player player = (Player) sender;
        CommandState state = commandStates.get(player);


        // Проверка была ли использована команда
        boolean isUsed = commandDataConfig.getBoolean(player.getUniqueId().toString() + ".used");

        if (isUsed) {
            player.sendMessage("§cВы уже использовали команду.");
            return true;
        }

        int COUNT_TO_ADD = 4;
        // Here we need to give items to our player

        ItemStack diamonds = new ItemStack(Material.DIAMOND, COUNT_TO_ADD);

        Inventory inventory = player.getInventory();
        boolean isNotHaveEmptySlots = inventory.firstEmpty() == -1;
        boolean isCanAddToDiamonds = inventory.contains(Material.DIAMOND)
                && inventory.getItem(inventory.first(Material.DIAMOND)).getAmount()
                + COUNT_TO_ADD <= inventory.getItem(inventory.first(Material.DIAMOND)).getMaxStackSize();

        if (isNotHaveEmptySlots && !isCanAddToDiamonds) {
            player.sendMessage("§cУ вас заполнен инвентарь.");

            return true;
        }
        // Give the player our items (comma-seperated list of all ItemStack)
        player.getInventory().addItem(diamonds);
        // Пометим игрока как использовавшего команду
        if (state == null) {
            state = new CommandState();
            commandStates.put(player, state);
        }
        state.setUsed(true);

        // Сохраняем информацию о состоянии использования команды в YML-файл
        commandDataConfig.set(player.getUniqueId().toString() + ".used", true);
        saveCommandData();

        player.sendMessage("§aПоздравляем, вы получили награду!");
        return true;
    }

}
