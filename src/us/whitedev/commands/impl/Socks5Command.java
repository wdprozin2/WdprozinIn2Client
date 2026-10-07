package us.whitedev.commands.impl;

import us.whitedev.commands.Command;
import us.whitedev.helpers.Socks5Helper;

public class Socks5Command implements Command {
    private static final String COMMAND_NAME = "socks5";
    private final Socks5Helper socks5 = Socks5Helper.getInstance();

    @Override
    public String getName() {
        return COMMAND_NAME;
    }

    @Override
    public void onCommand(String[] args) {
        if (args.length == 1) {
            msgHelper.sendSeparateLine();
            msgHelper.sendMessage("&6[SOCKS5 Proxy]&7 Status: " + this.socks5.getStatusDescription(), true);
            msgHelper.sendMessage("&7Comandos:", true);
            msgHelper.sendMessage("&f!socks5 on &8- &7Ativa o proxy SOCKS5", true);
            msgHelper.sendMessage("&f!socks5 off &8- &7Desativa o proxy SOCKS5", true);
            msgHelper.sendMessage("&f!socks5 set <ip:porta> &8- &7Define o proxy (ex: 127.0.0.1:1080)", true);
            msgHelper.sendMessage("&f!socks5 set <ip:porta:user:pass> &8- &7Define proxy com autenticacao", true);
            msgHelper.sendMessage("&f!socks5 set <ip> <porta> [user] [pass] &8- &7Define proxy com argumentos separados", true);
            msgHelper.sendSeparateLine();
            return;
        }

        String sub = args[1].toLowerCase();
        switch (sub) {
            case "on":
            case "enable":
            case "ativar":
                this.socks5.setEnabled(true);
                msgHelper.sendMessage("&6[SOCKS5 Proxy]&7 Proxy foi &aativado&7! " + this.socks5.getStatusDescription(), true);
                break;

            case "off":
            case "disable":
            case "desativar":
                this.socks5.setEnabled(false);
                msgHelper.sendMessage("&6[SOCKS5 Proxy]&7 Proxy foi &cdesativado&7!", true);
                break;

            case "status":
                msgHelper.sendMessage("&6[SOCKS5 Proxy]&7 Status atual: " + this.socks5.getStatusDescription(), true);
                break;

            case "set":
                if (args.length == 3) {
                    // formato ip:port ou ip:port:user:pass
                    String[] parts = args[2].split(":");
                    if (parts.length == 2) {
                        try {
                            int port = Integer.parseInt(parts[1]);
                            this.socks5.setProxy(parts[0], port);
                            this.socks5.setEnabled(true);
                            msgHelper.sendMessage("&6[SOCKS5 Proxy]&a Configurado com sucesso: &f" + parts[0] + ":" + port, true);
                        } catch (NumberFormatException e) {
                            msgHelper.sendMessage("&6[SOCKS5 Proxy]&c Porta invalida: &f" + parts[1], true);
                        }
                    } else if (parts.length == 4) {
                        try {
                            int port = Integer.parseInt(parts[1]);
                            this.socks5.setProxy(parts[0], port, parts[2], parts[3]);
                            this.socks5.setEnabled(true);
                            msgHelper.sendMessage("&6[SOCKS5 Proxy]&a Configurado com autenticacao: &f" + parts[0] + ":" + port + " (" + parts[2] + ")", true);
                        } catch (NumberFormatException e) {
                            msgHelper.sendMessage("&6[SOCKS5 Proxy]&c Porta invalida: &f" + parts[1], true);
                        }
                    } else {
                        msgHelper.sendMessage("&6[SOCKS5 Proxy]&c Formato invalido! Use: &f!socks5 set <ip:porta> &7ou &f!socks5 set <ip:porta:user:pass>", true);
                    }
                } else if (args.length >= 4) {
                    // formato ip port [user] [pass]
                    try {
                        String host = args[2];
                        int port = Integer.parseInt(args[3]);
                        if (args.length >= 6) {
                            this.socks5.setProxy(host, port, args[4], args[5]);
                            this.socks5.setEnabled(true);
                            msgHelper.sendMessage("&6[SOCKS5 Proxy]&a Configurado com autenticacao: &f" + host + ":" + port + " (" + args[4] + ")", true);
                        } else {
                            this.socks5.setProxy(host, port);
                            this.socks5.setEnabled(true);
                            msgHelper.sendMessage("&6[SOCKS5 Proxy]&a Configurado com sucesso: &f" + host + ":" + port, true);
                        }
                    } catch (NumberFormatException e) {
                        msgHelper.sendMessage("&6[SOCKS5 Proxy]&c Porta invalida: &f" + args[3], true);
                    }
                } else {
                    msgHelper.sendMessage("&6[SOCKS5 Proxy]&c Uso: &f!socks5 set <ip:porta> &7ou &f!socks5 set <ip> <porta>", true);
                }
                break;

            default:
                msgHelper.sendMessage("&6[SOCKS5 Proxy]&c Subcomando desconhecido. Digite &f!socks5 &cpara ajuda.", true);
                break;
        }
    }
}
