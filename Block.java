
public class Block
{
    int isBomb;
    String [] numbers = new String[]{" ","blueone.png","greentwo.png","redthree.png","purplefour.png","yellowfive.png","pinksix.png","blackseven.png","blackeight.png"};
    boolean isShown = false;
    int numBombs = 0;
    int difficulty = 1;
    int x;
    int y;
    int shownColor;
    boolean isWin = false;
    boolean isFlagged = false;
    public Block(int randBomb,int x, int y,int color)
    {
        if(randBomb == 1 || randBomb == 7 || randBomb == 7){
            this.isBomb = 1;
        }
        else{
            this.isBomb = 0;
        }
        this.x = x;
        this.y = y;
        this.shownColor = color;
    }

    public void drawBlock(double x, double y){
        if(shownColor == 1){
            StdDraw.setPenColor(127,255,0);
        }
        else{
            StdDraw.setPenColor(50,205,50);
        }
        if(!(this.isShown)){
            StdDraw.filledSquare(x,y,0.5);
            if(isFlagged){
                StdDraw.picture(x,y,"flag.png",1,1);
            }
        }
        if(isShown){
            if(shownColor == 2){
                StdDraw.setPenColor(250,235,215);
                StdDraw.filledSquare(x,y,0.5);
            }
            else{
                StdDraw.setPenColor(253,245,230);
                StdDraw.filledSquare(x,y,0.5);
            }
            StdDraw.setPenColor(StdDraw.BLACK);
            if(this.isBomb == 0){
                if(this.numBombs != 0){
                    StdDraw.picture((double)(x ),(double)(y ),numbers[this.numBombs],1,1);
                }
            }
            else{
                StdDraw.picture((double)(x),(double)(y),"bomb1.png",1,1);
            }
        }
        if(isWin && isBomb == 1){
            StdDraw.setPenColor(0,100,0);
            StdDraw.filledSquare(x,y,0.5);
        }
        if(isWin && isBomb == 0){
            StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
            StdDraw.filledSquare(x,y,0.5);
        }
    }
}

