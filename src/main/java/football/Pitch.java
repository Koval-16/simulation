package football;

/**
 * Klasa <code>football.Pitch</code> reprezentuje boisko o wymiarach 5x6 pól.
 */

public class Pitch {
    private Field[][] pitch;

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