/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yihaodian.performance.util;

import java.util.Random;

/**
 *
 * @author heyin
 */
public class LineReader {
    private String[] dataList;
    private int lines;
    private int[] readSequence;
    private int cursor;
    private boolean loop;
    
//    private static final String DEFAULT_BASE = System.getProperty("user.dir");// $NON-NLS-1$
    
    public  LineReader(String filename,String[] data,boolean loop){
        this.dataList=data;
        this.lines = dataList.length;
        this.readSequence = random_serial(lines,lines);
        this.cursor = 0;
        this.loop = loop;
    }
    
    public synchronized String readRandomLine(){
        if (cursor<lines) {
            String rs = dataList[readSequence[cursor]];
            cursor += 1;
            return rs;
        }else{
            if (!loop)
                return null;
            readSequence = random_serial(lines,lines);
            String rs = dataList[readSequence[0]];
            cursor = 1;
            return rs;
        }
    }
    
    public synchronized String readNextLine(){
        if (cursor<lines) {
            String rs = dataList[cursor];
            cursor += 1;
            return rs;
        }else{
            String rs = dataList[0];
            cursor = 1;
            return rs;
        }
        
    }
    
     private static int[] random_serial(int limit, int need) {
         int[] temp = new int[limit];
         int[] result = new int[need];
         for (int i = 0; i < limit; i++)
             temp[i] = i;
        int w;
         Random rand = new Random();
         for (int i = 0; i < need; i++) {
             w = rand.nextInt(limit - i) + i;
             int t = temp[i];
             temp[i] = temp[w];
             temp[w] = t;
             result[i] = temp[i];
         }
         return result;
     }
    
}
