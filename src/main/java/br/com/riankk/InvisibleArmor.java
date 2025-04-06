package br.com.riankk;

import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class InvisibleArmor extends JavaPlugin {

    public static final Map<UUID, Set<EnumWrappers.ItemSlot>> armadurasOcultas = new HashMap<>();

    @Override
    public void onEnable() {
        // Listener que intercepta os pacotes
        new ArmaduraListener(this, armadurasOcultas);

        // Comandos
        getCommand("esconder").setExecutor(new HideCommand(armadurasOcultas, this));
        getCommand("mostrar").setExecutor(new ShowCommand(armadurasOcultas, this));
    }
}
