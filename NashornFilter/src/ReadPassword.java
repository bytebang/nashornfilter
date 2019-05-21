
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ReadPassword
{
    public static void main(String[] args)
    {

        // Read the file
        try (Stream<String> stream = Files.lines(Paths.get("/etc/passwd")))
        {
            // Instantiate the filter
            NashornFilter f = new NashornFilter("o.userid.match('g') != null || o.uid > 1000")
                                        .withScriptVariableName("o").onErrorDefaultTo(true);

            
            // Yeah - Java8 read, filter and instantiante on one line of code !
            ArrayList<PwdUser> users =  stream.map(PwdUser::new)
                                            .filter(f::eval)
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