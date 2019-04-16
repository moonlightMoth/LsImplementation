package moonlightMoth.LsImpl;

import org.kohsuke.args4j.*;

import java.util.ArrayList;
import java.util.List;

class ParamsContainer
{
    @Option(name = "-l", usage = "Print files with permissions, size and last modification date, forbids -h", forbids = {"-h"})
    boolean isExtendedOutput;

    @Option(name = "-h", usage = "Prints files info in human readable format, forbids -l", forbids = {"-l"})
    boolean isHumanReadable;

    @Option(name = "-r", usage = "Revert print order")
    boolean isReversed;

    @Option(name = "-o", usage = "File to print ls result to", metaVar = "[outfile]")
    String outputToFile;

    @Argument (usage = "path to ls and file to output", metaVar = "[outfile] <lsdir>")
    List<String> args = new ArrayList<>();

    static ParamsContainer getInstance(String[] args)
    {
        ParamsContainer params = new ParamsContainer();
        CmdLineParser parser = new CmdLineParser(params);

        try
        {
            parser.parseArgument(args);

            if (params.args.size() > 1)
            {
                parser.printUsage(System.out);
                return null;
            }

            if (params.args.isEmpty())
                params.args.add(".");

        }
        catch (CmdLineException e)
        {
            parser.printUsage(System.out);
            return null;
        }


        return params;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Ls dir/file = ");
        sb.append(args.get(0));
        sb.append(";");
        sb.append(System.lineSeparator());
        sb.append("Flags:");
        if (isExtendedOutput)
            sb.append(" -l");
        if (isHumanReadable)
            sb.append(" -h");
        if (isReversed)
            sb.append(" -r");
        if (outputToFile != null)
            sb.append(" -o");
        sb.append(System.lineSeparator());
        if (outputToFile != null)
        {
            sb.append("Output file: ");
            sb.append(outputToFile);
        }

        return sb.toString();

    }
}

