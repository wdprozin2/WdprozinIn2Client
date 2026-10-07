package us.whitedev.gui.clickgui.components.modules;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.jetbrains.annotations.NotNull;

public class WelcomeRenderer {
    private void renderTitle(VBox vBox) {
        Label title = new Label("WdprozinIn2Client - Changelog");
        title.setStyle("-fx-font-size: 27; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);-fx-padding: 15 0 0 15;");
        vBox.getChildren().add(title);
        Line line = new Line();
        line.setStartX(0.0);
        line.setEndX(700.0);
        line.setStrokeWidth(3.0);
        line.setStroke(Color.web("#cc00ff"));
        vBox.getChildren().add(line);
    }

    public void renderChangelog(VBox vBox) {
        this.renderTitle(vBox);
        Label welcomeLabel = new Label("Welcome on WdprozinIn2Client!");
        welcomeLabel.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);-fx-padding: 5 0 0 15;");
        vBox.getChildren().add(welcomeLabel);
        Label label1 = new Label("Exclusive Crash/Exploit for Minecraft 1.20.1");
        label1.setStyle("-fx-font-size: 18; -fx-text-fill: rgba(200, 200, 200);-fx-padding: -5 0 0 15;");
        vBox.getChildren().add(label1);
        Label label2 = new Label(String.format("Changelog for WdprozinIn2Client %s:", "6.2"));
        label2.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);-fx-padding: 5 0 0 15;");
        vBox.getChildren().add(label2);
        Label label3 = WelcomeRenderer.getClientLabel();
        vBox.getChildren().add(label3);
        Label label4 = new Label(String.format("Changelog for WdprozinIn2Client Proxy %s:", "2.0"));
        vBox.getChildren().add(label4);
        label4.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);-fx-padding: 5 0 0 15;");
        Label label5 = WelcomeRenderer.getProxyLabel();
        vBox.getChildren().add(label5);
    }

    @NotNull
    private static Label getClientLabel() {
        Label label3 = new Label("- Added 4 new Crash Methods\n- Added Support for DataComponents 1.20.5 - 1.21.4\n- Added DataComponents Support for NBT Crashers\n- Added/Reworked Packets in XynisProtocol\n- Added new 6 DataComponents\n- Fixed Hud Position\n- Auto Disable Switchable Modules after Disconnect\n- Improved censorship options\n- XynisProxy 2.0 Release\n");
        label3.setStyle("-fx-font-size: 18; -fx-text-fill: rgba(200, 200, 200);-fx-padding: 0 0 0 25;");
        return label3;
    }

    @NotNull
    private static Label getProxyLabel() {
        Label label5 = new Label("- Reworked proxy system\n- Reworked proxy commands\n- Reworked proxy handler\n");
        label5.setStyle("-fx-font-size: 18; -fx-text-fill: rgba(200, 200, 200);-fx-padding: 0 0 0 25;");
        return label5;
    }
}
