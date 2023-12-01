import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader {

    private final Stream<String> input;

    public FileReader(String resourceName) {
        // Load resource
        InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream(resourceName);

        // Make sure resource is valid
        if (input == null) {
            throw new IllegalArgumentException("Couldn't find resource " + resourceName);
        }

        // Cache resource
        this.input = new BufferedReader(new InputStreamReader(input)).lines();
    }

    public List<Integer> getInputAsIntegers() {
        return input.map(Integer::parseInt).collect(Collectors.toList());
    }

    public List<String> getInputAsStrings() {
        return input.collect(Collectors.toList());
    }

}