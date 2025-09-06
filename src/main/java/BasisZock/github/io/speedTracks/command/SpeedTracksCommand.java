package BasisZock.github.io.speedTracks.command;

import BasisZock.github.io.speedTracks.SpeedTracks;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SpeedTracksCommand implements CommandExecutor, TabCompleter {

    private final SpeedTracks plugin;
    private final String prefix = ChatColor.GOLD + "[SpeedTracks] " + ChatColor.GRAY;

    public SpeedTracksCommand(SpeedTracks plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            sender.sendMessage(prefix + "Usage: /" + label + " <reload|info>");
            return true;
        }

        String subCommand = args[0].toLowerCase();
        switch (subCommand) {
            case "reload" -> handleReload(sender);
            case "info" -> handleInfo(sender, args);
            default -> sender.sendMessage(prefix + ChatColor.RED + "Unknown command. Usage: /" + label + " <reload|info>");
        }
        return true;
    }

    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("speedtracks.command.reload")) {
            sender.sendMessage(prefix + ChatColor.RED + "You don't have permission to use this command.");
            return;
        }
        plugin.loadConfiguration();
        sender.sendMessage(prefix + ChatColor.GREEN + "Configuration reloaded successfully!");
    }

    private void handleInfo(CommandSender sender, String[] args) {
        if (!sender.hasPermission("speedtracks.command.info")) {
            sender.sendMessage(prefix + ChatColor.RED + "You don't have permission to use this command.");
            return;
        }

        if (args.length == 1) {
            Map<Material, Double> speedMap = plugin.getSpeedMultiplierMap();
            if (speedMap.isEmpty()) {
                sender.sendMessage(prefix + "There are no custom speed blocks configured.");
                return;
            }
            sender.sendMessage(ChatColor.GOLD + "--- SpeedTracks Configured Blocks ---");
            speedMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> sender.sendMessage(ChatColor.YELLOW + entry.getKey().name() + ": " + ChatColor.AQUA + entry.getValue() + "x"));
        } else if (args.length == 2) {
            try {
                Material material = Material.valueOf(args[1].toUpperCase());
                double multiplier = plugin.getSpeedMultiplierMap().getOrDefault(material, 1.0);
                sender.sendMessage(prefix + ChatColor.YELLOW + material.name() + ChatColor.GRAY + " has a speed multiplier of " + ChatColor.AQUA + multiplier + "x");
            } catch (IllegalArgumentException e) {
                sender.sendMessage(prefix + ChatColor.RED + "The block '" + args[1] + "' does not exist.");
            }
        } else {
            sender.sendMessage(prefix + "Usage: /st info [block_material]");
        }
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            if (sender.hasPermission("speedtracks.command.reload")) completions.add("reload");
            if (sender.hasPermission("speedtracks.command.info")) completions.add("info");
            return StringUtil.copyPartialMatches(args[0], completions, new ArrayList<>());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("info") && sender.hasPermission("speedtracks.command.info")) {
            List<String> materialNames = Arrays.stream(Material.values())
                    .filter(Material::isBlock)
                    .map(Enum::name)
                    .collect(Collectors.toList());
            return StringUtil.copyPartialMatches(args[1], materialNames, new ArrayList<>());
        }
        return new ArrayList<>();
    }
}