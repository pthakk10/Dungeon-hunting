import java.util.Scanner;

public class DarkPlace extends Place {


    public DarkPlace(Scanner inputFile) {
        super(inputFile);
        super.illuminated = false;
    }

    @Override
    public void use(Artifact artifact) {
        if(super.illuminated)
        {
            //If the place is already illuminated that return false
            System.out.println("================================================");
            System.out.println("* The current place already has plenty of light");
            System.out.println("================================================");

        }
        else
        {
            //If the place is not illluminated, then illuminate it and return true
            super.illuminated = true;
            System.out.println("================================================");
            System.out.println("* The current place illuminated itself with the blazing torch");
            System.out.println("================================================");
        }

    }
}
