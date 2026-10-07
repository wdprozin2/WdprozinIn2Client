package us.whitedev.gui.clickgui.utils;

public enum Section {
    CRASHERS("\ud83d\udd25 Crashers"),
    EXPLOITS("\ud83d\udca3 Exploits"),
    ALT_MANAGER("\ud83c\udfad AltManager"),
    VERSIONS("\ud83d\udd27 Versions"),
    HACKS("\u2694\ufe0f Hacks"),
    PROXY("\ud83d\udee1\ufe0f Proxy"),
    STYLES("\ud83c\udfa8 Styles");

    private final String displayName;

    private Section(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
