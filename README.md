# WdprozinIn2Client (v6.3)

![Version](https://img.shields.io/badge/version-6.3-pink.svg)
![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1%20%7C%201.21.1-blue.svg)
![Status](https://img.shields.io/badge/status-active-success.svg)
![Visibility](https://img.shields.io/badge/repository-public-brightgreen.svg)

A high-performance custom Minecraft client for advanced network analysis, protocol testing, packet simulation, and server stress research.

---

## ✨ Release Highlights & Features (v6.3)

- **Default Minecraft 1.21.1 Protocol**: Launches by default with protocol `1.21.1` (ViaLoadingBase 767) for out-of-the-box compatibility with modern Paper, Purpur, Spigot, and BungeeCord servers.
- **Apple HIG Elevated UI Design**: Complete redesign following Apple Human Interface Guidelines:
  - Frosted translucent dark acrylic styling (`#0d0e15`) with nested 18px radii.
  - Vibrant accent borders synchronized with the active theme.
  - 10px rounded navigation pills with clean active indicators.
  - Crisp typography using SF Pro & Inter font hierarchy.
- **Draggable Dynamic Island HUD Widget**:
  - Borderless translucent floating window (`StageStyle.TRANSPARENT`).
  - Real-time live metrics updated every 250ms:
    - **PLAYER**: Active account name (defaults to `Wdprozin1`).
    - **SERVER**: Live server IP / hostname (e.g. `pacmc.srvmc.com`).
    - **ENGINE**: Server brand detection (Paper, Purpur, Vanilla).
    - **XYZ**: Dynamic coordinates updating in real time.
    - **FPS**: Instantaneous framerate counter.
- **Clean Glyph Rendering**: Full Unicode emoji support without missing glyph square boxes (`[]`).
- **Integrated SOCKS5 Proxy**: Seamless Netty-level SOCKS5 proxy routing with optional authentication, managed exclusively through the in-game ClickGUI (**VPN / Proxy** section).
- **Microsoft OpenAuth**: Automated browser login workflow with clipboard detection and URL token parsing.
- **Discord Rich Presence (RPC)**: Live in-game presence integration (`WdprozinIn2Client 6.3`).
- **Automated GitHub Updates**: Built-in auto-updater querying the official repository (`wdprozin2/WdprozinIn2Client`) on startup or via `!update`.

---

## 🎮 In-Game Commands

| Command | Description |
| :--- | :--- |
| `!help` | Displays all registered client commands and usage syntax. |
| `!update` | Checks GitHub Releases for new builds and downloads the latest `.jar`. |
| `!proxy` | Manage bot and auxiliary proxy configurations. |
| `!crash` | Send or inspect crash payloads and stress testing methods. |
| `!exploit` | Trigger specialized network exploit functions. |
| `!stop` | Abort all ongoing crasher threads and packet loops. |
| `!detect` | Probe server anti-crash protection engines and packet limits. |
| `!fakegm` | Spoof game mode packets for the active session. |
| `!config` | Load, save, or switch client configuration profiles. |
| `!bypasslist` | View supported server anticheat and anti-exploit bypasses. |

> ℹ️ **Note on SOCKS5 Proxy**: Proxy connection settings (Host, Port, Username, Password) are configured in the **ClickGUI (Proxy)** tab and persisted safely to `xynis_socks5.txt`.

---

## 🚀 How to Run

1. Clone or download the release from the official repository:
   ```cmd
   git clone https://github.com/wdprozin2/WdprozinIn2Client.git
   ```
2. Launch using the provided startup script:
   ```cmd
   start.bat
   ```
3. `start.bat` automatically bootstraps the client with the default `Wdprozin1` user, loads local Mojang assets, and verifies update packages.

---

## 🔒 Build Verification & Release Integrity

Every official release published on GitHub Releases includes an authoritative SHA-256 cryptographic checksum. Always verify your `.jar` hash before execution:

```powershell
Get-FileHash -Algorithm SHA256 WdprozinIn2Client.jar
```

---

## 👤 Author & Credits

- **Client Developer**: `wdprozin_` ([@wdprozin2](https://github.com/wdprozin2))
- **Official Repository**: [github.com/wdprozin2/WdprozinIn2Client](https://github.com/wdprozin2/WdprozinIn2Client)
