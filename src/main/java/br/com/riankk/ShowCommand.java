package br.com.riankk;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class ShowCommand implements CommandExecutor {

    private final Map<UUID, Set<EnumWrappers.ItemSlot>> escondidas;
    private final JavaPlugin plugin;

    public ShowCommand(Map<UUID, Set<EnumWrappers.ItemSlot>> escondidas, JavaPlugin plugin) {
        this.escondidas = escondidas;
        this.plugin = plugin;
    }

    private EnumWrappers.ItemSlot getSlot(String arg) {
        return switch (arg.toLowerCase()) {
            case "capa" -> EnumWrappers.ItemSlot.HEAD;
            case "peito" -> EnumWrappers.ItemSlot.CHEST;
            case "calça" -> EnumWrappers.ItemSlot.LEGS;
            case "bota" -> EnumWrappers.ItemSlot.FEET;
            default -> null;
        };
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length < 1) {
            sender.sendMessage("§cUse /mostrar <parte|tudo>");
            return true;
        }

        Set<EnumWrappers.ItemSlot> slots = escondidas.getOrDefault(player.getUniqueId(), new HashSet<>());

        if (args[0].equalsIgnoreCase("tudo")) {
            slots.clear();
        } else {
            EnumWrappers.ItemSlot slot = getSlot(args[0]);
            if (slot == null) {
                sender.sendMessage("§cParte inválida. Use capacete, peito, calça, bota ou tudo.");
                return true;
            }
            slots.remove(slot);
        }

        // Atualiza a visualização pros outros jogadores
        updatePlayer(player);
        sender.sendMessage("§aParte(s) mostrada(s) com sucesso!");
        return true;
    }

    private void updatePlayer(Player p) {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            viewer.hideEntity(plugin, p);
            Bukkit.getScheduler().runTaskLater(plugin, () -> viewer.showEntity(plugin, p), 2L);
        }
    }
}
