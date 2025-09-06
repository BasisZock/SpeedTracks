# SpeedTracks

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Paper Version](https://img.shields.io/badge/Paper-1.21-blue.svg)](https://papermc.io/)
[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/basiszock/SpeedTracks) <!-- Change the link to your repo -->

**A powerful and intuitive PaperMC plugin to control minecart speeds using configurable track blocks.**

SpeedTracks allows you to design railway systems with dynamic speeds. By simply changing the block under a rail, you can create high-speed express lines, slow scenic routes, or special functional tracks. The system is managed through a clean and easy-to-use configuration file.

## Features

-   **🚀 Intuitive Speed Multipliers:** Configure speeds in a user-friendly way. `1.0` is normal speed, `2.0` is double speed, `0.5` is half speed.
-   **✅ Simple by Design:** No complex setup. Place a block, put a rail on it, and watch your minecarts adjust their speed automatically.
-   **⚡ High Performance:** Built for the Paper API to be lightweight with minimal impact on server performance.
-   **ℹ️ Informative Commands:** Use `/st info` to view all custom speed blocks or check the multiplier for a specific block.
-   **🔄 Live Reloading:** Update your configuration on the fly with the `/st reload` command. No server restart needed.

## Installation

1.  Download the latest release from the [Releases page](https://github.com/basiszock/SpeedTracks/releases).
2.  Place the `SpeedTracks-X.X.X.jar` file into your **Paper 1.21+** server's `plugins/` directory.
3.  Start your server. The default configuration file will be generated at `plugins/SpeedTracks/config.yml`.

## Configuration (`config.yml`)

The configuration is designed to be as simple as possible.

```yaml
# ------------------------------------------------------------------ #
#                  Configuration for SpeedTracks                     #
# ------------------------------------------------------------------ #

# Define custom speed multipliers for minecarts based on the block
# directly beneath the rail.
#
# The value is a multiplier of Minecraft's default minecart speed.
# 1.0 = Default speed (100%)
# 2.0 = Double speed (200%)
# 0.5 = Half speed (50%)
# 0.0 = Stops the minecart
speed-blocks:
  # Examples for faster travel
  GOLD_BLOCK: 2.0
  DIAMOND_BLOCK: 3.0
  EMERALD_BLOCK: 4.0
  NETHERITE_BLOCK: 6.0

  # Examples for slower travel
  SOUL_SAND: 0.5
  SLIME_BLOCK: 0.25

  # Example for a full stop
  OBSIDIAN: 0.0
```

## Commands & Permissions

The main command is `/speedtracks`, with a convenient alias `/st`.

| Command                     | Description                                            | Permission                  | Default    |
| --------------------------- | ------------------------------------------------------ | --------------------------- | ---------- |
| `/st reload`                | Reloads the `config.yml` from disk.                    | `speedtracks.command.reload`| `op`       |
| `/st info`                  | Lists all configured blocks and their speed multipliers. | `speedtracks.command.info`  | `everyone` |
| `/st info <block_material>` | Shows the speed multiplier for a specific block.         | `speedtracks.command.info`  | `everyone` |

## Building from Source

To build SpeedTracks yourself, you'll need JDK 17+ and Git.

```bash
# Clone the repository
git clone https://github.com/basiszock/SpeedTracks.git
cd SpeedTracks

# Build using the Gradle wrapper
./gradlew build
```

The compiled plugin will be located in the `build/libs/` directory.

## License

This project is licensed under the **GNU General Public License v3.0**. See the [LICENSE](LICENSE) file for more details.
