package moonlightMoth.LsImpl;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;

class LsInvoker
{
    private static PrintStream filePrintStream = null;
    private static ParamsContainer pc;
    private static File file;
    private static ReversibleStringBuilder rsb;
    private static StringBuilder sb = new StringBuilder();
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    static void invoke(ParamsContainer pc) throws IOException
    {
        LsInvoker.pc = pc;

        rsb = new ReversibleStringBuilder(pc.isReversed);

        file = new File(pc.lsCat);

        if (!file.exists())
        {
            System.out.println("ls: no such file or dir: " + file.getAbsolutePath());
            return;
        }

        if (pc.outputToFile != null)
        {
            File outFile = new File(pc.outputToFile);
            filePrintStream = new PrintStream(outFile);
            if (!outFile.exists())
                if (outFile.createNewFile())
                    System.setOut(filePrintStream);
                else
                    System.out.println("cannot create output file");

            else
                System.setOut(filePrintStream);
        }

        init();
    }

    private static void init()
    {
        if (file.isDirectory())
        {
            if (!pc.isHumanReadable && !pc.isExtendedOutput)
                listDirOutput();
            if (pc.isExtendedOutput)
                listExtendedDirOutput();
            if (pc.isHumanReadable)
                listHumanReadableDirOutput();
        }
        else
        {
            if (!pc.isHumanReadable && !pc.isExtendedOutput)
                listFileOutput(file);
            if (pc.isExtendedOutput)
                listExtendedFileOutput(file);
            if (pc.isHumanReadable)
                listHumanReadableFileOutput(file);
        }

        System.out.print(rsb.toString());
        if (filePrintStream != null)
            filePrintStream.close();
    }

    private static void listDirOutput()
    {
        for (File f: sortFiles(file.listFiles()))
        {
            listFileOutput(f);
        }
    }

    private static void listExtendedDirOutput()
    {
        for (File f: sortFiles(file.listFiles()))
        {
            listExtendedFileOutput(f);
        }
    }

    private static void listHumanReadableDirOutput()
    {
        for (File f: sortFiles(file.listFiles()))
        {
            listHumanReadableFileOutput(f);
        }
    }

    private static void listFileOutput(File file)
    {
        sb.append(file.getName());
        sb.append(System.lineSeparator());

        rsb.append(sb);
        sb.delete(0, sb.length());
    }

    private static void listExtendedFileOutput(File file)
    {
        int bitmask = 0;

        sb.append(file.isDirectory() ? "d " : "- ");

        bitmask += file.canRead() ? 4 : 0;
        bitmask += file.canWrite() ? 2 : 0;
        bitmask += file.canExecute() ? 1 : 0;

        sb.append(bitmask);
        sb.append(" ");
        sb.append(file.lastModified());
        sb.append("  ");
        sb.append(file.isDirectory() ? "0  " : file.length());
        sb.append("      ");
        sb.append(file.getName());
        sb.append(System.lineSeparator());

        rsb.append(sb);
        sb.delete(0, sb.length());
    }

    private static void listHumanReadableFileOutput(File file)
    {
        sb.append(file.isDirectory() ? "d" : "-");
        sb.append(file.canRead() ? "r" : "-");
        sb.append(file.canWrite() ? "w" : "-");
        sb.append(file.canExecute() ? "x  " : "-  ");
        sb.append(sdf.format(file.lastModified()));
        sb.append("  ");
        sb.append(file.isDirectory() ? "0  " : getFileSizeHumanReadable(file));
        sb.append("      ");
        sb.append(file.getName());
        sb.append(System.lineSeparator());

        rsb.append(sb);
        sb.delete(0, sb.length());

    }

    private static String getFileSizeHumanReadable(File f)
    {
        long sizeBytes = f.length();
        int rem = 0;
        int counter = 0;

        while (sizeBytes / 1024 > 0 && counter < 4)
        {
            rem = (int)(sizeBytes % 1024);
            sizeBytes = sizeBytes / 1024;
            counter++;
        }

        if (counter > 0)
            return sizeBytes + getName(counter) + " " + rem + getName(counter-1);
        else
            return sizeBytes + "B";
    }

    private static String getName(int counter)
    {
        switch (counter)
        {
            case 0: return "B";
            case 1: return "K";
            case 2: return "M";
            case 3: return "G";
            case 4: return "T";
            default: return "?";
        }
    }

    private static File[] sortFiles(File[] files)
    {
        Arrays.sort(files, (Comparator.comparing(File::getName)));
        return files;
    }

}
