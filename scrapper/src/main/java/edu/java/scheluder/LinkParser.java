package edu.java.scheluder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LinkParser {
    public static List<String> isGitHubLink(String link) {
        try {
            URI uri = new URI(link);
            String host = uri.getHost();
            if (host != null && (host.equals("github.com") || host.endsWith(".github.com"))) {
                List<String> out = new ArrayList<>();
                out.add(link.split("/")[Integer.parseInt("3")]);
                out.add(link.split("/")[Integer.parseInt("4")]);
                return out;
            }
            return Collections.singletonList("-1");
        } catch (URISyntaxException e) {
            return Collections.singletonList("-1");
        }
    }

    public static List<String> isStackOverflowLink(String link) {
        try {
            URI uri = new URI(link);
            String host = uri.getHost();
            if (host != null && (host.equals("stackoverflow.com") || host.endsWith(".stackoverflow.com"))) {
                List<String> out = new ArrayList<>();
                out.add(link.split("/")[Integer.parseInt("3")]);
                out.add(link.split("/")[Integer.parseInt("4")]);
                return out;
            }
            return Collections.singletonList("-1");
        } catch (URISyntaxException e) {
            return Collections.singletonList("-1");
        }
    }
}
