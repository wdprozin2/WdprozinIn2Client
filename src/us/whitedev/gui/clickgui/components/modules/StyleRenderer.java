package us.whitedev.gui.clickgui.components.modules;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import us.whitedev.gui.clickgui.ClickGui;
import us.whitedev.gui.clickgui.utils.ColorSlider;
import us.whitedev.gui.clickgui.utils.StyleUtil;
import us.whitedev.gui.hud.HudGui;
import us.whitedev.gui.hud.utils.HudOptions;
import us.whitedev.utils.DiscordRP;

public class StyleRenderer {
    private Timeline timeline;
    private final String commonButtonStyle = "-fx-background-radius: 0;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;";
    private String commonButtonStyleHover = "-fx-background-radius: 15 15 15 15;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: rgba(0, 255, 246);";
    private Label shapeLabel;
    private ColorSlider shapeSlider;

    private void renderTitle(VBox vBox) {
        Label title = new Label("WdprozinIn2Client - GuiStyle");
        title.setStyle("-fx-font-size: 27; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);-fx-padding: 15 0 0 15;");
        vBox.getChildren().add(title);
        Line line = new Line();
        line.setStartX(0.0);
        line.setEndX(700.0);
        line.setStrokeWidth(3.0);
        line.setStroke(Color.web("#cc00ff"));
        vBox.getChildren().add(line);
    }

    public void renderOptions(VBox vBox) {
        this.renderTitle(vBox);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10.0, 0.0, 0.0, 20.0));
        grid.setHgap(10.0);
        grid.setVgap(10.0);
        Label welcomeLabel = new Label("Here you can Change the Design of Gui");
        welcomeLabel.setStyle("-fx-font-size: 20; -fx-text-fill: rgba(255, 255, 255);");
        grid.add(welcomeLabel, 0, 0);
        Label colorLabel = new Label("Main Gui Color: ");
        ChoiceBox<ClickGui.GuiStyle> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(ClickGui.GuiStyle.values());
        choiceBox.setPrefWidth(180.0);
        choiceBox.setValue(ClickGui.guiStyle);
        colorLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;-fx-text-fill: white;");
        grid.add(colorLabel, 0, 1);
        grid.add(choiceBox, 0, 2);
        this.shapeLabel = new Label("Gui Radius: " + StyleUtil.getShapeInt());
        this.shapeLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;-fx-text-fill: white;");
        this.shapeSlider = new ColorSlider(0, 30, StyleUtil.getShapeInt());
        this.shapeSlider.setMaxWidth(180.0);
        grid.add(this.shapeLabel, 1, 1);
        grid.add(this.shapeSlider, 1, 2);
        Label hudPlaceLabel = new Label("Hud Place:");
        hudPlaceLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;-fx-text-fill: white;");
        ChoiceBox<ClickGui.HudPlace> choiceBoxHud = new ChoiceBox<>();
        choiceBoxHud.getItems().addAll(ClickGui.HudPlace.values());
        choiceBoxHud.setPrefWidth(180.0);
        choiceBoxHud.setValue(ClickGui.hudPlace);
        grid.add(hudPlaceLabel, 2, 1);
        grid.add(choiceBoxHud, 2, 2);
        Label clientStyleLabel = new Label("Client Style:");
        clientStyleLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;-fx-text-fill: white;");
        ChoiceBox<ClickGui.ClientStyle> choiceBoxClientStyle = new ChoiceBox<>();
        choiceBoxClientStyle.getItems().addAll(ClickGui.ClientStyle.values());
        choiceBoxClientStyle.setPrefWidth(180.0);
        choiceBoxClientStyle.setValue(ClickGui.clientStyle);
        grid.add(clientStyleLabel, 0, 3);
        grid.add(choiceBoxClientStyle, 0, 4);
        Label discordLabel = new Label("DiscordRP Visibility:");
        discordLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;-fx-text-fill: white;");
        ChoiceBox<String> choiceBoxDiscord = new ChoiceBox<>();
        choiceBoxDiscord.getItems().addAll(List.of("Enabled", "Disabled"));
        choiceBoxDiscord.setPrefWidth(180.0);
        choiceBoxDiscord.setValue(DiscordRP.rpSwitch ? "Enabled" : "Disabled");
        grid.add(discordLabel, 1, 3);
        grid.add(choiceBoxDiscord, 1, 4);
        Label hudInfoLabel = new Label("Hud Info:");
        hudInfoLabel.setStyle("-fx-font-size: 16; -fx-font-weight: bold;-fx-text-fill: white;");
        ChoiceBox<String> hudInfoChoiceBox = new ChoiceBox<>();
        hudInfoChoiceBox.getItems().addAll(HudOptions.getMapKeys());
        hudInfoChoiceBox.setPrefWidth(180.0);
        grid.add(hudInfoLabel, 2, 3);
        grid.add(hudInfoChoiceBox, 2, 4);
        Button saveConfigButton = new Button("Save Config");
        saveConfigButton.setPrefWidth(180.0);
        saveConfigButton.setStyle("-fx-background-radius: 0;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
        saveConfigButton.setOnMouseEntered(e -> {
            this.timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(10.0), e2 -> saveConfigButton.setStyle(this.commonButtonStyleHover), new KeyValue[0])});
            this.timeline.setCycleCount(-1);
            this.timeline.play();
        });
        saveConfigButton.setOnMouseExited(e -> {
            this.timeline.stop();
            saveConfigButton.setStyle("-fx-background-radius: 0;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
        });
        this.initTimer();
        grid.add(saveConfigButton, 0, 6);
        saveConfigButton.setOnMouseClicked(e -> {
            ClickGui.guiStyle = choiceBox.getSelectionModel().getSelectedItem();
            ClickGui.hudPlace = choiceBoxHud.getSelectionModel().getSelectedItem();
            ClickGui.clientStyle = choiceBoxClientStyle.getSelectionModel().getSelectedItem();
            DiscordRP.rpSwitch = "Enabled".equalsIgnoreCase(choiceBoxDiscord.getSelectionModel().getSelectedItem());
        });
        Button infoVisButton = new Button("Change Info Visibility");
        infoVisButton.setPrefWidth(180.0);
        infoVisButton.setStyle("-fx-background-radius: 0;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
        infoVisButton.setOnMouseEntered(e -> {
            this.timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(10.0), e2 -> infoVisButton.setStyle(this.commonButtonStyleHover), new KeyValue[0])});
            this.timeline.setCycleCount(-1);
            this.timeline.play();
        });
        infoVisButton.setOnMouseExited(e -> {
            this.timeline.stop();
            infoVisButton.setStyle("-fx-background-radius: 0;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: white;");
        });
        this.initTimer();
        grid.add(infoVisButton, 1, 6);
        infoVisButton.setOnMouseClicked(e -> {
            String hudInfoName = hudInfoChoiceBox.getSelectionModel().getSelectedItem();
            if (hudInfoName != null) {
                HudOptions.setOptionValue(hudInfoName, !HudOptions.getOption(hudInfoName));
                HudGui.restartGUI();
            }
        });
        GridPane.setMargin(this.shapeLabel, new Insets(0.0, 0.0, 0.0, -140.0));
        GridPane.setMargin(this.shapeSlider, new Insets(0.0, 0.0, 0.0, -140.0));
        GridPane.setMargin(hudInfoLabel, new Insets(0.0, 0.0, 0.0, 30.0));
        GridPane.setMargin(hudInfoChoiceBox, new Insets(0.0, 0.0, 0.0, 30.0));
        GridPane.setMargin(discordLabel, new Insets(0.0, 0.0, 0.0, -140.0));
        GridPane.setMargin(choiceBoxDiscord, new Insets(0.0, 0.0, 0.0, -140.0));
        GridPane.setMargin(hudPlaceLabel, new Insets(0.0, 0.0, 0.0, 30.0));
        GridPane.setMargin(choiceBoxHud, new Insets(0.0, 0.0, 0.0, 30.0));
        GridPane.setMargin(infoVisButton, new Insets(0.0, 0.0, 0.0, -140.0));
        vBox.getChildren().add(grid);
    }

    private void initTimer() {
        Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(10.0), e -> {
            this.commonButtonStyleHover = "-fx-background-radius: 15 15 15 15;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: " + StyleUtil.getFinalColor();
            int shapeInt = (int)this.shapeSlider.getSliderValue();
            this.shapeLabel.setText("Gui Radius: " + (int)Math.round((double)shapeInt * 3.33));
            StyleUtil.setShapeInt(shapeInt);
        }, new KeyValue[0])});
        timeline.setCycleCount(-1);
        timeline.play();
    }
}
