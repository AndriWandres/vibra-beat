package ch.vibrabeat.silvanandri.vibrabeat.model;

import com.orm.SugarRecord;

public class Beat extends SugarRecord {
    String name = null;
    String beatString = null;

    public Beat() {}

    public Beat(String beatString) {
        this(null, beatString);
    }

    public Beat(String name, String beatString) {
        this.name = name;
        this.beatString = beatString;
    }

    public String toString() {
        return name + " " + beatString;
    }
}
