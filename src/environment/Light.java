package environment;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Light {
    private BufferedImage image;
    private int x;
    private int y;

    public Light(int x, int y, int radius, float luminosity) {
        //Recommended luminosity between 1 and 2
        this.x = x;
        this.y = y;
        image = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();

        for(int i = 0; i < radius; i++) {
            double luma = 1.0D - ((i + 0.001) / radius);
            int alpha = Math.min((int)(255.0D * luma * luminosity), 255);
            g2.setColor(new Color(0, 0, 0, alpha));
            g2.setStroke(new BasicStroke(2));
            g2.drawOval(radius - i, radius - i, i * 2, i * 2);
        }
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, image.getWidth(), image.getHeight(), null);
    }    

}
