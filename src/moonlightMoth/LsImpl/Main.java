package moonlightMoth.LsImpl;

public class Main
{
    public static void main(String[] args)
    {
        doMain(new String[]{"test/moonlightMoth/LsImpl"});
    }

    private static void doMain(String[] args)
    {
        ParamsContainer params = ParamsContainer.getInstance(args);

        if (params != null)
        {
            System.out.println(params);
            LsInvoker.invoke(params);
        }

    }



}
