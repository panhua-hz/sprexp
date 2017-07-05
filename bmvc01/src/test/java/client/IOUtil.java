package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;

public class IOUtil {
    private static final int BUFF_SIZE = 256;
    
    public static void iowriter1(InputStream is, OutputStream os) throws IOException{
        Reader rd = new InputStreamReader(is, "UTF-8");
        Writer wt = new OutputStreamWriter(os, "UTF-8");
        rwriter(rd,wt);
    }
    public static void rwriter(Reader rd, Writer wt) throws IOException{
        int size = 0;
        char[] buf = new char[BUFF_SIZE];
        while((size=rd.read(buf))>0){
            wt.write(buf, 0, size);
        }
        wt.flush();
    }
    
    public static void iowriter(InputStream is, OutputStream os) throws IOException{
        int size = 0;
        byte[] bf = new byte[BUFF_SIZE];
        while ((size=is.read(bf)) != -1){
            os.write(bf, 0, size);
        }
        os.flush();
    }
}
