package Adventure_Game.data_access;

import Adventure_Game.interface_adapter.move.MoveStaticMapDataAccessInterface;

import javax.swing.*;
import java.awt.*;

public class StaticMapDataAccessObject implements MoveStaticMapDataAccessInterface {
    public ImageIcon getMapImage(double latitude, double longitude) {
        // TODO: replace with a true static map image using api
        return createPlaceholderImage(latitude, longitude);
    }

    private ImageIcon createPlaceholderImage(double lat, double lon) {
        int width = 300;
        int height = 200;

        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(
                width, height, java.awt.image.BufferedImage.TYPE_INT_RGB
        );
        Graphics2D g = image.createGraphics();

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString("Static Map Placeholder", 70, 80);
        g.drawString(String.format("Lat: %.4f", lat), 70, 100);
        g.drawString(String.format("Lon: %.4f", lon), 70, 120);

        g.dispose();

        return new ImageIcon(image);
    }
}
