/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yihaodian.performance.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author heyin
 */
public class PerfFileServer {

    private Map<String, LineReader> readerMap;
    private Map<String, String[]> dataMap;
    private static PerfFileServer server;

    private PerfFileServer() {
        readerMap = new HashMap<String, LineReader>();
        dataMap = new HashMap<String, String[]>();
    }

    public static PerfFileServer getInstance() {
        if (server == null) {
            server = new PerfFileServer();
        }
        return server;
    }
    
    public LineReader getLineReader(String name) {
        return getLineReader(name,true);
    }

    public LineReader getLineReader(String name,boolean loop) {
        LineReader rs = readerMap.get(name);
        if (rs == null) {
            synchronized (this) {
                String[] fileData = readFile(name);
                rs = new LineReader(name,fileData,loop);
                readerMap.put(name, rs);
                dataMap.put(name, fileData);
                return rs;
            }
        } else {
            return rs;
        }
    }

    public void reset(String name) {
        readerMap.remove(name);
    }

    private String[] readFile(String path) {
        try {
            String classPath = this.getClass().getClassLoader().getResource("").getPath();
//            System.out.println(classPath);
            File base = new File(classPath);
            File f = new File(path);
            f = f.isAbsolute() ? f : new File(base, path);

            BufferedReader reader = new BufferedReader(new FileReader(f));
            List<String> results = new ArrayList<String>();
            String line = reader.readLine();
            while (line != null) {
                results.add(line);
                line = reader.readLine();
            }
            reader.close();
            return results.toArray(new String[0]);
        } catch (IOException e) {
            return null;
        }
    }
}
