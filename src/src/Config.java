package src;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class Config {
    // short, long, work durations
    // number of work phases
    private long short_break, long_break, work;
    private int work_phases;

    public Config(String path) throws IOException {
        FileInputStream is = new FileInputStream(new File(path));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        AbstractMap.SimpleEntry<String, Long> entry = readNextParam(br);
        HashMap<String, Long> settingsMap = new HashMap<>();
        while(entry != null){
            settingsMap.put(entry.getKey(), entry.getValue());
            entry = readNextParam(br);
        }
        short_break = settingsMap.get("short_break") * 1000;
        long_break = settingsMap.get("long_break") * 1000;
        work = settingsMap.get("work") * 1000;
        work_phases = (int)(settingsMap.get("work_phases").longValue());
    }

    private AbstractMap.SimpleEntry<String, Long> readNextParam(BufferedReader br) throws IOException{
        String str = "";
        str = br.readLine();
        if(str == null) return null;
        int colon = str.lastIndexOf(':');
        String paramName = str.substring(0, colon);
        String valStr = str.substring(colon + 1, str.length());
        Long val = Long.valueOf(valStr);
        AbstractMap.SimpleEntry<String, Long> entry = new AbstractMap.SimpleEntry<>(paramName, val);
        return entry;
    }

    public long getShortDuration(){
        return short_break;
    }

    public long getLongDuration() { return long_break; }

    public long getWorkDuration(){
        return work;
    }

    public int getWorkPhases(){
        return work_phases;
    }
}
