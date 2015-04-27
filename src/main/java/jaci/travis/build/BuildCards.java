package jaci.travis.build;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BuildCards {

    public static void main(String[] args) throws IOException {
        String json = new String(Base64.decode(args[0]));
        File filebase = new File(args[1]);
        JsonObject project = new JsonParser().parse(json).getAsJsonObject();

        String title = project.get("title").getAsString();
        String repo = project.get("repo").getAsString();
        String repoName = project.get("repo_name").getAsString();
        ArrayList<Branch> branchList = new ArrayList<Branch>();
        HashMap<String, String> colours = new HashMap<String, String>();

        JsonArray branches = project.get("branches").getAsJsonArray();
        for (int i = 0; i < branches.size(); i++) {
            JsonObject branch = branches.get(i).getAsJsonObject();
            Branch b = new Branch();
            b.branchName = branch.get("branch").getAsString();
            b.buildNumber = branch.get("build").getAsString();
            b.buildStatus = branch.get("status").getAsString();
            if (!branch.get("duration").isJsonNull())
                b.duration = branch.get("duration").getAsInt();
            branchList.add(b);
        }

        if (project.has("colors")) {
            JsonObject colors = project.get("colors").getAsJsonObject();
            for (Map.Entry<String, JsonElement> element : colors.entrySet()) {
                colours.put(element.getKey(), element.getValue().getAsString());
            }
        }

        CardRenderer renderer = new CardRenderer(title, branchList, colours);
        File outDir = new File(filebase, "imgout/" + repo);
        outDir.mkdirs();
        ImageIO.write(renderer.render(), "png", new File(outDir, repoName + ".png"));
    }

    public static class Branch {
        public String branchName;
        public String buildNumber;
        public String buildStatus;
        public int duration;
    }

}
