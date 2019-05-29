
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A simple class that shows how to read the /etc/passwd into a filtered, sorted list of objects which
 * is printed afterwards.
 * 
 * It also demosntrates how the NashornFilter can be used.
 * 
 * @author bytebang
 */
public class ReadPassword
{
    public static void main(String[] args)
    {

        // Read the file
        try (Stream<String> stream = Files.lines(Paths.get("/etc/passwd")))
        {
            // Instantiate the filter
            NashornFilter scriptfilter = new NashornFilter("o.userid.match('g') != null || o.uid > 1000")
                                        .withScriptVariableName("o").onErrorDefaultTo(true);

            
            // Yeah - Java8 read, filter and instantiate on one line of code !
            ArrayList<PwdUser> users =  stream.map(PwdUser::new)
                                            .filter(scriptfilter::apply)
                                            .sorted((a,b) -> a.userid.compareTo(b.userid))
                                            .collect(Collectors.toCollection(ArrayList::new));
            
            // And print them
            users.stream().forEach(System.out::println);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}