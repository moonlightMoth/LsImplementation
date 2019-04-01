package moonlightMoth.LsImpl;

import java.io.File;

public class LsInvoker
{
    private static ParamsContainer pc;
    private static File file;

    private static void init()
    {
        file = new File(pc.args.get(pc.args.size()-1));

        for (File s : file.listFiles())
        {
            System.out.println();
        }

        System.getSecurityManager().

        System.out.println(file.getAbsolutePath());
        System.out.println(file.exists());
        System.out.println(file.isFile());
        System.out.println(file.isDirectory());
    }

    public static void invoke(ParamsContainer pc)
    {
        LsInvoker.pc = pc;

        init();
    }
}
