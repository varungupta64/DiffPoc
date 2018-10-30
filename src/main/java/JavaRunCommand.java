import java.io.*;

public class JavaRunCommand {

    public static void main(String args[]) {

        String s = null;

        try {
            
	    // run the Unix "ps -ef" command
            // using the Runtime exec method:

            /**
             * Assume that 2 branch names would be present.
             * Do git status
             * For a specific package, filter out all the names of files that are changed.
             * Recursively, find if the changes are breaking or not.
             */
            Process p = Runtime.getRuntime().exec("git status");
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new 
                 InputStreamReader(p.getErrorStream()));

            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
            
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}