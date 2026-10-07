package us.whitedev.commands.impl;

import us.whitedev.commands.Command;
import us.whitedev.updater.AutoUpdater;

public class UpdateCommand implements Command {
    private static final String COMMAND_NAME = "update";
    private final AutoUpdater updater = AutoUpdater.getInstance();

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length >= 3 && args[1].equalsIgnoreCase("repo")) {
            this.updater.setRepo(args[2]);
            msgHelper.sendMessage("&aUpdate repository set to: &f" + args[2], true);
            return;
        }
        this.updater.checkAndUpdate(true);
    }
}
