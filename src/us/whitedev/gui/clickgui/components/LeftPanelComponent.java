/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javafx.animation.KeyFrame
 *  javafx.animation.KeyValue
 *  javafx.animation.Timeline
 *  javafx.geometry.Insets
 *  javafx.scene.Node
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
 *  javafx.scene.layout.Priority
 *  javafx.scene.layout.Region
 *  javafx.scene.layout.VBox
 *  javafx.scene.text.Text
 *  javafx.scene.text.TextAlignment
 *  javafx.scene.text.TextFlow
 *  javafx.util.Duration
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 */
package us.whitedev.gui.clickgui.components;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import us.whitedev.crashers.CrashManager;
import us.whitedev.exploits.ExploitManager;
import us.whitedev.gui.clickgui.components.RightPanelComponent;
import us.whitedev.gui.clickgui.utils.StyleUtil;

public class LeftPanelComponent {
    private Text title;
    private Text playTimeValue;
    private Text nickNameText;
    private Text nickNameValue;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private Timeline timeline;
    private String clickedStyle = "-fx-background-radius: 15 15 15 15;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(40, 255, 246);";
    private final String normalButtonStyle = "-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);";
    public static ClickedButtonType CLICKED_BUTTON = ClickedButtonType.CRASH;
    private final RightPanelComponent rightPanelComponent = new RightPanelComponent();

    private void createCrashComponents(VBox vBoxMain, VBox vBox2) {
        Button button = new Button("\ud83d\udca3 Crashers              ");
        vBoxMain.getChildren().add(button);
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
        button.setOnMouseClicked(e -> {
            button.setText("\ud83d\udca3 Crashers            | ");
            button.setStyle(this.clickedStyle);
            CLICKED_BUTTON = ClickedButtonType.CRASH;
            vBox2.getChildren().clear();
            this.rightPanelComponent.createCrashScene(vBox2, CrashManager.getManager().allMethods().split(", "));
        });
        this.timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis((double)10.0), e -> {
            if (CLICKED_BUTTON != ClickedButtonType.CRASH) {
                button.setText("\ud83d\udca3 Crashers              ");
                button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
            } else {
                button.setStyle(this.clickedStyle);
            }
        }, new KeyValue[0])});
        this.timeline.setCycleCount(-1);
        this.timeline.play();
    }

    private void createExploitComponents(VBox vBoxMain, VBox vBox2) {
        Button button = new Button("\ud83d\udd25 Exploits               ");
        vBoxMain.getChildren().add(button);
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
        button.setOnMouseClicked(e -> {
            button.setText("\ud83d\udd25 Exploits             | ");
            button.setStyle(this.clickedStyle);
            CLICKED_BUTTON = ClickedButtonType.EXPLOIT;
            vBox2.getChildren().clear();
            this.rightPanelComponent.createExploitScene(vBox2, ExploitManager.getManager().allExploits().split(", "));
        });
        this.timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis((double)10.0), e -> {
            if (CLICKED_BUTTON != ClickedButtonType.EXPLOIT) {
                button.setText("\ud83d\udd25 Exploits               ");
                button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
            } else {
                button.setStyle(this.clickedStyle);
            }
        }, new KeyValue[0])});
        this.timeline.setCycleCount(-1);
        this.timeline.play();
    }

    private void createAltLoginComponents(VBox vBoxMain, VBox vBox2) {
        Button button = new Button("\ud83d\udc64 AltManager         ");
        vBoxMain.getChildren().add(button);
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
        button.setOnMouseClicked(e -> {
            button.setText("\ud83d\udc64 AltManager      | ");
            button.setStyle(this.clickedStyle);
            CLICKED_BUTTON = ClickedButtonType.ALTMANAGER;
            vBox2.getChildren().clear();
            this.rightPanelComponent.createAltManagerScene(vBox2);
        });
        this.timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis((double)10.0), e -> {
            if (CLICKED_BUTTON != ClickedButtonType.ALTMANAGER) {
                button.setText("\ud83d\udc64 AltManager         ");
                button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
            } else {
                button.setStyle(this.clickedStyle);
            }
        }, new KeyValue[0])});
        this.timeline.setCycleCount(-1);
        this.timeline.play();
    }

    private void createBotComponents(VBox vBoxMain, VBox vBox2) {
        Button button = new Button("\u2694\ufe0f Hacks                   ");
        vBoxMain.getChildren().add(button);
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
        button.setOnMouseClicked(e -> {
            button.setText("\u2694\ufe0f Hacks                 | ");
            button.setStyle(this.clickedStyle);
            CLICKED_BUTTON = ClickedButtonType.HACKS;
            vBox2.getChildren().clear();
            this.rightPanelComponent.createHacksScene(vBox2);
        });
        this.timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis((double)10.0), e -> {
            if (CLICKED_BUTTON != ClickedButtonType.HACKS) {
                button.setText("\u2694\ufe0f Hacks                   ");
                button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
            } else {
                button.setStyle(this.clickedStyle);
            }
        }, new KeyValue[0])});
        this.timeline.setCycleCount(-1);
        this.timeline.play();
    }

    
    private void createProxyComponents(VBox vBoxMain, VBox vBox2) {
        Button button = new Button("\ud83d\udee1\ufe0f Proxy                    ");
        vBoxMain.getChildren().add(button);
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
        button.setOnMouseClicked(e -> {
            button.setText("\ud83d\udee1\ufe0f Proxy                  | ");
            button.setStyle(this.clickedStyle);
            CLICKED_BUTTON = ClickedButtonType.PROXY;
            vBox2.getChildren().clear();
            new us.whitedev.gui.clickgui.components.modules.VpnRenderer().renderOptions(vBox2);
        });
        Timeline tl = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis((double)10.0), e -> {
            if (CLICKED_BUTTON != ClickedButtonType.PROXY) {
                button.setText("\ud83d\udee1\ufe0f Proxy                    ");
                button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
            } else {
                button.setStyle(this.clickedStyle);
            }
        }, new KeyValue[0])});
        tl.setCycleCount(-1);
        tl.play();
    }

private void createStyleComponents(VBox vBoxMain, VBox vBox2) {
        Button button = new Button("\ud83c\udfa8 Style                    ");
        vBoxMain.getChildren().add(button);
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
        button.setOnMouseClicked(e -> {
            button.setText("\ud83c\udfa8 Style                  | ");
            button.setStyle(this.clickedStyle);
            CLICKED_BUTTON = ClickedButtonType.STYLE;
            vBox2.getChildren().clear();
            this.rightPanelComponent.createStyleScene(vBox2);
        });
        this.timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis((double)10.0), e -> {
            if (CLICKED_BUTTON != ClickedButtonType.STYLE) {
                button.setText("\ud83c\udfa8 Style                    ");
                button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
            } else {
                button.setStyle(this.clickedStyle);
            }
        }, new KeyValue[0])});
        this.timeline.setCycleCount(-1);
        this.timeline.play();
    }

    private void createVersionComponents(VBox vBoxMain, VBox vBox2) {
        Button button = new Button("\ud83d\udd27 Version               ");
        vBoxMain.getChildren().add(button);
        button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
        button.setOnMouseClicked(e -> {
            button.setText("\ud83d\udd27 Version             | ");
            button.setStyle(this.clickedStyle);
            CLICKED_BUTTON = ClickedButtonType.VERSION;
            vBox2.getChildren().clear();
            this.rightPanelComponent.createVersionScene(vBox2);
        });
        this.timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis((double)10.0), e -> {
            if (CLICKED_BUTTON != ClickedButtonType.VERSION) {
                button.setText("\ud83d\udd27 Version               ");
                button.setStyle("-fx-background-color: rgba(0, 0, 0, 0);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: rgba(255, 255, 255);");
            } else {
                button.setStyle(this.clickedStyle);
            }
        }, new KeyValue[0])});
        this.timeline.setCycleCount(-1);
        this.timeline.play();
    }

    private void createFreeSpace(VBox vBoxMain, VBox vBox2) {
        Region topEmptyRegion = new Region();
        VBox.setVgrow((Node)topEmptyRegion, (Priority)Priority.ALWAYS);
        vBoxMain.getChildren().add(topEmptyRegion);
        this.createCrashComponents(vBoxMain, vBox2);
        this.createExploitComponents(vBoxMain, vBox2);
        this.createAltLoginComponents(vBoxMain, vBox2);
        this.createVersionComponents(vBoxMain, vBox2);
        this.createBotComponents(vBoxMain, vBox2);
        this.createProxyComponents(vBoxMain, vBox2);
        this.createStyleComponents(vBoxMain, vBox2);
        Region bottomEmptyRegion = new Region();
        VBox.setVgrow((Node)bottomEmptyRegion, (Priority)Priority.ALWAYS);
        vBoxMain.getChildren().add(bottomEmptyRegion);
        bottomEmptyRegion.setPrefSize(200.0, 60.0);
    }

    private void createSmallInfo(VBox vBox) {
        TextFlow playTime = new TextFlow();
        playTime.setTextAlignment(TextAlignment.CENTER);
        Text playTimeText = new Text("PlayTime: ");
        this.playTimeValue = new Text("00:00:00");
        playTimeText.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-fill: white");
        this.playTimeValue.setStyle("-fx-font-size: 14; -fx-fill: " + StyleUtil.getFinalColor());
        playTime.getChildren().addAll(playTimeText, this.playTimeValue);
        TextFlow nickName = new TextFlow();
        nickName.setTextAlignment(TextAlignment.CENTER);
        this.nickNameText = new Text("Nickname: ");
        this.nickNameValue = new Text(Minecraft.getInstance().getUser().getName());
        nickName.getChildren().addAll(this.nickNameText, this.nickNameValue);
        VBox.setMargin((Node)nickName, (Insets)new Insets(0.0, 0.0, 10.0, 0.0));
        vBox.getChildren().add(playTime);
        vBox.getChildren().add(nickName);
        Timeline privTimeline = this.getTimeline();
        privTimeline.play();
    }

    @NotNull
    private Timeline getTimeline() {
        Timeline privTimeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds((double)1.0), e -> {
            if (this.seconds >= 59) {
                if (this.minutes >= 59) {
                    ++this.hours;
                    this.minutes = -1;
                }
                ++this.minutes;
                this.seconds = -1;
            }
            ++this.seconds;
            this.playTimeValue.setText(String.format("%02d:%02d:%02d", this.hours, this.minutes, this.seconds));
            this.nickNameValue.setText(Minecraft.getInstance().getUser().getName());
        }, new KeyValue[0])});
        privTimeline.setCycleCount(-1);
        return privTimeline;
    }

    public void createAllComponents(VBox vBoxMain, VBox vBox2) {
        TextFlow title = new TextFlow();
        title.setTextAlignment(TextAlignment.CENTER);
        Text xynisClientText = new Text("WdprozinIn2Client ");
        Text xynisVersionText = new Text("6.2");
        xynisClientText.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-fill: " + StyleUtil.getFinalColor());
        xynisVersionText.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-fill: white;");
        title.getChildren().addAll(xynisClientText, xynisVersionText);
        title.setStyle("-fx-padding: 0 0 0 0;");
        this.title = xynisClientText;
        Label subtitle = new Label("Exclusive Minecraft Crash/Exploit Client");
        subtitle.setStyle("-fx-font-size: 10; -fx-text-fill: rgba(140, 140, 140);");
        vBoxMain.getChildren().add(title);
        vBoxMain.getChildren().add(subtitle);
        VBox.setMargin((Node)title, (Insets)new Insets(15.0, 0.0, 0.0, 0.0));
        this.createFreeSpace(vBoxMain, vBox2);
        this.createSmallInfo(vBoxMain);
        this.initTimer();
    }

    private void initTimer() {
        Timeline privTimeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis((double)10.0), e -> {
            this.clickedStyle = "-fx-background-radius: 15 15 15 15;-fx-background-color: rgba(25, 25, 25);-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: " + StyleUtil.getFinalColor();
            this.title.setStyle("-fx-font-size: 22; -fx-font-weight: bold; -fx-fill: " + StyleUtil.getFinalColor());
            this.playTimeValue.setStyle("-fx-font-size: 14; -fx-fill: " + StyleUtil.getFinalColor());
            if (this.nickNameValue.getText().length() > 12) {
                this.nickNameText.setStyle("-fx-font-size: 11; -fx-font-weight: bold; -fx-fill: white");
                this.nickNameValue.setStyle("-fx-font-size: 11; -fx-fill: " + StyleUtil.getFinalColor());
            } else {
                this.nickNameText.setStyle("-fx-font-size: 14; -fx-font-weight: bold; -fx-fill: white");
                this.nickNameValue.setStyle("-fx-font-size: 14; -fx-fill: " + StyleUtil.getFinalColor());
            }
        }, new KeyValue[0])});
        privTimeline.setCycleCount(-1);
        privTimeline.play();
    }

    private static enum ClickedButtonType {
        CRASH,
        EXPLOIT,
        ALTMANAGER,
        STYLE,
        HACKS,
        VERSION,
        PROXY;

    }
}

