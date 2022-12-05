import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        List<Magazine> result = Magazine.GetMagazineFromFile("input.txt");
        Iterator var2 = result.iterator();

        while(var2.hasNext())
        {
            Magazine x = (Magazine)var2.next();
            x.Print();
        }
    }
}