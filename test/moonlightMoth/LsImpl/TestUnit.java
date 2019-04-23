package moonlightMoth.LsImpl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestUnit
{
    private PipedInputStream pis = new PipedInputStream();
    private PrintStream ps = null;
    private String separator = "###";
    private BufferedReader br = null;
    private BufferedReader fileBr = null;

    private String testDirRootName = "testDir" + File.separator;
    private String dirName = testDirRootName + "dasIstDirectory" + File.separator;
    private String outFileName = "outputFile.txt";
    private File testDirRoot = new File(testDirRootName);
    private File outFile = new File(outFileName);
    private File dirFile = new File(dirName);

    private File[] fileArray = new File[] {
            new File(testDirRootName + "raz.txt"),
            new File(testDirRootName + "dva.saf"),
            new File (testDirRootName + "tri.pat")};

    @BeforeAll
    public void before()
    {
        try
        {
            ps = new PrintStream(new PipedOutputStream(pis));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.setOut(ps);

        try
        {
            createTestDir();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        br = new BufferedReader(new InputStreamReader(pis));
        try
        {
            fileBr = new BufferedReader(new FileReader(outFileName));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    void testCaseDir() throws IOException
    {
        System.setOut(ps);
        String expected =
                "dasIstDirectory" + System.lineSeparator() +
                        "dva.saf" + System.lineSeparator() +
                        "raz.txt" + System.lineSeparator() +
                        "tri.pat";


        Main.main(new String[] {testDirRootName});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }



    @Test
    void testCaseExtendedOutputDir() throws IOException
    {
        System.setOut(ps);
        String expected =
                        "d 7 1000  0        dasIstDirectory" + System.lineSeparator() +
                        "- 2 1000  1600      dva.saf" + System.lineSeparator() +
                        "- 6 1000  800      raz.txt" + System.lineSeparator() +
                        "- 7 1000  2400      tri.pat";


        Main.main(new String[] {"-l", testDirRootName});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }

    @Test
    void testCaseHumanReadableDir() throws IOException
    {
        System.setOut(ps);
        String expected =
                "drwx  01.01.1970 03:00:01  0        dasIstDirectory" + System.lineSeparator() +
                        "--w-  01.01.1970 03:00:01  1K 576B      dva.saf" + System.lineSeparator() +
                        "-rw-  01.01.1970 03:00:01  800B      raz.txt" + System.lineSeparator() +
                        "-rwx  01.01.1970 03:00:01  2K 352B      tri.pat";


        Main.main(new String[] {"-h", testDirRootName});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }

    @Test
    void testCaseReversedHumanReadable() throws IOException
    {
        System.setOut(ps);
        String expected =
                "-rwx  01.01.1970 03:00:01  2K 352B      tri.pat" + System.lineSeparator() +
                        "-rw-  01.01.1970 03:00:01  800B      raz.txt" + System.lineSeparator() +
                        "--w-  01.01.1970 03:00:01  1K 576B      dva.saf" + System.lineSeparator() +
                        "drwx  01.01.1970 03:00:01  0        dasIstDirectory";

        Main.main(new String[] {"-h", "-r", testDirRootName});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }

    @Test
    void testCaseInvalidFlagsMixCrush() throws IOException
    {
        System.setOut(ps);
        String expected =
                "[outfile] <lsdir> : path to ls and file to output" + System.lineSeparator() +
                        " -h                : Prints files info in human readable format, forbids -l" + System.lineSeparator() +
                        "                     (default: true)" + System.lineSeparator() +
                        " -l                : Print files with permissions, size and last modification" + System.lineSeparator() +
                        "                     date, forbids -h (default: true)" + System.lineSeparator() +
                        " -o [outfile]      : File to print ls result to" + System.lineSeparator() +
                        " -r                : Revert print order (default: false)";

        Main.main(new String[] {"-l", "-h"});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }

    @Test
    void testCaseInvalidArgsCrush() throws IOException
    {
        System.setOut(ps);
        String expected =
                "[outfile] <lsdir> : path to ls and file to output" + System.lineSeparator() +
                        " -h                : Prints files info in human readable format, forbids -l" + System.lineSeparator() +
                        "                     (default: false)" + System.lineSeparator() +
                        " -l                : Print files with permissions, size and last modification" + System.lineSeparator() +
                        "                     date, forbids -h (default: false)" + System.lineSeparator() +
                        " -o [outfile]      : File to print ls result to" + System.lineSeparator() +
                        " -r                : Revert print order (default: false)";

        Main.main(new String[] {"-sus"});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }

    @Test
    void testCaseTooManyArgsCrush() throws IOException
    {
        System.setOut(ps);
        String expected =
                "[outfile] <lsdir> : path to ls and file to output (default: [sus,asd,sad])" + System.lineSeparator() +
                        " -h                : Prints files info in human readable format, forbids -l" + System.lineSeparator() +
                        "                     (default: false)" + System.lineSeparator() +
                        " -l                : Print files with permissions, size and last modification" + System.lineSeparator() +
                        "                     date, forbids -h (default: false)" + System.lineSeparator() +
                        " -o [outfile]      : File to print ls result to" + System.lineSeparator() +
                        " -r                : Revert print order (default: false)";

        Main.main(new String[] {"sus", "asd", "sad"});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }

    @Test
    void testCaseHumanReadableDirToFile() throws IOException
    {
        String expected =
                "drwx  01.01.1970 03:00:01  0        dasIstDirectory" + System.lineSeparator() +
                        "--w-  01.01.1970 03:00:01  1K 576B      dva.saf" + System.lineSeparator() +
                        "-rw-  01.01.1970 03:00:01  800B      raz.txt" + System.lineSeparator() +
                        "-rwx  01.01.1970 03:00:01  2K 352B      tri.pat";


        Main.main(new String[] {"-h", "-o", outFileName, testDirRootName});

        assertEquals(expected, readOutputFromFile());
    }

    private String readOutput() throws IOException
    {
        String s;
        StringBuilder sb = new StringBuilder();

        while ((s = br.readLine()) != null)
        {
            if (s.equals(separator))
                break;
            sb.append(s).append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    private String readOutputFromFile() throws IOException
    {
        String s;
        StringBuilder sb = new StringBuilder();

        while ((s = fileBr.readLine()) != null)
        {
            if (s.equals(separator))
                break;
            sb.append(s).append(System.lineSeparator());
        }
        reloadOutFile();

        return sb.toString().trim();
    }

    private void reloadOutFile() throws IOException
    {
        outFile.delete();
        outFile.createNewFile();
    }

    @AfterAll
    public void after()
    {
        try
        {
            fileBr.close();
            br.close();
            ps.close();
            pis.close();
            System.out.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        deleteTestDirs();
    }

    private void createTestDir() throws IOException
    {
        PrintWriter pw;
        byte b = 0b1111111;

        testDirRoot.mkdir();

        outFile.createNewFile();

        for (int i = 0; i < fileArray.length; i++)
        {
            fileArray[i].createNewFile();
            pw = new PrintWriter(fileArray[i]);
            for (int j = 0; j < (i+1)*800; j++)
            {
                pw.write(b);
            }
            pw.flush();
            pw.close();
        }

        try
        {
            setPermissionsPosix(fileArray[0], true, true, true);
            setPermissionsPosix(fileArray[1], false, true, false);
            setPermissionsPosix(fileArray[2], false, true, true);
        }
        catch (UnsupportedOperationException e)
        {
            e.printStackTrace();
        }


        dirFile.mkdir();

        long conventionalLastModified = 1000;

        for (int i = 0; i < fileArray.length; i++)
        {
            fileArray[i].setLastModified(conventionalLastModified);
        }

        dirFile.setLastModified(conventionalLastModified);

    }

    private void setPermissionsPosix(File f, boolean read, boolean write, boolean execute) throws IOException
    {
        HashSet<PosixFilePermission> set = new HashSet<>();

        set.add(read ? PosixFilePermission.OTHERS_READ : null);
        set.add(write ? PosixFilePermission.OTHERS_WRITE : null);
        set.add(execute ? PosixFilePermission.OTHERS_EXECUTE : null);

        Files.setPosixFilePermissions(f.toPath(), set);
    }


    private void deleteTestDirs()
    {
        outFile.delete();

        dirFile.delete();

        for (File file : fileArray)
        {
            file.delete();
        }

        testDirRoot.delete();

    }
}
