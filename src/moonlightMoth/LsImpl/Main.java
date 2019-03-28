package moonlightMoth.LsImpl;

public class Main
{
    public static void main(String[] args)
    {
        //ParamsCLI params = new ParamsCLI(args);

        domain(new String[]{"-o", "tank", "sas", "ys"});
    }

    private static void domain(String[] args)
    {
        ParamsCLI params = new ParamsCLI(args);
        System.out.println(params);
    }



}
