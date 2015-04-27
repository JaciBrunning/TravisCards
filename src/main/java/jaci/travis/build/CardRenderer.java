package jaci.travis.build;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Render System for a Card. This will render the image and return the BufferedImage object
 * given the parameters defined in the contructor.
 *
 * @author Jaci
 */
public class CardRenderer {

    public HashMap<String, Color> colors = new HashMap<String, Color>();
    public HashMap<String, String> message_bindings = new HashMap<String, String>();

    public String title;
    public List<BuildCards.Branch> branches;

    public static String font = "Arial";


    public CardRenderer(String title, ArrayList<BuildCards.Branch> branches, HashMap<String, String> colours) {
        putDefaultColours(colors);

        for (Map.Entry<String, String> userColor : colours.entrySet())
            colors.put(userColor.getKey(), Color.decode(userColor.getValue()));

        message_bindings.put("passed", "Passing");
        message_bindings.put("failed", "Failing");
        message_bindings.put("errored", "Errored");
        message_bindings.put("created", "Starting...");
        message_bindings.put("started", "In Progress...");
        message_bindings.put("cancelled", "Cancelled");

        this.title = title;
        this.branches = branches;
    }

    public BufferedImage render() throws IOException {
        int height = 80 + (50 * branches.size());
        int width = Math.max(400, title.getBytes().length * 35);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();

        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.WEIGHT, 1.6F);

        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(
                RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g.setColor(colors.get("background"));
        g.fillRect(0, 0, width, height);
        g.setColor(colors.get("top-border"));
        g.fillRect(0, 0, width, 8);

        g.setColor(colors.get("text"));
        g.setFont(new Font(font, 0, 61));
        g.drawString(title, 10, 65);

        for (int i = 0; i < branches.size(); i++) {
            int sY = 95 + (50 * i);

            g.setFont(new Font(font, 0, 20));
            g.setColor(colors.get("text"));
            BuildCards.Branch branch = branches.get(i);
            String name = branch.branchName;
            String status = branch.buildStatus;
            if (message_bindings.containsKey(status))
                status = message_bindings.get(status);
            name = Character.toUpperCase(name.charAt(0)) + name.substring(1);
            status = Character.toUpperCase(status.charAt(0)) + status.substring(1);
            g.drawString(name, 10, sY);

            g.setFont(new Font(font, 0, 14).deriveFont(attributes));
            g.setColor(colors.get(branch.buildStatus));
            String text = "";
            if (branch.buildStatus.equals("created") || branch.buildStatus.equals("started") || branch.buildStatus.equals("cancelled")) {
                text = String.format("Build #%s - %s", branch.buildNumber, status);
            } else
                text = String.format("Build #%s - %s (%dm:%ds)", branch.buildNumber, status, branch.duration / 60, branch.duration % 60);
            g.drawString(text, 20, sY + 20);
        }

        g.dispose();
        return image;
    }

    static void putDefaultColours(HashMap<String, Color> colors) {
        colors.put("passed", new Color(106, 246, 119));
        colors.put("failed", new Color(255, 0, 48));
        colors.put("errored", new Color(255, 120, 120));
        colors.put("created", new Color(255, 230, 112));
        colors.put("started", new Color(255, 179, 41));
        colors.put("cancelled", new Color(255, 0, 99));

        colors.put("background", new Color(15, 15, 15));
        colors.put("top-border", new Color(255, 0, 202));

        colors.put("text", new Color(226, 226, 226));
    }
}
