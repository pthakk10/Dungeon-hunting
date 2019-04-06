import java.util.Scanner;

public class HealthArtifact extends Artifact {

    private int healthRegeneration;

    public HealthArtifact(Scanner inputFile, int healthRegeneration) {
        super(inputFile);
        this.healthRegeneration = healthRegeneration;
    }

    @Override
    public int use() {
        return this.healthRegeneration;
    }

}
