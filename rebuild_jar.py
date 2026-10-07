import zipfile, os, shutil

jar_path = r"C:\Users\joaol\OneDrive\Desktop\Xynis&.1\Xynis.jar"
temp_jar = r"C:\Users\joaol\OneDrive\Desktop\Xynis&.1\Xynis_rebuild_temp.jar"
bin_dir = r"C:\Users\joaol\.gemini\antigravity\scratch\bin"
scratch = r"C:\Users\joaol\.gemini\antigravity\scratch"
custom_bg = r"C:\Users\joaol\OneDrive\Desktop\Xynis_decompiled\resources\client\images\background.png"

# Also copy background.png to game run directory
game_dir = r"C:\Users\joaol\OneDrive\Desktop\Xynis&.1"
if os.path.exists(custom_bg):
    shutil.copy(custom_bg, os.path.join(game_dir, "background.png"))
    print("Copied background.png to game directory!")

# Map of in-jar relative paths to on-disk absolute paths
injections = {
    "com/viaversion/viaversion/api/data/MappingDataLoader.class": os.path.join(bin_dir, "MappingDataLoader.class"),
    "net/minecraft/network/Connection.class": os.path.join(bin_dir, "Connection.class"),
    "net/minecraft/client/gui/screens/TitleScreen.class": os.path.join(bin_dir, "TitleScreen.class"),
    "de/florianmichael/viamcp/gui/GuiProtocolSelector.class": os.path.join(bin_dir, "GuiProtocolSelector.class"),
    "us/whitedev/Main2PacketsClient.class": os.path.join(bin_dir, "Main2PacketsClient.class"),
    "assets/viarewind/data/mappings-1.8to1.7.10.nbt": os.path.join(scratch, "mappings-1.8to1.7.10.nbt"),
    "assets/viarewind/data/mappings-1.9.4to1.8.nbt": os.path.join(scratch, "mappings-1.9.4to1.8.nbt"),
    "client/images/background.png": custom_bg
}

# Add all compiled classes under bin/us
for root, dirs, files in os.walk(os.path.join(bin_dir, "us")):
    for f in files:
        if f.endswith(".class"):
            full_p = os.path.join(root, f)
            rel_p = os.path.relpath(full_p, bin_dir).replace("\\", "/")
            injections[rel_p] = full_p

print(f"Total injections: {len(injections)}")
for k in injections:
    print(" -", k)

src_z = zipfile.ZipFile(jar_path, "r")
out_z = zipfile.ZipFile(temp_jar, "w", compression=zipfile.ZIP_DEFLATED)

written = set()

for entry_name, file_path in injections.items():
    if os.path.exists(file_path):
        with open(file_path, "rb") as f:
            out_z.writestr(entry_name, f.read())
        written.add(entry_name)
    else:
        print("Warning: file not found for injection:", file_path)

for item in src_z.namelist():
    if item not in written:
        out_z.writestr(item, src_z.read(item))
        written.add(item)

src_z.close()
out_z.close()

# Verify
vz = zipfile.ZipFile(temp_jar, "r")
manifest = vz.read("META-INF/MANIFEST.MF").decode("utf-8", errors="ignore")
assert "Main-Class: net.minecraft.client.main.Main" in manifest

# Verify background.png size in JAR
bg_info = vz.getinfo("client/images/background.png")
print("Verified client/images/background.png size in JAR:", bg_info.file_size)
vz.close()

os.replace(temp_jar, jar_path)
print("Xynis.jar successfully rebuilt and verified!")
