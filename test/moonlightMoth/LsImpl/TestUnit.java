package moonlightMoth.LsImpl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.text.SimpleDateFormat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestUnit
{
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private PipedInputStream pis = new PipedInputStream();
    private PrintStream ps;
    private String separator = "###";
    BufferedReader br;

    PrintStream oldOs;

    private String testDirNameRoot = "testDir/";
    private File testDirRoot = new File(testDirNameRoot);
    private File outFile = new File("outputFile");
    private String dirName = "dasIstDirectory/";
    private File dirFile = new File(testDirNameRoot + dirName);

    private File[] fileArray = new File[] {
            new File(testDirNameRoot + "raz.txt"),
            new File(testDirNameRoot + "dva.saf"),
            new File (testDirNameRoot + "tri.pat")};

    private File[] fileArrayInDir = new File[] {
            new File(testDirNameRoot + dirName + "alpha.txt"),
            new File(testDirNameRoot + dirName + "beta.saf"),
            new File (testDirNameRoot + dirName + "gamma.pat"),
            new File (testDirNameRoot + dirName + "omega")};

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

        oldOs = System.out;

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
    }

    @Test
    void testCaseDir() throws IOException
    {
        String expected =
                "dva.saf\ndasIstDirectory\ntri.pat\nraz.txt";


        Main.main(new String[] {testDirNameRoot});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }



    @Test
    void testCaseExtendedOutputDir() throws IOException
    {

        String expected =
                        "- 2 1000  1600      dva.saf\n" +
                        "d 7 1000  0        dasIstDirectory\n" +
                        "- 7 1000  2400      tri.pat\n" +
                        "- 6 1000  800      raz.txt";


        Main.main(new String[] {"-l", testDirNameRoot});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }

    @Test
    void testCaseHumanReadableDir() throws IOException
    {
        String expected =
                        "--w-  01.01.1970 03:00:01  1K 576B      dva.saf\n" +
                        "drwx  01.01.1970 03:00:01  0        dasIstDirectory\n" +
                        "-rwx  01.01.1970 03:00:01  2K 352B      tri.pat\n" +
                        "-rw-  01.01.1970 03:00:01  800B      raz.txt";


        Main.main(new String[] {"-h", testDirNameRoot});
        System.out.println(separator);

        assertEquals(expected, readOutput());
    }



    private String readOutput() throws IOException
    {
        String s;
        StringBuilder sb = new StringBuilder();

        while ((s = br.readLine()) != null)
        {
            if (s.equals(separator))
                break;
            sb.append(s).append("\n");
        }
        return sb.toString().trim();
    }

    @AfterAll
    public void after()
    {
        deleteTestDirs();
    }

    private void createTestDir() throws IOException
    {
        PrintWriter pw;
        byte b = 0b1111111;

        testDirRoot.mkdir();

        for (int i = 0; i < fileArray.length; i++)
        {
            fileArray[i].createNewFile();
            pw = new PrintWriter(fileArray[i]);
            for (int j = 0; j < (i+1)*800; j++)
            {
                pw.write(b);
            }
            pw.flush();
        }

        fileArray[1].setReadable(false);
        fileArray[2].setExecutable(true);

        dirFile.mkdir();

        for (int i = 0; i < fileArrayInDir.length; i++)
        {
            pw = new PrintWriter(fileArrayInDir[i]);
            fileArrayInDir[i].createNewFile();
            for (int j = 0; j < i*20; j++)
            {
                pw.write(b);
            }
            pw.flush();
        }

        long conventionalLastModified = 1000;

        for (int i = 0; i < fileArray.length; i++)
        {
            fileArray[i].setLastModified(conventionalLastModified);
        }

        dirFile.setLastModified(conventionalLastModified);
    }

    private void deleteTestDirs()
    {
        outFile.delete();

        for (File file : fileArrayInDir)
        {
            file.delete();
        }

        dirFile.delete();

        for (File file : fileArray)
        {
            file.delete();
        }

        testDirRoot.delete();

    }
}
