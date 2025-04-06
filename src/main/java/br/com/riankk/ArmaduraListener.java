package br.com.riankk;

import com.comphenix.protocol.*;
import com.comphenix.protocol.events.*;
import com.comphenix.protocol.wrappers.*;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ArmaduraListener {
    public ArmaduraListener(Plugin plugin, Map<UUID, Set<EnumWrappers.ItemSlot>> escondidas) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_EQUIPMENT) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player viewer = event.getPlayer();
                PacketContainer packet = event.getPacket();

                int entityId = packet.getIntegers().read(0);
                Player target = null;
                for (Player p : viewer.getWorld().getPlayers()) {
                    if (p.getEntityId() == entityId) {
                        target = p;
                        break;
                    }
                }

                if (target == null || !escondidas.containsKey(target.getUniqueId())) return;

                Set<EnumWrappers.ItemSlot> partes = escondidas.get(target.getUniqueId());

                List<Pair<EnumWrappers.ItemSlot, ItemStack>> lista = new ArrayList<>();
                for (Pair<EnumWrappers.ItemSlot, ItemStack> par : packet.getSlotStackPairLists().read(0)) {
                    if (partes.contains(par.getFirst())) {
                        lista.add(new Pair<>(par.getFirst(), null));
                    } else {
                        lista.add(par);
                    }
                }

                packet.getSlotStackPairLists().write(0, lista);
            }
        });
    }
}
