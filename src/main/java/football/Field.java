package football;

/**
 * Klasa <code>football.Field</code> reprezentująca pojedyńcze pole.
 */

public class Field {
    private final int width;
    private final int length;

    public Field(int width, int length){
        this.width = width;
        this.length = length;
    }

    public int getWidth(){
        return width;
    }

    public int getLength(){
        return length;
    }
}