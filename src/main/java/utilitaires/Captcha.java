package utilitaires;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

public class Captcha {

    public BufferedImage image;
    public String answer;
    public Captcha()
    {
        this.answer = generateRandomText();
        this.image = generateCaptchaImage();
    }

    private void addNoise(Graphics2D g2d, int width, int height) {
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 100; i++) {
            int x = (int) (Math.random() * width);
            int y = (int) (Math.random() * height);
            g2d.fillOval(x, y, 2, 2);
        }
    }

    private String generateRandomText() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            text.append(chars.charAt(random.nextInt(chars.length())));
        }
        return text.toString();
    }

    private BufferedImage generateCaptchaImage() {
        int width = 230;
        int height = 50;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Set random font
        Font font = new Font("Arial", Font.BOLD, 40);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);

        // Generate random text
        String captchaText = this.answer;
        g2d.drawString(captchaText, 40, 40);

        // Add some noise (optional)
        addNoise(g2d, width, height);

        // Dispose the graphics context
        g2d.dispose();

        return image;
    }
}
