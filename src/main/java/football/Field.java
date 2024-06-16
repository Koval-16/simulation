package football;

/**
 * Class Field represents single field of the pitch.
 * Each field has its own coordinates
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