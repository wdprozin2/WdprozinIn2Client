# WdprozinIn2Client

A private, high-performance custom Minecraft client for advanced network analysis, protocol testing, and crash simulations (v6.2).

---

## ✨ Features & Enhancements

- **Integrated SOCKS5 Proxy**: Seamless Netty-level SOCKS5 proxy routing with optional username/password authentication, managed directly and exclusively from the in-game ClickGUI (**VPN / Proxy** section).
- **Extended Protocol Support (ViaVersion / ViaMCP)**: Comprehensive support for modern and legacy Minecraft protocols, now up to version **26.3**.
- **Automated GitHub Updates**: Built-in auto-updater querying the official repository (`wdprozin2/WdprozinIn2Client`) on startup or via `!update` command.
- **Discord Rich Presence (RPC)**: Official integration displaying live in-game status, custom application artwork (`logo`), and verified credentials (`wdprozin_`).
- **Bespoke UI & Styling**: Custom background aesthetics, modernized dark vibrancy interface, and full branding overhaul.

---

## 🎮 In-Game Commands

| Command | Description |
| :--- | :--- |
| `!help` | Displays all registered client commands. |
| `!update` | Checks GitHub Releases for new builds and downloads the latest `.jar`. |
| `!proxy` | Manage bot and auxiliary proxy settings. |
| `!crash` | Send or inspect crash payloads. |
| `!exploit` | Trigger specialized network exploit functions. |
| `!stop` | Abort all ongoing crasher threads and packet loops. |
| `!detect` | Probe server anti-crash protection engines. |
| `!fakegm` | Spoof game mode packets for the active session. |

> ℹ️ **Note on SOCKS5 Proxy**: To ensure stability and prevent conflicting configurations, SOCKS5 proxy settings (Host, Port, Username, Password) are configured exclusively via the **ClickGUI (VPN / Proxy)** menu and saved to `xynis_socks5.txt`.

---

## 🚀 How to Run

1. Launch using the provided startup script:
   ```cmd
   start.bat
   ```
2. If an update was downloaded by the Auto-Updater (`Xynis_update.jar`), `start.bat` will automatically replace the client with the updated version before launching.

---

## 👤 Author & Credits

- **Client Developer**: `wdprozin_` ([@wdprozin2](https://github.com/wdprozin2))
- **Official Repository**: [github.com/wdprozin2/WdprozinIn2Client](https://github.com/wdprozin2/WdprozinIn2Client)
