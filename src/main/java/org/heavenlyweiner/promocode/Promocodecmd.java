package org.heavenlyweiner.promocode;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Promocodecmd implements CommandExecutor {
    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {
        if (!(sender instanceof Player)) {
            // If the player (or console) uses our command correct, we can return true
            return true;
        }
        int COUNT_TO_ADD = 4;
        Player player = (Player) sender;
        // Here we need to give items to our player

        ItemStack diamonds = new ItemStack(Material.DIAMOND, COUNT_TO_ADD);

        Inventory inventory = player.getInventory();
        boolean isNotHaveEmptySlots = inventory.firstEmpty() == -1;
        boolean isCanAddToDiamonds = inventory.contains(Material.DIAMOND)
                && inventory.getItem(inventory.first(Material.DIAMOND)).getAmount()
                + COUNT_TO_ADD <= inventory.getItem(inventory.first(Material.DIAMOND)).getMaxStackSize();

        if (isNotHaveEmptySlots && !isCanAddToDiamonds) {
            player.sendMessage("У вас заполнен инвентарь.");

            return true;
        }
        // Give the player our items (comma-seperated list of all ItemStack)
        player.getInventory().addItem(diamonds);
        player.sendMessage("Поздравляем вы успешно получили награду.");
        return true;
    }
}
