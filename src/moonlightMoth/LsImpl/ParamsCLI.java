package moonlightMoth.LsImpl;

import org.kohsuke.args4j.*;
import org.kohsuke.args4j.spi.ArgumentImpl;

import java.util.ArrayList;
import java.util.List;

public class ParamsCLI
{
    @Option(name = "-l", usage = "Print files with permissions, size and last modification date", forbids = {"-h"})
    private boolean isExtendedOutput;

    @Option(name = "-h", usage = "Prints files info in human readable format", forbids = {"-l"})
    private boolean isHumanReadable;

    @Option(name = "-r", usage = "Revert print order")
    private boolean isReversed;

    @Option(name = "-o", usage = "File to print ls result to", metaVar = "[outfile]")
    private boolean isOutputToFile;

    @Argument (usage = "path to ls and file to output", required = true, metaVar = "[outfile] <lsdir>")
    private List<String> args = new ArrayList<>();

    public ParamsCLI(String[] args)
    {
        CmdLineParser parser = new CmdLineParser(this);


        if (args.length < 1 || this.args.size() > 2 || this.args.isEmpty())
        {
            parser.printUsage(System.out);
            
        }
        else
        {
            try
            {
                parser.parseArgument(args);

            }
            catch (CmdLineException e)
            {
                e.printStackTrace();
            }
        }
    }



    public void setExtendedOutput(boolean extendedOutput)
    {
        isExtendedOutput = extendedOutput;
    }

    public void setHumanReadable(boolean humanReadable)
    {
        isHumanReadable = humanReadable;
    }

    public void setOutputToFile(boolean outputToFile)
    {
        isOutputToFile = outputToFile;
    }

    public void setReversed(boolean reversed)
    {
        isReversed = reversed;
    }

    public boolean isExtendedOutput()
    {
        return isExtendedOutput;
    }

    public boolean isHumanReadable()
    {
        return isHumanReadable;
    }

    public boolean isOutputToFile()
    {
        return isOutputToFile;
    }

    public boolean isReversed()
    {
        return isReversed;
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

