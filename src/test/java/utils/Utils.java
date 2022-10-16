package utils;

import org.junit.platform.commons.util.ClassLoaderUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class Utils {
    public String readFile(String fileName) {
        String content = null;

        try {
            content = Files.readString(
                    Path.of(
                            Objects.requireNonNull(
                                    ClassLoaderUtils.getDefaultClassLoader().getResource(fileName)
                            ).toURI()
                    )
            );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return content;
    }
}
