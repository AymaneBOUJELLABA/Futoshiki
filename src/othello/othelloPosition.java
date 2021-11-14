package othello;
public class othelloPosition extends Position {
    final static public int BLANK = 0;
    final static public int HUMAN = 1;
    final static public int PROGRAM = -1;
    int [] board = new int[64];
    public othelloPosition() {
        board[27]=-1;
        board[28]=1;
        board[35]=1;
        board[36]=-1;
        for(int i=0 ; i<64; i++)
        {
            if(board[i] != -1 && board[i] != 1)
            board[i]=0;
        }
}
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        for (int i=0; i<64; i++) {
            sb.append(""+board[i]+",");
        }
        sb.append("]");
        return sb.toString();
    }
}
