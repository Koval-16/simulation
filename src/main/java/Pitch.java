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