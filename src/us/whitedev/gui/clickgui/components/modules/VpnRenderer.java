package us.whitedev.gui.clickgui.components.modules;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import us.whitedev.helpers.Socks5Helper;
import us.whitedev.helpers.VpnHelper;

public class VpnRenderer {
    private final VpnHelper vpnHelper = VpnHelper.getInstance();
    private final Socks5Helper socks5 = Socks5Helper.getInstance();

    private final String inputStyle = "-fx-background-color: rgba(22, 22, 26, 0.95); "
            + "-fx-text-fill: #ffffff; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 8px; "
            + "-fx-border-color: rgba(255, 255, 255, 0.15); "
            + "-fx-border-radius: 8px; "
            + "-fx-padding: 8px 12px;";

    private final String primaryButtonStyle = "-fx-background-color: rgba(20, 40, 50, 0.9); "
            + "-fx-text-fill: #00fff6; "
            + "-fx-font-weight: bold; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 8px; "
            + "-fx-border-color: rgba(0, 255, 246, 0.4); "
            + "-fx-border-radius: 8px; "
            + "-fx-cursor: hand; "
            + "-fx-padding: 8px 16px;";

    private final String primaryButtonHoverStyle = "-fx-background-color: rgba(0, 255, 246, 0.25); "
            + "-fx-text-fill: #ffffff; "
            + "-fx-font-weight: bold; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 8px; "
            + "-fx-border-color: #00fff6; "
            + "-fx-border-radius: 8px; "
            + "-fx-cursor: hand; "
            + "-fx-padding: 8px 16px;";

    private final String secondaryButtonStyle = "-fx-background-color: rgba(30, 30, 35, 0.85); "
            + "-fx-text-fill: #ffffff; "
            + "-fx-font-weight: bold; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 8px; "
            + "-fx-border-color: rgba(255, 255, 255, 0.15); "
            + "-fx-border-radius: 8px; "
            + "-fx-cursor: hand; "
            + "-fx-padding: 8px 16px;";

    private final String secondaryButtonHoverStyle = "-fx-background-color: rgba(50, 50, 60, 0.95); "
            + "-fx-text-fill: #00fff6; "
            + "-fx-font-weight: bold; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 8px; "
            + "-fx-border-color: rgba(255, 255, 255, 0.3); "
            + "-fx-border-radius: 8px; "
            + "-fx-cursor: hand; "
            + "-fx-padding: 8px 16px;";

    private final String dangerButtonStyle = "-fx-background-color: rgba(50, 20, 20, 0.85); "
            + "-fx-text-fill: #ff6b6b; "
            + "-fx-font-weight: bold; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 8px; "
            + "-fx-border-color: rgba(255, 107, 107, 0.4); "
            + "-fx-border-radius: 8px; "
            + "-fx-cursor: hand; "
            + "-fx-padding: 8px 16px;";

    private final String dangerButtonHoverStyle = "-fx-background-color: rgba(80, 25, 25, 0.95); "
            + "-fx-text-fill: #ffffff; "
            + "-fx-font-weight: bold; "
            + "-fx-font-size: 13px; "
            + "-fx-background-radius: 8px; "
            + "-fx-border-color: #ff6b6b; "
            + "-fx-border-radius: 8px; "
            + "-fx-cursor: hand; "
            + "-fx-padding: 8px 16px;";

    public void renderOptions(VBox vBox) {
        // Main Title
        Label title = new Label("WdprozinIn2Client - Proxy & VPN");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 26.0));
        title.setStyle("-fx-text-fill: white; -fx-padding: 12 0 4 16;");
        vBox.getChildren().add(title);

        Line line = new Line(0.0, 0.0, 700.0, 0.0);
        line.setStrokeWidth(2.0);
        line.setStroke(Color.web("#00fff6"));
        vBox.getChildren().add(line);

        VBox container = new VBox(18.0);
        container.setPadding(new Insets(16.0, 20.0, 24.0, 20.0));

        // ================= SOCKS5 PROXY CARD =================
        VBox s5Card = new VBox(12.0);
        s5Card.setPadding(new Insets(16.0));
        s5Card.setStyle("-fx-background-color: rgba(18, 18, 22, 0.88); "
                + "-fx-background-radius: 12px; "
                + "-fx-border-color: rgba(255, 255, 255, 0.1); "
                + "-fx-border-radius: 12px;");

        Label s5Header = new Label("🛡️ SOCKS5 Proxy Configuration");
        s5Header.setFont(Font.font("Inter", FontWeight.BOLD, 17.0));
        s5Header.setStyle("-fx-text-fill: #ffffff;");

        Label s5Status = new Label();
        updateSocks5StatusLabel(s5Status);

        GridPane s5Form = new GridPane();
        s5Form.setHgap(12.0);
        s5Form.setVgap(10.0);

        Label hostLabel = new Label("Host / IP:");
        hostLabel.setStyle("-fx-text-fill: rgba(220, 220, 220, 0.9); -fx-font-weight: bold; -fx-font-size: 12px;");
        TextField hostField = new TextField(this.socks5.getHost() == null ? "" : this.socks5.getHost());
        hostField.setPromptText("e.g. 127.0.0.1 or proxy.example.com");
        hostField.setPrefWidth(260.0);
        hostField.setStyle(this.inputStyle);

        Label portLabel = new Label("Port:");
        portLabel.setStyle("-fx-text-fill: rgba(220, 220, 220, 0.9); -fx-font-weight: bold; -fx-font-size: 12px;");
        TextField portField = new TextField(this.socks5.getPort() > 0 ? String.valueOf(this.socks5.getPort()) : "1080");
        portField.setPromptText("e.g. 1080");
        portField.setPrefWidth(120.0);
        portField.setStyle(this.inputStyle);

        Label userLabel = new Label("Username (optional):");
        userLabel.setStyle("-fx-text-fill: rgba(220, 220, 220, 0.9); -fx-font-weight: bold; -fx-font-size: 12px;");
        TextField userField = new TextField(this.socks5.getUsername() == null ? "" : this.socks5.getUsername());
        userField.setPromptText("Proxy username");
        userField.setPrefWidth(260.0);
        userField.setStyle(this.inputStyle);

        Label passLabel = new Label("Password (optional):");
        passLabel.setStyle("-fx-text-fill: rgba(220, 220, 220, 0.9); -fx-font-weight: bold; -fx-font-size: 12px;");
        PasswordField passField = new PasswordField();
        passField.setText(this.socks5.getPassword() == null ? "" : this.socks5.getPassword());
        passField.setPromptText("Proxy password");
        passField.setPrefWidth(260.0);
        passField.setStyle(this.inputStyle);

        s5Form.add(hostLabel, 0, 0);
        s5Form.add(hostField, 0, 1);
        s5Form.add(portLabel, 1, 0);
        s5Form.add(portField, 1, 1);
        s5Form.add(userLabel, 0, 2);
        s5Form.add(userField, 0, 3);
        s5Form.add(passLabel, 1, 2);
        s5Form.add(passField, 1, 3);

        Label feedbackLabel = new Label();
        feedbackLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold;");

        HBox s5Buttons = new HBox(10.0);
        Button saveAndEnableBtn = new Button("Save & Enable");
        saveAndEnableBtn.setStyle(this.primaryButtonStyle);
        saveAndEnableBtn.setOnMouseEntered(ev -> saveAndEnableBtn.setStyle(this.primaryButtonHoverStyle));
        saveAndEnableBtn.setOnMouseExited(ev -> saveAndEnableBtn.setStyle(this.primaryButtonStyle));

        Button toggleBtn = new Button(this.socks5.isEnabled() ? "Disable Proxy" : "Enable Proxy");
        toggleBtn.setStyle(this.secondaryButtonStyle);
        toggleBtn.setOnMouseEntered(ev -> toggleBtn.setStyle(this.secondaryButtonHoverStyle));
        toggleBtn.setOnMouseExited(ev -> toggleBtn.setStyle(this.secondaryButtonStyle));

        Button disableBtn = new Button("Disconnect");
        disableBtn.setStyle(this.dangerButtonStyle);
        disableBtn.setOnMouseEntered(ev -> disableBtn.setStyle(this.dangerButtonHoverStyle));
        disableBtn.setOnMouseExited(ev -> disableBtn.setStyle(this.dangerButtonStyle));

        saveAndEnableBtn.setOnAction(e -> {
            String host = hostField.getText().trim();
            String portStr = portField.getText().trim();
            String user = userField.getText().trim();
            String pass = passField.getText();

            if (host.isEmpty()) {
                feedbackLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-size: 12px;");
                feedbackLabel.setText("❌ Host field cannot be empty!");
                return;
            }

            int port;
            try {
                port = Integer.parseInt(portStr);
                if (port <= 0 || port > 65535) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-size: 12px;");
                feedbackLabel.setText("❌ Invalid port! Enter a number between 1 and 65535.");
                return;
            }

            this.socks5.setProxy(host, port, user, pass);
            this.socks5.setEnabled(true);

            updateSocks5StatusLabel(s5Status);
            toggleBtn.setText("Disable Proxy");
            feedbackLabel.setStyle("-fx-text-fill: #55ff55; -fx-font-size: 12px;");
            feedbackLabel.setText("✅ SOCKS5 proxy saved & enabled: " + host + ":" + port + (this.socks5.hasAuth() ? " (with auth)" : ""));
        });

        toggleBtn.setOnAction(e -> {
            boolean newState = !this.socks5.isEnabled();
            this.socks5.setEnabled(newState);
            updateSocks5StatusLabel(s5Status);
            toggleBtn.setText(newState ? "Disable Proxy" : "Enable Proxy");
            if (newState) {
                feedbackLabel.setStyle("-fx-text-fill: #55ff55; -fx-font-size: 12px;");
                feedbackLabel.setText("✅ SOCKS5 proxy enabled!");
            } else {
                feedbackLabel.setStyle("-fx-text-fill: #ffaa00; -fx-font-size: 12px;");
                feedbackLabel.setText("⚠️ SOCKS5 proxy disabled.");
            }
        });

        disableBtn.setOnAction(e -> {
            this.socks5.setEnabled(false);
            updateSocks5StatusLabel(s5Status);
            toggleBtn.setText("Enable Proxy");
            feedbackLabel.setStyle("-fx-text-fill: #ff6b6b; -fx-font-size: 12px;");
            feedbackLabel.setText("🛑 Proxy disconnected.");
        });

        s5Buttons.getChildren().addAll(saveAndEnableBtn, toggleBtn, disableBtn);

        Label hintLabel = new Label("💡 Automatically saved to xynis_socks5.txt | Proxy is managed exclusively via this menu");
        hintLabel.setStyle("-fx-text-fill: rgba(160, 160, 160, 0.8); -fx-font-size: 11px;");

        s5Card.getChildren().addAll(s5Header, s5Status, s5Form, s5Buttons, feedbackLabel, hintLabel);

        // ================= VPN CARD =================
        VBox vpnCard = new VBox(10.0);
        vpnCard.setPadding(new Insets(16.0));
        vpnCard.setStyle("-fx-background-color: rgba(18, 18, 22, 0.88); "
                + "-fx-background-radius: 12px; "
                + "-fx-border-color: rgba(255, 255, 255, 0.1); "
                + "-fx-border-radius: 12px;");

        Label vpnHeader = new Label("🌐 Cloudflare WARP & IP Protection");
        vpnHeader.setFont(Font.font("Inter", FontWeight.BOLD, 17.0));
        vpnHeader.setStyle("-fx-text-fill: #ffffff;");

        Label vpnStatus = new Label("WARP VPN: " + (this.vpnHelper.isEnabled() ? "CONNECTED" : "DISCONNECTED"));
        vpnStatus.setStyle("-fx-text-fill: " + (this.vpnHelper.isEnabled() ? "#55ff55" : "rgba(180, 180, 180, 0.8)") + "; -fx-font-size: 13px; -fx-font-weight: bold;");

        Label ipLabel = new Label("Local IP: " + this.vpnHelper.getDisplayIp());
        ipLabel.setStyle("-fx-text-fill: rgba(220, 220, 220, 0.9); -fx-font-size: 13px;");

        Label extIpLabel = new Label("External IP: " + (this.vpnHelper.fetchExternalIp() == null ? "unknown" : this.vpnHelper.fetchExternalIp()));
        extIpLabel.setStyle("-fx-text-fill: rgba(220, 220, 220, 0.9); -fx-font-size: 13px;");

        HBox vpnButtons = new HBox(10.0);
        Button vpnToggle = new Button(this.vpnHelper.isEnabled() ? "Disconnect WARP" : "Connect WARP");
        vpnToggle.setStyle(this.secondaryButtonStyle);
        vpnToggle.setOnMouseEntered(ev -> vpnToggle.setStyle(this.secondaryButtonHoverStyle));
        vpnToggle.setOnMouseExited(ev -> vpnToggle.setStyle(this.secondaryButtonStyle));

        vpnToggle.setOnAction(e -> {
            boolean warp = this.vpnHelper.isWarpAvailable();
            if (!this.vpnHelper.isEnabled()) {
                if (warp) {
                    this.vpnHelper.connectWarp();
                } else {
                    this.vpnHelper.toggleEnabled();
                }
            } else if (warp) {
                this.vpnHelper.disconnectWarp();
            } else {
                this.vpnHelper.toggleEnabled();
            }
            vpnStatus.setText("WARP VPN: " + (this.vpnHelper.isEnabled() ? "CONNECTED" : "DISCONNECTED") + (this.vpnHelper.isWarpAvailable() ? " (WARP)" : ""));
            vpnStatus.setStyle("-fx-text-fill: " + (this.vpnHelper.isEnabled() ? "#55ff55" : "rgba(180, 180, 180, 0.8)") + "; -fx-font-size: 13px; -fx-font-weight: bold;");
            vpnToggle.setText(this.vpnHelper.isEnabled() ? "Disconnect WARP" : "Connect WARP");
            ipLabel.setText("Local IP: " + this.vpnHelper.getDisplayIp());
            String ext = this.vpnHelper.fetchExternalIp();
            extIpLabel.setText("External IP: " + (ext == null ? "unknown" : ext));
        });

        ToggleButton hideIpToggle = new ToggleButton(this.vpnHelper.isHideIp() ? "Show IP" : "Hide IP");
        hideIpToggle.setSelected(this.vpnHelper.isHideIp());
        hideIpToggle.setStyle(this.secondaryButtonStyle);
        hideIpToggle.setOnAction(e -> {
            this.vpnHelper.setHideIp(hideIpToggle.isSelected());
            hideIpToggle.setText(this.vpnHelper.isHideIp() ? "Show IP" : "Hide IP");
            ipLabel.setText("Local IP: " + this.vpnHelper.getDisplayIp());
        });

        vpnButtons.getChildren().addAll(vpnToggle, hideIpToggle);
        vpnCard.getChildren().addAll(vpnHeader, vpnStatus, ipLabel, extIpLabel, vpnButtons);

        container.getChildren().addAll(s5Card, vpnCard);
        vBox.getChildren().add(container);
    }

    private void updateSocks5StatusLabel(Label label) {
        if (this.socks5.isEnabled()) {
            label.setText("Status: ENABLED -> " + this.socks5.getHost() + ":" + this.socks5.getPort() + (this.socks5.hasAuth() ? " (auth: " + this.socks5.getUsername() + ")" : ""));
            label.setStyle("-fx-text-fill: #55ff55; -fx-font-size: 13px; -fx-font-weight: bold;");
        } else {
            label.setText("Status: DISABLED (Direct connection without proxy)");
            label.setStyle("-fx-text-fill: rgba(180, 180, 180, 0.7); -fx-font-size: 13px; -fx-font-weight: bold;");
        }
    }
}
