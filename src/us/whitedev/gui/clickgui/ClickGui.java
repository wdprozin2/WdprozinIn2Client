package us.whitedev.gui.clickgui;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.Main;
import us.whitedev.gui.clickgui.components.AltManagerComponent;
import us.whitedev.gui.clickgui.components.ModuleSelectorComponent;
import us.whitedev.gui.clickgui.components.RightPanelComponent;
import us.whitedev.gui.clickgui.components.StyleComponent;
import us.whitedev.gui.clickgui.components.VersionComponent;
import us.whitedev.gui.clickgui.components.WelcomeComponent;
import us.whitedev.gui.clickgui.components.modules.HacksRenderer;
import us.whitedev.gui.clickgui.components.modules.VpnRenderer;
import us.whitedev.gui.clickgui.utils.ColorTheme;
import us.whitedev.gui.clickgui.utils.Section;
import us.whitedev.gui.hud.HudGui;

public class ClickGui
extends Application {
    private static final Minecraft mc = Minecraft.getInstance();
    private static Stage stage;
    private static VBox selectedMenuItem;
    private static Label playTimeLabel;
    private static int playTimeSeconds;
    private static double xOffset;
    private static double yOffset;
    public static String CURRENT_THEME_COLOR;
    public static String CURRENT_THEME_COLOR_LIGHT;
    private static String currentThemeColorAlpha;
    private static String currentThemeColorAlphaHover;
    private static VBox mainContentPanel;
    private static StackPane borderContainer;
    private static VBox logoSection;
    private static Label nickname;
    public static boolean IS_FOCUSED;
    public static boolean IS_VISIBLE;
    public static HudStyle HUD_STYLE;
    public static ColorTheme COLOR_THEME;
    public static HudPlace HUD_PLACE;
    public static ClientStyle CLIENT_STYLE;
    public static boolean isFocused;
    public static boolean isVisible;
    public static GuiStyle guiStyle;
    public static ClientStyle clientStyle;
    public static HudPlace hudPlace;
    private static final ModuleSelectorComponent moduleComponent;
    private static final AltManagerComponent altManagerComponent;
    private static final WelcomeComponent welcomeComponent;
    private static final VersionComponent versionComponent;
    private static final StyleComponent styleComponent;
    private static final HacksRenderer hacksRenderer;

    public void start(Stage primaryStage) {
        stage = primaryStage;
        ClickGui.showGUI();
    }

    private static void showGUI() {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: #0b0b0b;");
        HBox mainContainer = ClickGui.createMainContainer();
        borderContainer = new StackPane();
        borderContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);-fx-border-color: " + CURRENT_THEME_COLOR + ";-fx-border-width: 2px;-fx-border-radius: 25px;-fx-background-radius: 25px;");
        borderContainer.setPadding(new Insets(0.0));
        borderContainer.getChildren().add(mainContainer);
        root.getChildren().add(borderContainer);
        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        Scene scene = new Scene((Parent)root, 1000.0, 650.0);
        scene.setFill((Paint)Color.web("#0b0b0b"));
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        try {
            InputStream inputStream = Main.class.getResourceAsStream("/client/GuiStyle.css");
            if (inputStream != null) {
                Path tempFile = Files.createTempFile("GuiStyle", ".css", new FileAttribute[0]);
                Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
                scene.getStylesheets().add(tempFile.toUri().toString());
            }
        }
        catch (Exception ignored) {
            System.err.println("Cannot find GuiStyle.css");
        }
        ClickGui.setThemeColor(COLOR_THEME);
        ClickGui.refreshThemeColors();
        ClickGui.switchToSection(null);
        ClickGui.startPlayTimeTimer();
        ClickGui.initTimer();
        HudGui.initGui();
    }

    private static HBox createMainContainer() {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(0.0);
        VBox sidebar = ClickGui.createSidebar();
        VBox mainContent = ClickGui.createMainContent();
        container.getChildren().addAll(sidebar, mainContent);
        return container;
    }

    private static VBox createSidebar() {
        VBox sidebar = new VBox();
        sidebar.setPrefWidth(240.0);
        sidebar.setStyle("-fx-background-color: rgba(10, 10, 10, 0.9);-fx-background-radius: 12px 0 0 12px;");
        sidebar.setPadding(new Insets(25.0));
        sidebar.setSpacing(18.0);
        logoSection = ClickGui.createLogoSection();
        VBox navigation = ClickGui.createNavigation();
        VBox.setVgrow(navigation, Priority.ALWAYS);
        VBox footerSection = ClickGui.createSidebarFooter();
        sidebar.getChildren().addAll(logoSection, navigation, footerSection);
        return sidebar;
    }

    private static VBox createLogoSection() {
        VBox logoSection = new VBox();
        logoSection.setAlignment(Pos.CENTER);
        logoSection.setSpacing(3.0);
        Label title = new Label("WdprozinIn2Client ");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 20.0));
        title.setStyle("-fx-text-fill: linear-gradient(to right, " + CURRENT_THEME_COLOR + ", " + CURRENT_THEME_COLOR_LIGHT + ");");
        Text versionText = new Text("6.2");
        versionText.setFont(Font.font("Inter", FontWeight.BOLD, 20.0));
        versionText.setStyle("-fx-fill: white;");
        TextFlow titleFlow = new TextFlow(title, versionText);
        titleFlow.setTextAlignment(TextAlignment.CENTER);
        titleFlow.setLineSpacing(0.0);
        titleFlow.setPrefWidth(-1.0);
        titleFlow.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(titleFlow, new Insets(0.0, 0.0, 0.0, 0.0));
        titleFlow.setStyle("-fx-text-alignment: center;");
        Label subtitle = new Label("Exclusive Minecraft Crash/Exploit Client");
        subtitle.setFont(Font.font("Inter", 10.0));
        subtitle.setStyle("-fx-text-fill: #9ca3af;");
        logoSection.getChildren().addAll(titleFlow, subtitle);
        return logoSection;
    }

    private static VBox createNavigation() {
        VBox navigation = new VBox();
        navigation.setSpacing(8.0);
        navigation.setAlignment(Pos.TOP_LEFT);
        Section[] sections = Section.values();
        for (int i = 0; i < sections.length; ++i) {
            Section section = sections[i];
            VBox menuItem = ClickGui.createMenuItem(section.getDisplayName(), section, i == 0);
            navigation.getChildren().add(menuItem);
        }
        return navigation;
    }

    private static VBox createMenuItem(String text, Section section, boolean isActive) {
        VBox menuItem = new VBox();
        menuItem.setPadding(new Insets(10.0));
        menuItem.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label(text);
        label.setFont(Font.font("Inter", FontWeight.BOLD, 13.0));
        label.setStyle("-fx-text-fill: white;");
        if (isActive) {
            menuItem.setStyle("-fx-background-color: " + currentThemeColorAlpha + ";-fx-background-radius: 6px;-fx-border-color: " + CURRENT_THEME_COLOR + ";-fx-border-width: 0 0 0 2px;");
            selectedMenuItem = menuItem;
        } else {
            menuItem.setStyle("-fx-background-color: transparent;-fx-background-radius: 6px;");
        }
        menuItem.setOnMouseEntered(e -> {
            if (menuItem != selectedMenuItem) {
                menuItem.setStyle("-fx-background-color: " + currentThemeColorAlphaHover + ";-fx-background-radius: 6px;");
            }
        });
        menuItem.setOnMouseExited(e -> {
            if (menuItem != selectedMenuItem) {
                menuItem.setStyle("-fx-background-color: transparent;-fx-background-radius: 6px;");
            }
        });
        menuItem.setOnMouseClicked(e -> {
            if (selectedMenuItem != null) {
                selectedMenuItem.setStyle("-fx-background-color: transparent;-fx-background-radius: 6px;");
            }
            selectedMenuItem = menuItem;
            menuItem.setStyle("-fx-background-color: " + currentThemeColorAlpha + ";-fx-background-radius: 6px;-fx-border-color: " + CURRENT_THEME_COLOR + ";-fx-border-width: 0 0 0 2px;");
            ClickGui.switchToSection(section);
        });
        menuItem.getChildren().add(label);
        return menuItem;
    }

    private static VBox createSidebarFooter() {
        VBox footer = new VBox();
        footer.setAlignment(Pos.CENTER);
        footer.setSpacing(3.0);
        footer.setStyle("-fx-border-color: #374151; -fx-border-width: 1 0 0 0;");
        footer.setPadding(new Insets(15.0, 0.0, 0.0, 0.0));
        playTimeLabel = new Label("PlayTime: 00:00:00");
        playTimeLabel.setFont(Font.font("Inter", 12.0));
        playTimeLabel.setStyle("-fx-text-fill: #9ca3af;");
        nickname = new Label("Name: " + mc.getUser().getName());
        nickname.setFont(Font.font("Inter", 12.0));
        nickname.setStyle("-fx-text-fill: #9ca3af;");
        footer.getChildren().addAll(playTimeLabel, nickname);
        return footer;
    }

    private static VBox createMainContent() {
        VBox mainContent = new VBox();
        mainContent.setPadding(new Insets(25.0));
        mainContent.setSpacing(20.0);
        HBox.setHgrow(mainContent, Priority.ALWAYS);
        mainContent.setStyle("-fx-background-repeat: stretch;-fx-background-size: cover;-fx-background-position: center center;");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setStyle("-fx-background-color: rgba(25, 25, 25, 0.8);-fx-background-radius: 12px;");
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        mainContentPanel = new VBox();
        mainContentPanel.setStyle("-fx-background-color: rgba(20, 20, 20, 0.95);-fx-padding: 25px; -fx-background-radius: 12px;");
        mainContentPanel.setSpacing(25.0);
        scrollPane.setContent(mainContentPanel);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        mainContent.getChildren().add(scrollPane);
        return mainContent;
    }

    public static void switchToSection(Section section) {
        VBox currentSection;
        mainContentPanel.getChildren().clear();
        if (section != null) {
            currentSection = switch (section) {
                case CRASHERS, EXPLOITS -> moduleComponent.createModulesSection(section);
                case ALT_MANAGER -> altManagerComponent.createAltManagerSection("Waiting on action...");
                case VERSIONS -> versionComponent.createVersionComponent();
                case HACKS -> {
                    VBox v = new VBox();
                    new RightPanelComponent().createHacksScene(v);
                    yield v;
                }
                case PROXY -> {
                    VBox v = new VBox();
                    new VpnRenderer().renderOptions(v);
                    yield v;
                }
                case STYLES -> styleComponent.createStyleSettingsComponent();
                default -> throw new IncompatibleClassChangeError();
            };
        } else {
            currentSection = welcomeComponent.createWelcomeComponent();
        }
        if (currentSection != null) {
            mainContentPanel.getChildren().add(currentSection);
        }
    }

    public static void renderCustomSection(VBox currentSection) {
        if (currentSection != null) {
            mainContentPanel.getChildren().clear();
            mainContentPanel.getChildren().add(currentSection);
        }
    }

    private static void startPlayTimeTimer() {
        Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.seconds(1.0), e -> {
            int hours = ++playTimeSeconds / 3600;
            int minutes = playTimeSeconds % 3600 / 60;
            int seconds = playTimeSeconds % 60;
            String timeString = String.format("PlayTime: %02d:%02d:%02d", hours, minutes, seconds);
            playTimeLabel.setText(timeString);
            nickname.setText("Name: " + mc.getUser().getName());
        }, new KeyValue[0])});
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public static void setThemeColor(ColorTheme theme) {
        CURRENT_THEME_COLOR = theme.getPrimary();
        CURRENT_THEME_COLOR_LIGHT = theme.getLight();
        currentThemeColorAlpha = theme.getAlpha();
        currentThemeColorAlphaHover = theme.getAlphaHover();
    }

    private static void refreshThemeColors() {
        if (borderContainer != null) {
            borderContainer.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8);-fx-border-color: " + CURRENT_THEME_COLOR + ";-fx-border-width: 2px;-fx-border-radius: 15px;-fx-background-radius: 13px;");
        }
        ClickGui.refreshLogoSection();
        ClickGui.refreshSelectedMenuItem();
    }

    private static void refreshLogoSection() {
        if (logoSection != null) {
            Label title = (Label)((TextFlow)logoSection.getChildren().get(0)).getChildren().get(0);
            title.setStyle("-fx-text-fill: linear-gradient(to right, " + CURRENT_THEME_COLOR + ", " + CURRENT_THEME_COLOR_LIGHT + ");");
        }
    }

    private static void refreshSelectedMenuItem() {
        if (selectedMenuItem != null) {
            selectedMenuItem.setStyle("-fx-background-color: " + currentThemeColorAlpha + ";-fx-background-radius: 6px;-fx-border-color: " + CURRENT_THEME_COLOR + ";-fx-border-width: 0 0 0 2px;");
        }
    }

    private static void initTimer() {
        Timeline timeline = new Timeline(new KeyFrame[]{new KeyFrame(Duration.millis(10.0), e -> {
            ClickGui.refreshThemeColors();
            IS_FOCUSED = stage.isFocused();
        }, new KeyValue[0])});
        timeline.setCycleCount(-1);
        timeline.play();
    }

    private static void hideGUI() {
        stage.close();
    }

    public static void restartGUI() {
        Platform.runLater(() -> {
            if (stage.isShowing()) {
                stage.toFront();
            } else {
                ClickGui.hideGUI();
                ClickGui.showGUI();
            }
        });
    }

    public static void initGui() {
        if (stage != null) {
            ClickGui.restartGUI();
        } else {
            ClickGui.launch(new String[0]);
        }
    }

    static {
        selectedMenuItem = null;
        playTimeSeconds = 0;
        xOffset = 0.0;
        yOffset = 0.0;
        CURRENT_THEME_COLOR = "#ec4899";
        CURRENT_THEME_COLOR_LIGHT = "#f9a8d4";
        currentThemeColorAlpha = "rgba(236, 72, 153, 0.25)";
        currentThemeColorAlphaHover = "rgba(236, 72, 153, 0.15)";
        IS_FOCUSED = false;
        IS_VISIBLE = true;
        HUD_STYLE = HudStyle.RAINBOW;
        COLOR_THEME = ColorTheme.PINK;
        HUD_PLACE = HudPlace.LEFT;
        CLIENT_STYLE = ClientStyle.XYNIS;
        isFocused = IS_FOCUSED;
        isVisible = IS_VISIBLE;
        guiStyle = GuiStyle.DEFAULT;
        clientStyle = CLIENT_STYLE;
        hudPlace = HUD_PLACE;
        moduleComponent = new ModuleSelectorComponent();
        altManagerComponent = new AltManagerComponent();
        welcomeComponent = new WelcomeComponent();
        versionComponent = new VersionComponent();
        styleComponent = new StyleComponent();
        hacksRenderer = new HacksRenderer();
    }

    public static enum HudStyle {
        RAINBOW,
        DEFAULT,
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        VIOLET;
    }

    public static enum HudPlace {
        LEFT,
        RIGHT,
        HIDE;
    }

    public static enum ClientStyle {
        MINECRAFT,
        XYNIS;
    }

    public static enum GuiStyle {
        RAINBOW,
        DEFAULT,
        RED,
        ORANGE,
        YELLOW,
        GREEN,
        BLUE,
        VIOLET;
    }
}
