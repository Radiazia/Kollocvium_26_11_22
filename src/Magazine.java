import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Magazine implements Comparable<Magazine>
{
    private String name;
    private String companyName;
    private int expirationDate;
    private int numOfPackages;
    private double packingPrice;
    public Magazine() {}
    public Magazine(String name) {this.name = name;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getExpirationDate() {return expirationDate;}
    public void setExpirationDate(int expirationDate)
    {
        if (expirationDate < 2020 || expirationDate > 2022)
        {
            throw new IllegalArgumentException("Expiration date should be in 2020-2022");
        }
        this.expirationDate = expirationDate;
    }
    public String getCompanyName() {return companyName;}
    public void setCompanyName(String companyName) {this.companyName = companyName; }
    public int getNumOfPackages() {return numOfPackages;}
    public void setNumOfPackages(int numOfPackages)
    {
        if (numOfPackages < 1 || numOfPackages > 100)
        {
            throw new IllegalArgumentException("Expiration date should be in 1-100");
        }
        this.numOfPackages = numOfPackages;
    }
    public double getPackingPrice() {return packingPrice;}
    public void setPackingPrice(double packingPrice) {this.packingPrice = packingPrice;}
    public static boolean IsExpirationDate(String expirationDate)
    {
        var regExp = new String("202[0-2]");
        return expirationDate.matches(regExp);
    }
    public static boolean IsNumOfPackages(String nameOfPackages)
    {
        var regExp = new String("([1-9]|[1-9][0-9]|100)");
        return nameOfPackages.matches(regExp);
    }
    public static boolean IsName(String name)
    {
        var regExp = new String("\"[\\S]+\"");
        return name.matches(regExp);
    }
    public static boolean IsCompanyName(String companyName)
    {
        var regExp = new String("([\\S]+)");
        return companyName.matches(regExp);
    }
    private static Magazine GetMagazineFromLine(String line)
    {
        var magazine = new Magazine();
        var stringTokenizer = new StringTokenizer(line, "\t\r ");

        while (stringTokenizer.hasMoreTokens())
        {
            var str = stringTokenizer.nextToken();
            if (IsName(str))
            {
                magazine.setName(str);
            }
            else if (IsCompanyName(str))
            {
                magazine.setCompanyName(str);
            }
            else if (IsExpirationDate(str))
            {
                magazine.setExpirationDate(Integer.parseInt(str));
            }
            else if (IsNumOfPackages(str))
            {
                magazine.setNumOfPackages(Integer.parseInt(str));
            }
            else
            {
                magazine.setPackingPrice(Double.parseDouble(str));
            }
        }
        return magazine;
    }
    public static List<Magazine> GetMagazineFromFile(String inputFileName) throws FileNotFoundException
    {
        var inputFile = new File(inputFileName);
        var scanner = new Scanner(inputFile);
        var size = Integer.parseInt(scanner.nextLine());
        var magazines = new ArrayList<Magazine>(size);

        for (int i = 0; i < size; i++)
        {
            var magazineLine = scanner.nextLine();
            magazines.add(GetMagazineFromLine(magazineLine));
        }
        return magazines;
    }
    public void Print()
    {
        System.out.println(name + " " + companyName + " " + expirationDate + " " + numOfPackages + " " + packingPrice);
    }
    public void SortByName(List<Magazine> magazines)
    {
        magazines.sort((a, b) -> a.getName().compareTo(b.getName()));
    }
    public int BinarySearch(List<Magazine> magazines)
    {
        return Collections.binarySearch(magazines, new Magazine(), Magazine::compareTo);
    }
    @Override
    public int compareTo(Magazine o)
    {
        return this.getName().compareTo(o.getName());
    }
    public void SortByPackingPrice(List<Magazine> magazines)
    {
        magazines.sort((b, a) -> Double.compare(b.getPackingPrice(), a.getPackingPrice()));
    }
    public void FilterByAuthorName(ArrayList<Magazine> magazines, String companyName)
    {
        var stream = magazines.stream();
        stream
                .filter(m -> m.getCompanyName().equals(companyName))
                .forEach(System.out::println);
    }
    public Map<String, List<Magazine>> GroupByAuthorName(List<Magazine> magazines, String companyName)
    {
        var stream = magazines.stream();
        var res = stream.collect(Collectors.groupingBy(Magazine::getCompanyName));
        return res;
    }
}