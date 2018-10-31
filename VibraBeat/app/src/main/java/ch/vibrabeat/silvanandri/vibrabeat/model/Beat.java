package ch.vibrabeat.silvanandri.vibrabeat.model;

import com.orm.SugarRecord;

public class Beat extends SugarRecord {
    private String name;
    private String beatString;

    public Beat() { }

    public Beat(String beatString) {
        this(null, beatString);
    }

    public Beat(String name, String beatString) {
        this.name = name;
        this.beatString = beatString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeatString() {
        return beatString;
    }

    public void setBeatString(String beatString) {
        this.beatString = beatString;
    }

    public String getBeatLength() {
        if (beatString == null || beatString == "") {
            return "00:00";
        }

        String res = "";
        int ms = 0;

        String[] patternStr = beatString.split(";");

        // Count duration by adding up milliseconds
        for(int i = 0; i < patternStr.length; i++) {
            ms += Integer.parseInt(patternStr[i]);
        }

        // Add minutes
        res += String.format("%02d", (int) Math.floor(ms / 60000)) + ":";

        ms %= 60000;

        // Add seconds
        res += String.format("%02d", (int) Math.ceil(ms / 1000));

        return res;
    }

    public String toString() {
        return name + " " + beatString;
    }
}
