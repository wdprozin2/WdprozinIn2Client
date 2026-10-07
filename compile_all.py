import os, subprocess, zipfile, shutil

jar_path = r"C:\Users\joaol\OneDrive\Desktop\Xynis&.1\Xynis.jar"
scratch = r"C:\Users\joaol\.gemini\antigravity\scratch"
bin_dir = os.path.join(scratch, "bin")
os.makedirs(bin_dir, exist_ok=True)

javac = r"C:\Program Files\Eclipse Adoptium\jdk-21.0.12.8-hotspot\bin\javac.exe"
java = r"C:\Program Files\Eclipse Adoptium\jdk-21.0.12.8-hotspot\bin\java.exe"

# 1. Compile Java files
files_to_compile = [
    os.path.join(scratch, "src", "us", "whitedev", "helpers", "Socks5Helper.java"),
    os.path.join(scratch, "src", "us", "whitedev", "helpers", "MessageHelper.java"),
    os.path.join(scratch, "src", "us", "whitedev", "commands", "impl", "Socks5Command.java"),
    os.path.join(scratch, "src", "us", "whitedev", "commands", "impl", "HelpCommand.java"),
    os.path.join(scratch, "src", "us", "whitedev", "proxy", "functions", "SessionCreator.java"),
    os.path.join(scratch, "src", "us", "whitedev", "utils", "DiscordRP.java"),
    os.path.join(scratch, "src", "us", "whitedev", "gui", "clickgui", "utils", "Section.java"),
    os.path.join(scratch, "src", "us", "whitedev", "gui", "clickgui", "components", "modules", "VpnRenderer.java"),
    os.path.join(scratch, "src", "us", "whitedev", "gui", "clickgui", "components", "modules", "StyleRenderer.java"),
    os.path.join(scratch, "src", "us", "whitedev", "gui", "clickgui", "components", "modules", "WelcomeRenderer.java"),
    os.path.join(scratch, "src", "us", "whitedev", "gui", "clickgui", "components", "LeftPanelComponent.java"),
    os.path.join(scratch, "src", "us", "whitedev", "gui", "clickgui", "ClickGui.java"),
    os.path.join(scratch, "src", "us", "whitedev", "updater", "AutoUpdater.java"),
    os.path.join(scratch, "src", "us", "whitedev", "commands", "impl", "UpdateCommand.java"),
    os.path.join(scratch, "src", "us", "whitedev", "security", "LicenseManager.java"),
    os.path.join(scratch, "src", "us", "whitedev", "Main2PacketsClient.java")
]

print("Compiling modified source files...")
cmd = [javac, "-cp", f"{bin_dir};{jar_path}", "-source", "17", "-target", "17", "-proc:none", "-d", bin_dir] + files_to_compile
res = subprocess.run(cmd, capture_output=True, text=True)
print("Javac exit:", res.returncode)
if res.stderr:
    print("Javac warnings/errors:\n", res.stderr)
assert res.returncode == 0, "Compilation failed!"

print("All Java files compiled successfully!")
