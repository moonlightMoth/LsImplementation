package moonlightMoth.LsImpl;

public class Main
{
    public static void main(String[] args)
    {
        doMain(args);
    }

    private static void doMain(String[] args)
    {
        ParamsContainer params = ParamsContainer.getInstance(args);

        if (params != null)
        {
            LsInvoker.invoke(params);
        }
    }
}
