package br.com.riankk;

import com.comphenix.protocol.wrappers.EnumWrappers;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class DadosManager {
    private final File file;

    public DadosManager(File dataFolder) {
        this.file = new File(dataFolder, "armaduras.yml");
    }

    public void salvar(Map<UUID, Set<EnumWrappers.ItemSlot>> dados) {
        try {
            // ✅ Cria o diretório se ele não existir
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Map.Entry<UUID, Set<EnumWrappers.ItemSlot>> entry : dados.entrySet()) {
                    String line = entry.getKey() + ":" + entry.getValue().stream()
                            .map(EnumWrappers.ItemSlot::name)
                            .collect(Collectors.joining(","));
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<UUID, Set<EnumWrappers.ItemSlot>> carregar() {
        Map<UUID, Set<EnumWrappers.ItemSlot>> dados = new HashMap<>();
        if (!file.exists()) return dados;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(":");
                if (split.length < 2) continue;

                UUID uuid = UUID.fromString(split[0]);
                Set<EnumWrappers.ItemSlot> slots = Arrays.stream(split[1].split(","))
                        .map(EnumWrappers.ItemSlot::valueOf)
                        .collect(Collectors.toSet());
                dados.put(uuid, slots);
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return dados;
    }
}
