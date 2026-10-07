package us.whitedev.updater;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import us.whitedev.helpers.MessageHelper;

public class AutoUpdater {
    private static AutoUpdater instance;
    public static final String CURRENT_VERSION = "6.2";
    public static final String OFFICIAL_REPO = "wdprozin2/WdprozinIn2Client";
    private String repo = OFFICIAL_REPO;
    private boolean checkOnStart = true;
    private final File configFile = new File("xynis_updater.txt");
    private final MessageHelper messageHelper = new MessageHelper();
    private boolean isUpdating = false;

    private AutoUpdater() {
        this.loadConfig();
    }

    public static synchronized AutoUpdater getInstance() {
        if (instance == null) {
            instance = new AutoUpdater();
        }
        return instance;
    }

    public void loadConfig() {
        if (!this.configFile.exists()) {
            this.saveConfig();
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(this.configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] parts = line.split("=", 2);
                if (parts.length != 2) continue;
                String key = parts[0].trim().toLowerCase();
                String val = parts[1].trim();
                if (key.equals("repo") || key.equals("github_repo")) {
                    this.repo = val;
                } else if (key.equals("check_on_start")) {
                    this.checkOnStart = Boolean.parseBoolean(val);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(this.configFile))) {
            writer.println("# WdprozinIn2Client AutoUpdater Configuration");
            writer.println("# Defina seu repositorio do GitHub no formato dono/repositorio");
            writer.println("repo=" + this.repo);
            writer.println("check_on_start=" + this.checkOnStart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkOnStartup() {
        if (this.checkOnStart) {
            CompletableFuture.runAsync(() -> {
                try {
                    Thread.sleep(5000L); // Wait for chat/client to initialize
                    this.checkAndUpdate(false);
                } catch (Exception ignored) {}
            });
        }
    }

    public void checkAndUpdate(boolean userTriggered) {
        if (this.isUpdating) {
            if (userTriggered) {
                this.messageHelper.sendMessage("&eJa existe uma verificacao ou download de atualizacao em andamento!", true);
            }
            return;
        }
        this.isUpdating = true;
        if (userTriggered) {
            this.messageHelper.sendMessage("&7Buscando atualizacoes no GitHub (&b" + this.repo + "&7)...", true);
        }

        CompletableFuture.runAsync(() -> {
            try {
                String apiUrl = "https://api.github.com/repos/" + this.repo + "/releases/latest";
                HttpURLConnection conn = (HttpURLConnection) new URL(apiUrl).openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("User-Agent", "WdprozinIn2Client-Updater");
                conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
                conn.setConnectTimeout(8000);
                conn.setReadTimeout(12000);

                int responseCode = conn.getResponseCode();
                if (responseCode == 404) {
                    if (userTriggered) {
                        this.messageHelper.sendMessage("&cNenhuma release encontrada no repositorio &f" + this.repo + "&c!", true);
                        this.messageHelper.sendMessage("&7Configure o repositorio correto no arquivo &exynis_updater.txt&7.", true);
                    }
                    this.isUpdating = false;
                    return;
                } else if (responseCode != 200) {
                    if (userTriggered) {
                        this.messageHelper.sendMessage("&cErro ao verificar atualizacoes (HTTP " + responseCode + ").", true);
                    }
                    this.isUpdating = false;
                    return;
                }

                InputStream is = conn.getInputStream();
                String jsonStr = new String(is.readAllBytes());
                is.close();

                JsonObject json = JsonParser.parseString(jsonStr).getAsJsonObject();
                String tagName = json.has("tag_name") ? json.get("tag_name").getAsString() : "";
                String cleanTag = tagName.startsWith("v") || tagName.startsWith("V") ? tagName.substring(1) : tagName;

                if (cleanTag.isEmpty() || cleanTag.equalsIgnoreCase(CURRENT_VERSION)) {
                    if (userTriggered) {
                        this.messageHelper.sendMessage("&aVoce ja esta usando a versao mais recente (&f" + CURRENT_VERSION + "&a)!", true);
                    }
                    this.isUpdating = false;
                    return;
                }

                this.messageHelper.sendMessage("&aNova versao encontrada: &e" + tagName + " &a(atual: &7" + CURRENT_VERSION + "&a)!", true);

                // Find JAR asset
                String downloadUrl = null;
                String assetName = null;
                if (json.has("assets")) {
                    JsonArray assets = json.getAsJsonArray("assets");
                    for (JsonElement el : assets) {
                        JsonObject asset = el.getAsJsonObject();
                        String name = asset.get("name").getAsString();
                        if (name.endsWith(".jar")) {
                            downloadUrl = asset.get("browser_download_url").getAsString();
                            assetName = name;
                            break;
                        }
                    }
                }

                if (downloadUrl == null) {
                    this.messageHelper.sendMessage("&eA release possui novidades, mas nenhum arquivo .jar foi anexado para download.", true);
                    if (json.has("html_url")) {
                        this.messageHelper.sendMessage("&7Acesse: &b" + json.get("html_url").getAsString(), true);
                    }
                    this.isUpdating = false;
                    return;
                }

                this.messageHelper.sendMessage("&7Baixando atualizacao (&b" + assetName + "&7)...", true);
                File targetFile = new File("Xynis_update.jar");

                HttpURLConnection dlConn = (HttpURLConnection) new URL(downloadUrl).openConnection();
                dlConn.setRequestProperty("User-Agent", "WdprozinIn2Client-Updater");
                dlConn.setInstanceFollowRedirects(true);
                dlConn.connect();

                // Handle redirects manually if needed
                int dlCode = dlConn.getResponseCode();
                if (dlCode == 301 || dlCode == 302 || dlCode == 303) {
                    String newUrl = dlConn.getHeaderField("Location");
                    dlConn = (HttpURLConnection) new URL(newUrl).openConnection();
                    dlConn.setRequestProperty("User-Agent", "WdprozinIn2Client-Updater");
                    dlConn.connect();
                }

                InputStream in = dlConn.getInputStream();
                FileOutputStream out = new FileOutputStream(targetFile);
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
                out.close();
                in.close();

                this.messageHelper.sendMessage("&aAtualizacao baixada com sucesso!", true);
                this.messageHelper.sendMessage("&eReinicie o jogo pelo start.bat para aplicar a nova versao!", true);
            } catch (Exception e) {
                if (userTriggered) {
                    this.messageHelper.sendMessage("&cErro ao baixar atualizacao: &f" + e.getMessage(), true);
                }
                e.printStackTrace();
            } finally {
                this.isUpdating = false;
            }
        });
    }

    public String getRepo() {
        return this.repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
        this.saveConfig();
    }
}
