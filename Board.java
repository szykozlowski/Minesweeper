
public class Board
{
    int rows;
    int cols;
    int size;
    Block[][] Blocks;
    int random;
    int color;
    int temp;
    int bombCount = 0;
    int randClearx = (int)(Math.random() * (size/3) + 1) + 2;
    int randCleary = (int)(Math.random() * (size/3) + 1) + 2;
    public Board(int size)
    {
        this.rows = size;
        this.cols = size;
        this.size = size;
        Blocks = new Block[rows][cols];
    }

    public void genBoard(){
        for(int i = 0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                if((i+j)%2 == 0){
                    color = 1;
                }
                else{color = 2;}
                if(i >= 1 && j >= 1 && i <= rows - 2 && j <= cols - 2){
                    this.random = (int)(Math.random() * 10);
                    this.Blocks[i][j] = new Block(random,i,j,color);
                }
                else{this.Blocks[i][j] = new Block(17,i,j,color);}
            }
        }
    }

    public void printBoard(){
        for(int i = 0;i<rows;i++){
            System.out.println();
            for(int j = 0;j<cols;j++){
                if(Blocks[i][j].isBomb == 1){
                    System.out.print("1" + " ");
                }
                else{System.out.print("0" + " ");}
            }
        }
    }

    public void checkBombs(){
        bombCount = 0;
        for(int i = 1;i<rows - 1;i++){
            for(int j = 1;j<cols - 1;j++){
                if(Blocks[i][j].isBomb == 0){
                    for (int x = -1; x <= 1; x++) {
                        for (int y = -1; y <= 1; y++) {
                            Blocks[i][j].numBombs += Blocks[i+y][j+x].isBomb; 
                        }
                    }
                }
                else if(Blocks[i][j].isBomb == 1){
                    bombCount += 1;
                    continue;
                }
                else{continue;}
            }
        }

    }

    public void drawBoard(){
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text((double)(size/2),(double)(size - 0.5),"Bombs Remaining: " + Integer.toString(Main.fakeBombCount));
        for(int i = 1;i<rows - 1;i++){
            for(int j = 1;j<cols - 1;j++){
                Blocks[i][j].drawBlock((double)(i + 0.5),(double)(j + 0.5));
            }          
        }
    }

    public void startMap(int xCord, int yCord){
        for (int y = -randCleary; y <= randCleary; y++) {
            for (int x =-randClearx; x <= randClearx; x++) {
                if((xCord - Math.abs(x) > 0 && yCord - Math.abs(y) > 0) && xCord + x < size && yCord + y < size){
                    Blocks[xCord+x][yCord+y].isBomb = 0;
                    Blocks[xCord+x][yCord+y].numBombs = 0;
                }
            }
        }

        for (int y = -randCleary; y <= randCleary; y++) {
            for (int x =-randClearx; x <= randClearx; x++) {
                if((xCord - x > 0 && yCord - y > 0) && xCord + x < size && yCord + y < size){
                    destroyBoard(xCord+x,yCord+y);
                }
            }
        }
    }

    public void destroyBoard(int xCord, int yCord){
        if(xCord > 0 && yCord > 0 && xCord < size && yCord < size){
            if(Blocks[xCord][yCord].isBomb == 0 && Blocks[xCord][yCord].numBombs == 0){
                Blocks[xCord][yCord].isShown = true;
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {
                        if(y == -1|| y == 1){
                            temp = 0;
                        }
                        else{
                            temp = x;
                        }
                        if((xCord - 1 > 0 && yCord - 1 > 0) && xCord + 1 < size && yCord + 1 < size){
                            if(Blocks[xCord+temp][yCord+y].isBomb == 0 && Blocks[xCord+temp][yCord+y].numBombs != 0){
                                //Blocks[xCord+temp][yCord+y].isShown = true;
                            }
                            else if(Blocks[xCord+temp][yCord+y].isBomb == 0 && Blocks[xCord+temp][yCord+y].numBombs == 0 && Blocks[xCord+temp][yCord+y].isShown == false){
                                Blocks[xCord+temp][yCord+y].isShown = true;
                                destroyBoard(xCord+temp,yCord+y);
                            }
                            else if(Blocks[xCord+temp][yCord+y].isBomb == 0){
                                Blocks[xCord+temp][yCord+y].isShown = true;
                            }
                        } 
                    }
                }
            }
        }
    }

    public void cleanBoard(){        
        for(int i = 1;i<rows - 1;i++){
            for(int j = 1;j<cols - 1;j++){
                if(Blocks[j][i].isBomb == 0 && Blocks[j][i].isShown == false){
                    for (int y = -1; y <= 1; y++) {
                        for (int x = -1; x <= 1; x++) {
                            if(Blocks[j + x][i + y].isBomb == 0 && Blocks[j + x][i + y].numBombs == 0 && Blocks[j + x][i + y].isShown == true){
                                Blocks[j][i].isShown = true;
                            }  
                        } 
                    }          
                }
            }
        }
    }

    public void mapRegen(){
        for(int i = 0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                Blocks[i][j].numBombs = 0;
            }          
        }
        checkBombs();
    }

    public int checkRemainingSquares(){
        int remaningSquares = 0;
        for(int i = 0;i<rows;i++){
            for(int j = 0;j<cols;j++){
                if(i >= 1 && j >= 1 && i <= rows - 2 && j <= cols - 2){
                    if(Blocks[i][j].isBomb == 0 && Blocks[i][j].isShown == false){
                        remaningSquares += 1;    
                    }
                }
                else{continue;}
            }
        }
        return remaningSquares;
    }   

    public void lose(){
        for(int i = rows - 1;i>0;i--){
            for(int j = 0;j<cols;j++){
                if(Blocks[j][i].isBomb == 1){
                    Blocks[j][i].isShown = true;
                    drawBoard();
                    StdDraw.show();
                    StdDraw.pause(60);
                }
            }
        }
    }   

    public void win(){
        for(int i = rows - 1;i>0;i--){
            for(int j = 0;j<cols;j++){
                if(Blocks[j][i].isBomb == 1){
                    Blocks[j][i].isWin = true;
                    drawBoard();
                    StdDraw.show();
                    //StdDraw.pause(10);
                }
                if(Blocks[j][i].isBomb == 0){
                    Blocks[j][i].isWin = true;
                    drawBoard();
                    StdDraw.show();
                    //StdDraw.pause(10);
                }
            }
        }
    }
}

