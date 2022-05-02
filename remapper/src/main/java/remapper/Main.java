package remapper;

import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;
import net.fabricmc.tinyremapper.TinyUtils;

import java.io.IOException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws IOException {
        // arg 0: in
        // arg 1: out
        // arg 2: mappings
        var remapper = TinyRemapper.newRemapper()
            .withMappings(TinyUtils.createTinyMappingProvider(Path.of(args[2]), "from", "to"))
            .fixPackageAccess(true)
            .build();

        try (var output = new OutputConsumerPath.Builder(Path.of(args[1])).build()) {
            remapper.readInputs(Path.of(args[0]));
            remapper.apply(output);
        } finally {
            remapper.finish();
        }
    }
}

