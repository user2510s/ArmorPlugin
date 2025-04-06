package br.com.riankk;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class InvisibleArmor extends JavaPlugin {

    public static Map<UUID, Set<EnumWrappers.ItemSlot>> armadurasOcultas;
    private DadosManager dadosManager;

    @Override
    public void onEnable() {
        dadosManager = new DadosManager(getDataFolder());
        armadurasOcultas = dadosManager.carregar();

        new ArmaduraListener(this, armadurasOcultas);

        getCommand("esconder").setExecutor(new HideCommand(armadurasOcultas, this, dadosManager));
        getCommand("mostrar").setExecutor(new ShowCommand(armadurasOcultas, this, dadosManager));

        // Exibir ActionBar periodicamente
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Set<EnumWrappers.ItemSlot> slots = armadurasOcultas.getOrDefault(player.getUniqueId(), Set.of());

                if (!slots.isEmpty()) {
                    player.sendActionBar("§e⚠ ");
                } else {
                    player.sendActionBar(""); // Limpa a ActionBar
                }
            }
        }, 0L, 40L); // Atualiza a cada 2 segundos
    }

    @Override
    public void onDisable() {
        dadosManager.salvar(armadurasOcultas);
    }
}
