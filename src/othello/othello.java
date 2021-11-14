package othello;
import java.util.*;
import java.util.ArrayList;

public class othello extends GameSearch
{
    
    private int [] tab = { 100, -20, 10, 5, 5, 10, -20,100,
                           -20, -50, -2, -2, -2, -2, -50, -20,
                           10, -2, -1, -1, -1,-1, -2, 10,
                           5, -2, -1, -1, -1, -1, -2, 5,
                           5, -2, -1, -1, -1, -1, -2, 5,
                           10, -2, -1, -1, -1,-1, -2, 10,
                           -20, -50, -2, -2, -2, -2, -50, -20,
                           100, -20, 10, 5, 5, 10, -20,100};

    public boolean drawnPosition(Position p) {
        if (GameSearch.DEBUG) System.out.println("drawnPosition("+p+")");
        boolean ret = true;
        othelloPosition pos = (othelloPosition)p;
        for (int i=0; i<64; i++) {
            if (pos.board[i] == othelloPosition.BLANK){
                ret = false;
                break;
            }
        }
        if (GameSearch.DEBUG) System.out.println("     ret="+ret);
        return ret;
    }
    public boolean wonPosition(Position p, boolean player) {
        if (GameSearch.DEBUG) System.out.println("wonPosition("+p+","+player+")");
        boolean ret = false;
        othelloPosition pos = (othelloPosition)p;
        int Hnum=0;
        int Pnum=0;
        int Bnum=0;
        for (int i=0; i<64; i++) {
            if (pos.board[i] == -1)
                Pnum++;
            else if (pos.board[i] == 1)
                Hnum++;
            else 
                Bnum++;
        }
        if (player)
        {
            if (Pnum == 0) ret = true;
            else if (Bnum == 0 && Hnum > Pnum) ret = true;
        }
        else
        {
           if (Hnum == 0) ret = true;
           else if (Bnum == 0 && Pnum > Hnum) ret = true; 
        }
        return ret;
    }
    public float positionEvaluation(Position p, boolean player) {
        int count = 0;
        othelloPosition pos = (othelloPosition)p;
        int a;
        if (player) a=1;
        else a=-1;
        for (int i=0; i<64; i++) {
            if (pos.board[i] == a) count++;
        }
        float evaluation =0;
        for(int i=0; i<64; i++)
        {
            if(pos.board[i] == 1) evaluation+=tab[i];
            else if(pos.board[i] == -1) evaluation-=tab[i];
        }
        if (wonPosition(p, player))  {
            return evaluation + (1.0f / 65-count);
        }
        if (wonPosition(p, !player))  {
            return -(evaluation + (1.0f / 65-count));
        }
        return evaluation;
    }
    public void printPosition(Position p) {
        System.out.println("Board position:");
        othelloPosition pos = (othelloPosition)p;
        int count = 0;
        for (int row=0; row<8; row++) {
            System.out.println();
            for (int col=0; col<8; col++) {
                if (pos.board[count] == othelloPosition.HUMAN) {
                    System.out.print("H");
                } else if (pos.board[count] == othelloPosition.PROGRAM) {
                    System.out.print("P");
                } else {
                    System.out.print("o");
                }
                count++;
            }
        }
        System.out.println();
    }
    
    public Position [] possibleMoves(Position p, boolean player)
    {
        if (GameSearch.DEBUG) System.out.println("possibleMoves("+p+","+player+")");
        othelloPosition pos = (othelloPosition)p;
        //number of occupied positions
        int count = 0, j=0;
        
        int a;
        
        if (player)
        	a=1;
        else
        	a=-1;
        
        for(int i=0; i<64; i++)
        	if (pos.board[i] != 0)
        		count++;
        
        int [] occupiedPositions = new int[count];
        //table of occupied positions
        for (int i=0; i<64; i++)
        {
            if (pos.board[i] != 0)
            {
                occupiedPositions[j] = i;  
                j++;
            }
        }
//        System.out.print("le nombre des positions occupees est: " + occupiedPositions.length);
        //table des voisins
        ArrayList<Integer> voisins = new ArrayList<Integer>();
        for (int i=0; i<count; i++)
        {
            //stocker les 8 voisins de chaque occupiedPosition
            int [] temp = new int[8];
                if (occupiedPositions[i]%8 != 7)// X%8 != 7 pour les cases a droites 
                temp[0]= occupiedPositions[i]+1;
                else temp[0]= -1;
                if(occupiedPositions[i]%8 != 0)// X%8 != 0 les cases a gauche
                temp[1]= occupiedPositions[i]-1;
                else temp[1]= -1;
                if(occupiedPositions[i]%8 != 0)
                temp[2]= occupiedPositions[i]+7;
                else temp[2]= -1;
                temp[3]= occupiedPositions[i]+8;
                temp[4]= occupiedPositions[i]+9;
                if(occupiedPositions[i]%8 != 7)
                temp[5]= occupiedPositions[i]-7;
                else temp[5]= -1;
                temp[6]= occupiedPositions[i]-8;
                temp[7]= occupiedPositions[i]-9;
 
//                System.out.print("la position occupee num : " + i + "sa position: " + occupiedPositions[i] + "\n");
                
                for (int k=0; k<8; k++)
                {
                    for (int s=0; s<voisins.size(); s++)
                    {
                        //verifier si les voisins de chaque occupiedPosition n'existe pas deja sur adjacents
                        if (temp[k] == voisins.get(s))
                        {
                            temp[k]=-1;
                        }  
                    }
                    //verifier si les voisins sont occuppees 
                        if(temp[k] >=0 && temp[k] <=63)
                         if (pos.board[temp[k]] != 0) 
                        {
                            temp[k]=-1;
                        }
                }
                //ajouter les adjacents
                for (int k=0; k<8; k++)
                {
                    if(temp[k] <=63 && temp[k] >= 0)
                        voisins.add(temp[k]);
                }
                
//                System.out.print("ses voisins  non occuppees sont: \n");
//                for(int s=0; s<8;s++)
//                {
//                    if(temp[s] != -1)
//                    System.out.print("-----: " + temp[s] + "\n");
//                }
        }

        ArrayList<Position> ret1 = new ArrayList<Position>();
        for (int i=0; i< voisins.size(); i++)
        {
            int courant = voisins.get(i);
            int colonne = (courant + 1)% 8;
            boolean added=false;
            /* premiere condition:
            il y a au moins une case adjacente qui n'est pas du player courant */
            //deuxieme condition
            //il y a au moins une case du player courant sur la ligne/colonne/diagonale            
            othelloPosition pos2 = new  othelloPosition();
            //la ligne a droite
            for (int l=0; l<64; l++) pos2.board[l] = pos.board[l];
            {
                int k=courant+1;
                while(k>=0 && k<=63 && k<courant+8-colonne)
                {
                if(pos.board[k] == 0)
                    break;
                else if(pos.board[k] == a)
                {
                    if(k != courant+1)
                    {
                        int m=k;
                        while ( m>=courant && m>=0 && m<64)
                        {
                            pos2.board[m]=a;
                         m--;
                        }
                        added=true;//added: pour verifier si ce possible move est deja ajoute a ret
                    }
                    break;
                }
                k++;
                }
            }
            //la ligne a gauche
            {
                int k=courant-1;
                while(k>=0 && k<=63 && k>courant-colonne )
                {
                if(pos.board[k] == 0)
                    break;
                else if(pos.board[k] == a)
                {
                    if(k != courant-1)
                    {
                        int m=k;
                        while (m<=courant && m>=0 && m<64)
                        {
                            pos2.board[m]=a;
                            m++ ;
                        }
                        added=true;
                    }
                    break;
                }
                k--;
                }
            }
            //la colonne en bas
            {
                int k=courant+8;
                while(k>=0 && k<=63)
                {
                if(pos.board[k] == 0)
                    break;
                else if(pos.board[k] == a)
                {
                    if(k != courant+8)
                    {
                        int m=k;
                        while (m>=courant && m>=0 && m<64)
                        {
                            pos2.board[m]=a;
                            m-=8;
                        }
                        added=true;
                    }
                    break;
                } 
                k+=8;
                }
            }
            // la colonne en haut
            {
            int k=courant-8;
                while(k>=0 && k<=63)
                {
                if(pos.board[k] == 0)
                    break;
                else if(pos.board[k] == a)
                {
                    if(k != courant-8)
                    {
                        int m=k;
                        while(m<=courant && m>=0 && m<64)
                        {
                            pos2.board[m]=a;
                            m+=8;
                        }
                    added=true;
                    }
                    break;
                } 
                k-=8;
                }
            }
            //diagonale a gauche en bas
            { int tempcolonne = (courant+7+1)%8;
            int k=courant+7;
            while(k>=0 && k<=63 && tempcolonne<colonne)
            {
                tempcolonne = (k+1)%8;
                if(pos.board[k] == 0)
                    break;
                else if(pos.board[k] == a)
                {
                    if(k != courant+7)
                    {
                        int m=k;
                        while(m>=courant && m>=0 && m<64)
                        {
                            pos2.board[m]=a;
                            m-=7;
                        }
                        added=true;
                    }
                    break;
                } 
                k+=7;
            }
            }
            //diagonale en haut a droite
            {
                int tempcolonne = (courant-7+1)%8;
                int k=courant-7;
                while(k>=0 && k<=63 && tempcolonne>colonne)
            { 
                tempcolonne = (k+1)%8;
                if(pos.board[k] == 0)
                    break;
                else if(pos.board[k] == a)
                {
                    if(k != courant-7)
                    {
                        int m=k;
                        while(m<=courant && m>=0 && m<64)
                        {
                            pos2.board[m]=a;
                        m+=7 ;
                        }
                        added=true;
                    }
                    break;
                }
                k-=7;
            }
            }
            //diagonale en bas a droite
            {
                int tempcolonne = (courant+9+1)%8;
                int k=courant+9;
                while(k>=0 && k<=63 && tempcolonne>colonne)
            {
                tempcolonne = (k+1)%8;
                if(pos.board[k] == 0)
                    break;
                else if(pos.board[k] == a)
                {
                    if(k != courant+9)
                    {
                        int m=k;
                        while(m>=courant && m>=0 && m<64)
                        {
                            pos2.board[m]=a;
                            m-=9;
                        }
                            added=true;
                    }
                    break;
                }
                k+=9;
            }
            }
            //diagonale en haut a gauche
            {
                int tempcolonne = (courant-9+1)%8;
                int k=courant-9;
                while(k>=0 && k<=63 && tempcolonne<colonne)
            {
                tempcolonne=(k+1)%8;
                if(pos.board[k] == 0)
                    break;
                else if(pos.board[k] == a)
                {
                    if(k != courant-9)
                    {
                        int m=k;
                        while(m<=courant && m>=0 && m<64)
                        {
                            pos2.board[m]=a;
                        m+=9;
                        }
                        added=true;
                    }
                    break;
                } 
                k-=9;
            }
            }
            if (added)
            {
                ret1.add(pos2);
            }
        }
        if (ret1.size()==0)
            return null;
        Position [] ret = new Position[ret1.size()];
        for (int i=0; i<ret1.size(); i++)
        {
            ret[i]=ret1.get(i);
        }
        return ret;
    }
    public Position makeMove(Position p, boolean player, Move move)
    {
        if (GameSearch.DEBUG) System.out.println("Entered TicTacToe.makeMove");
        othelloMove m = (othelloMove)move;
        othelloPosition pos = (othelloPosition)p;
        othelloPosition pos2 = new  othelloPosition();
        for (int i=0; i<64; i++) pos2.board[i] = pos.board[i];
        int pp;
        if (player) pp =  1;
        else        pp = -1;
        if (GameSearch.DEBUG) System.out.println("makeMove: m.moveIndex = " + m.moveIndex);
        System.out.println("move index  : "+m.moveIndex);
        pos2.board[m.moveIndex] = pp;
        int a=m.moveIndex-8;
                int a1=m.moveIndex+8;
                int c=m.moveIndex-1;
                int c1=m.moveIndex+1;
                int d=m.moveIndex-9;
                int d1=m.moveIndex+9;
                int d2=m.moveIndex-7;
                int d3=m.moveIndex+7;
                boolean f=false;
                ArrayList<Integer> ai=new ArrayList<Integer>();
                //pour les voisins vertical en haut
                while(a>0)
                {
                    if(pos.board[a]==pp*-1) 
                    {
                        ai.add(a);
                        a-=8;
                        f=true;
                    } 
                     else if(pos.board[a]==pp)
                    {
                    if(f)
                    {
                     for(int j=0;j<ai.size();j++) 
                     pos2.board[ai.get(j)]=pp;
                    }
                    break;
                    }
                    else break;
                }
                f=false;
                ai.clear();
                //pour les voisins verticles en bas 
                while(a1<64)
                {
                    if(pos.board[a1]==pp*-1) 
                    {
                        ai.add(a1);
                        a1+=8;
                        f=true;
                    } 
                    else if(pos.board[a1]==pp)
                    {
                    if(f)
                    {
                     for(int j=0;j<ai.size();j++) 
                     pos2.board[ai.get(j)]=pp;
                    }
                    break;
                    }
                    else break;
                }
                //pour les voisins horizontales à gauche
                int temp=c/8*8;
                ai.clear();
                f=false;
                while(c>temp-1)
                {
                    if(pos.board[c]==pp*-1) 
                    {
                        ai.add(c);
                        c-=1;
                        f=true;
                    }
                    else if(pos.board[c]==pp)
                    {
                    if(f)
                    {
                     for(int j=0;j<ai.size();j++) 
                     pos2.board[ai.get(j)]=pp;
                    }
                    break;
                    }
                    else break;
                }
                //pour les voisins horizontales à droite
                int temp2=(c1/8*8)+8;
                f=false;
                ai.clear();
                while(c1<temp2)
                {
                    if(pos.board[c1]==pp*-1) 
                    {
                       ai.add(c1);
                        c1+=1;
                        f=true;
                    } 
                    else if(pos.board[c1]==pp)
                    {
                    if(f)
                    {
                     for(int j=0;j<ai.size();j++) 
                     pos2.board[ai.get(j)]=pp;
                    }
                    break;
                    }
                    else break;
                }
                //Pour les voisins au diagonales 
                f=false;
                ai.clear();
                if(m.moveIndex%8!=0 && d>=0)
                {
                do 
                {
                 if(pos.board[d]==pp*-1) 
                    {
                        ai.add(d);
                   f=true;
                        d-=9;
                    } 
                 else if(pos.board[d]==pp)
                    {
                    if(f)
                    {
                     for(int j=0;j<ai.size();j++) 
                     pos2.board[ai.get(j)]=pp;
                    }
                    break;
                    }
                    else break;
                }while(d%8!=0 && d>=0);
                }
                f=false;
                ai.clear();
                if(m.moveIndex%8!=0 && d3<64)
                {
                do 
                {
                 if(pos.board[d3]==pp*-1) 
                    {
                        ai.add(d3);
                        f=true;
                        d3+=7;
                    }
                 else if(pos.board[d3]==pp)
                    {
                    if(f)
                    {
                     for(int j=0;j<ai.size();j++) 
                     pos2.board[ai.get(j)]=pp;
                    }
                    break;
                    }
                    else break;
                }while(d3%8!=0 && d3<64);
                }
                f=false;
                ai.clear();
                 if((m.moveIndex+1)%8!=0 && d2>=0)
                 {
                do 
                {
                 if(pos.board[d2]==pp*-1) 
                    {
                        ai.add(d2);
                        f=true;
                        d2-=7;
                    } 
                 else if(pos.board[d2]==pp)
                    {
                    if(f)
                    {
                     for(int j=0;j<ai.size();j++) 
                     pos2.board[ai.get(j)]=pp;
                    }
                    break;
                    }
                    else break;
                }while((d2+1)%8!=0 && d2 >= 0);
                 }
                 f=false;
                 ai.clear();
                while(d1<64)
                {
                 if(pos.board[d1]==pp*-1) 
                    {
                      ai.add(d1);
                        d1+=9;
                        f=true;
                    } 
                 else if(pos.board[d1]==pp)
                    {
                    if(f)
                    {
                     for(int j=0;j<ai.size();j++) 
                     pos2.board[ai.get(j)]=pp;
                    }
                    break;
                    }
                    else break;
                }
        return pos2;
    }
    public boolean reachedMaxDepth(Position p, int depth) {
        boolean ret = false;
        if(depth>=5) ret = true;
        else 
        if (wonPosition(p, false)) ret = true;
        else if (wonPosition(p, true))  ret = true;
        else if (drawnPosition(p))      ret = true;
        if (GameSearch.DEBUG) {
            System.out.println("reachedMaxDepth: pos=" + p.toString() + ", depth="+depth
                               +", ret=" + ret);
        }
        return ret;
    }
    public Move createMove()
    {
        if (GameSearch.DEBUG) System.out.println("Enter blank square index [0,8]:");
        int ch = 0;
        try {
             Scanner sc=new Scanner(System. in) ;
             ch=sc.nextInt();
        } catch (Exception e) { }
        othelloMove mm = new othelloMove();
        mm.moveIndex = ch;
        return mm;
    }
    
    public static void main(String [] args)
    {
        othelloPosition p = new othelloPosition();
        othello ttt = new othello();
        ttt.playGame(p, true);
    }
}
