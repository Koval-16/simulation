package football;

/**
 * Class pitch represents the football pitch.
 * It contains fields.
 * The pitch should be 5x6.
 * The X-coordinate divides pitch into sides:
 * (left wing, left half-space, center, right half-space, right wing)
 * The Y-coordinate divides pitch into zones:
 * (defensive, center, offensive)
 */

public class Pitch {
    private final Field[][] pitch;

    public Pitch(int width, int length){
        pitch = new Field[width][length];
        for(int i=0; i<width; i++){
            for(int j=0; j<length; j++){
                pitch[i][j] = new Field(i, j);
            }
        }
    }

    public Field[][] getPitch(){
        return pitch;
    }
}