package us.whitedev.security;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;

public class LicenseManager {
    private static LicenseManager instance;
    private static final byte[] SECRET_KEY = "WdprozinIn2Client-KeyGen-SecKey#2026!Xyn".getBytes(StandardCharsets.UTF_8);

    private boolean verified = false;
    private String licensedUser = "";
    private String licenseType = "";
    private long expiryMs = -1L;

    private LicenseManager() {}

    public static synchronized LicenseManager getInstance() {
        if (instance == null) {
            instance = new LicenseManager();
        }
        return instance;
    }

    public boolean verify() {
        File keyFile = new File("license.key");
        if (!keyFile.exists()) {
            this.handleFailure("Missing 'license.key' file!\n\nThis client requires a valid cryptographic license key to run.\nPlease contact wdprozin_ on Discord (wdprozin_) to obtain your license.");
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(keyFile))) {
            String line = reader.readLine();
            if (line == null || line.trim().isEmpty()) {
                this.handleFailure("The 'license.key' file is empty or corrupted!");
                return false;
            }

            line = line.trim();
            byte[] decodedBytes;
            try {
                decodedBytes = Base64.getDecoder().decode(line);
            } catch (Exception e) {
                this.handleFailure("Invalid license key format (corrupted Base64 encoding).");
                return false;
            }

            String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
            String[] parts = decoded.split(":");
            if (parts.length != 4) {
                this.handleFailure("Invalid license payload structure.");
                return false;
            }

            String user = parts[0];
            long expiry;
            try {
                expiry = Long.parseLong(parts[1]);
            } catch (NumberFormatException e) {
                this.handleFailure("Invalid license timestamp.");
                return false;
            }

            String type = parts[2];
            String expectedSig = parts[3];

            // Verify signature
            String payload = user + ":" + expiry + ":" + type;
            String computedSig = calculateHmacSha256(payload, SECRET_KEY);

            if (!computedSig.equalsIgnoreCase(expectedSig)) {
                this.handleFailure("Tampered or counterfeit license key detected!\nSignature verification failed.");
                return false;
            }

            // Check expiration
            if (expiry != -1L && System.currentTimeMillis() > expiry) {
                this.handleFailure("Your license key for WdprozinIn2Client has EXPIRED!\nContact wdprozin_ on Discord to renew.");
                return false;
            }

            this.verified = true;
            this.licensedUser = user;
            this.licenseType = type;
            this.expiryMs = expiry;

            System.out.println("[WdprozinIn2Client] ========================================");
            System.out.println("[WdprozinIn2Client] Security check: License verified successfully!");
            System.out.println("[WdprozinIn2Client] Licensed to: " + this.licensedUser + " [" + this.licenseType + "]");
            System.out.println("[WdprozinIn2Client] Expiration: " + (expiry == -1L ? "Lifetime" : new java.util.Date(expiry).toString()));
            System.out.println("[WdprozinIn2Client] ========================================");
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            this.handleFailure("Error reading license key: " + e.getMessage());
            return false;
        }
    }

    private void handleFailure(String message) {
        System.err.println("\n[SECURITY ALERT] " + message + "\n");
        try {
            JOptionPane.showMessageDialog(
                null,
                message,
                "WdprozinIn2Client - Authorization Required",
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Throwable ignored) {
            // In headless environments, ignore dialog failure
        }
        System.exit(1);
    }

    private static String calculateHmacSha256(String data, byte[] key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hmacBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public boolean isVerified() {
        return this.verified;
    }

    public String getLicensedUser() {
        return this.licensedUser;
    }

    public String getLicenseType() {
        return this.licenseType;
    }

    public long getExpiryMs() {
        return this.expiryMs;
    }
}
