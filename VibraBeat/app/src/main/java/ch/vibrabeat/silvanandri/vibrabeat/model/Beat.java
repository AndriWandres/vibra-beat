package ch.vibrabeat.silvanandri.vibrabeat.model;

import com.orm.SugarRecord;

/**
 * Represents a beat in VibraBeat
 */
public class Beat extends SugarRecord {
    /** Name of the beat */
    private String name;

    /**
     * Rhythm pattern of the beat.
     * Displayed as milliseconds separated with semicolons.
     */
    private String beatString;

    /** Creates a new beat */
    public Beat() { }

    /**
     * Creates a new beat
     * @param beatString Rhythm pattern of the beat
     */
    public Beat(String beatString) {
        this(null, beatString);
    }

    /**
     * Creates a new beat
     * @param name Name of the beat
     * @param beatString Rhythm pattern of the beat
     */
    public Beat(String name, String beatString) {
        this.name = name;
        this.beatString = beatString;
    }

    /**
     * Gets the name of the beat
     * @return The name of the beat
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the beats name
     * @param name Name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the rhythm pattern of the beat
     * @return The rhythm pattern of the beat
     */
    public String getBeatString() {
        return beatString;
    }

    /**
     * Set the beats rhythm pattern
     * @param beatString Rhythm pattern to be set
     */
    public void setBeatString(String beatString) {
        this.beatString = beatString;
    }

    /**
     * Calculates the duration of a beat according to its beat string.
     * The time is displayed as mm:ss
     *
     * @return duration of beat in mm:ss format.
     */
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

    /**
     * Get the string representation of a beat.
     * @return Beat represented as a string
     */
    public String toString() {
        return name + " " + beatString;
    }
}
