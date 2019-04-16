package moonlightMoth.LsImpl;

import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        doMain(args);
    }

    private static void doMain(String[] args) throws IOException
    {
        ParamsContainer params = ParamsContainer.getInstance(args);

        if (params != null)
        {
            LsInvoker.invoke(params);
        }
    }
}
