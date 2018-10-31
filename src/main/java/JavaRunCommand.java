import com.google.gson.Gson;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JavaRunCommand {

    public static void main(String args[]) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        String s = null;

        try {

            String master_branch = "master";
            String current_branch = "develop";

            /**
             * Assume that 2 branch names would be present.
             * Do git diff --name-status master : This gives names of file modified between two branches.
             * For a specific package, filter out all the names of files that are changed.
             * Recursively, find if the changes are breaking or not :
             * Use git show to find complete files.
             * Use GSON to convert it to json format.
             * Compare using the utility shared.
             *
             * Check how mail will be triggered.
             * Check how to hook it to maven.
             */
            Process p = Runtime.getRuntime().exec("git diff --name-status master");
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));

            List<String> fileNames = new ArrayList<String>(10);

            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                //Only files of package com.example.pojo required.
                if(s.contains("com/example/pojo/")){
                    System.out.println(s);
                    fileNames.add(s.split("\t")[1]);
                }
            }

            for(String file : fileNames) {
                s = new String();
                //getting file from master
                p = Runtime.getRuntime().exec("git show "+master_branch+":"+file);
                BufferedReader stdInput1 = new BufferedReader(new
                        InputStreamReader(p.getInputStream()));
                String source = new String("");
                // read the output from the command
                while ((s = stdInput1.readLine()) != null) {
                    source += s;
                }

                Set<Info> masterList = PojoStringParser.parse(source);

                s = new String();
                //getting file from curent branch
                p = Runtime.getRuntime().exec("git show "+current_branch+":"+file);
                BufferedReader stdInput2 = new BufferedReader(new
                        InputStreamReader(p.getInputStream()));
                String branchSource = new String("");
                // read the output from the command
                while ((s = stdInput2.readLine()) != null) {
                    branchSource += s;
                }

                Set<Info>  branchList = PojoStringParser.parse(branchSource);

                for(Info info : masterList) {
                    if(!branchList.contains(info)) {
                        System.out.println(info + " is not present");
                        System.out.println(file + " is not backward compatible");
                        break;
                    }
                }
            }
            System.exit(0);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}