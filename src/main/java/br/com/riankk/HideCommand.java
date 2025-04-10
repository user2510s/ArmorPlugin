package br.com.riankk;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class HideCommand implements CommandExecutor {

    private final Map<UUID, Set<EnumWrappers.ItemSlot>> escondidas;
    private final JavaPlugin plugin;
    private final DadosManager dadosManager;

    public HideCommand(Map<UUID, Set<EnumWrappers.ItemSlot>> escondidas, JavaPlugin plugin, DadosManager dadosManager) {
        this.escondidas = escondidas;
        this.plugin = plugin;
        this.dadosManager = dadosManager;
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
            sender.sendMessage("§cUse /esconder <parte|tudo>");
            return true;
        }

        escondidas.putIfAbsent(player.getUniqueId(), new HashSet<>());
        Set<EnumWrappers.ItemSlot> slots = escondidas.get(player.getUniqueId());

        if (args[0].equalsIgnoreCase("tudo")) {
            slots.addAll(List.of(EnumWrappers.ItemSlot.HEAD, EnumWrappers.ItemSlot.CHEST,
                    EnumWrappers.ItemSlot.LEGS, EnumWrappers.ItemSlot.FEET));
        } else {
            EnumWrappers.ItemSlot slot = getSlot(args[0]);
            if (slot == null) {
                sender.sendMessage("§cParte inválida. Use capa, peito, calça, bota ou tudo.");
                return true;
            }
            slots.add(slot);
        }

        dadosManager.salvar(escondidas); // Salva os dados após o comando
        sender.sendMessage("§aParte(s) escondida(s) com sucesso!");
        return true;
    }
}
