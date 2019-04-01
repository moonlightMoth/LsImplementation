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
    boolean isOutputToFile;

    @Argument (usage = "path to ls and file to output", required = true, metaVar = "[outfile] <lsdir>")
    List<String> args = new ArrayList<>();

    private ParamsContainer()
    {

    }

    static ParamsContainer getInstance(String[] args)
    {
        ParamsContainer params = new ParamsContainer();
        CmdLineParser parser = new CmdLineParser(params);

        if (args.length < 1)
        {
            parser.printUsage(System.out);
            return null;
        }
        else
        {
            try
            {
                parser.parseArgument(args);

                if (params.args.size() > 2 || params.args.isEmpty())
                {
                    parser.printUsage(System.out);
                    return null;
                }

            }
            catch (CmdLineException e)
            {
                parser.printUsage(System.out);
                return null;
            }
        }

        return params;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Ls dir/file = ");
        sb.append(isOutputToFile ? args.get(1) : args.get(0));
        sb.append(";\nFlags:");
        if (isExtendedOutput)
            sb.append(" -l");
        if (isHumanReadable)
            sb.append(" -h");
        if (isReversed)
            sb.append(" -r");
        if (isOutputToFile)
            sb.append(" -o");
        sb.append("\n");
        if (isOutputToFile)
        {
            sb.append("Output file: ");
            sb.append(args.get(0));
        }

        return sb.toString();

    }
}

