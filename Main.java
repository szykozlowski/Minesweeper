//Szymon Kozlowski
//2018
public class Main
{
    public static int fakeBombCount = 0;
    public static int actualBombCount = 0;
    public static void main(String args[])
    {
        int size = 40;
        int mapScaleX = size;
        int mapScaleX2 = 0;
        int mapScaleY = size;
        int mapScaleY2 = 0;
        boolean firstClick = true;
        boolean game = true;
        boolean win = false;
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(1000,1000);
        StdDraw.setXscale(0,mapScaleX);
        StdDraw.setYscale(0,mapScaleY);
        Board board = new Board(size);
        board.genBoard();
        board.checkBombs();
        fakeBombCount = board.bombCount;
        actualBombCount = board.bombCount;
        while(game){
            StdDraw.setYscale(mapScaleY2,mapScaleY);
            StdDraw.setXscale(mapScaleX2,mapScaleX);
            if(StdDraw.isMousePressed() && firstClick == true){
                board.startMap((int)StdDraw.mouseX(),(int)StdDraw.mouseY());
                board.mapRegen();
                board.cleanBoard();
                board.drawBoard();
                StdDraw.show();
                firstClick = false;
                fakeBombCount = board.bombCount;
                actualBombCount = board.bombCount;
            }
            else if(StdDraw.isMousePressed()){
                if(StdDraw.mouseX() > 0 && StdDraw.mouseX() < size && StdDraw.mouseY() > 0 && StdDraw.mouseY() < size){
                    if(board.Blocks[(int)(StdDraw.mouseX())][(int)(StdDraw.mouseY())].isFlagged == false){
                        board.Blocks[(int)(StdDraw.mouseX())][(int)(StdDraw.mouseY())].isShown = true;    
                        if(board.Blocks[(int)(StdDraw.mouseX())][(int)(StdDraw.mouseY())].isBomb == 1){
                            board.lose();
                            //game = false;
                            board.drawBoard();
                            StdDraw.show();
                            StdDraw.pause(2000);
                        }
                        board.destroyBoard((int)StdDraw.mouseX(),(int)StdDraw.mouseY());
                    }
                }
            }

            if(StdDraw.isKeyPressed(32)){                
                if(board.Blocks[(int)StdDraw.mouseX()][(int)StdDraw.mouseY()].isFlagged == true){
                    board.Blocks[(int)StdDraw.mouseX()][(int)StdDraw.mouseY()].isFlagged = false;
                    fakeBombCount += 1;
                    if(board.Blocks[(int)StdDraw.mouseX()][(int)StdDraw.mouseY()].isBomb == 1){
                        actualBombCount += 1;
                    }
                }
                else{
                    board.Blocks[(int)StdDraw.mouseX()][(int)StdDraw.mouseY()].isFlagged = true;
                    fakeBombCount -= 1;
                    if(board.Blocks[(int)StdDraw.mouseX()][(int)StdDraw.mouseY()].isBomb == 1){
                        actualBombCount -= 1;
                    }
                }
                board.drawBoard();
                StdDraw.show();
                StdDraw.pause(200);
            }
            if(StdDraw.isKeyPressed(16)){  
                mapScaleY -= 1;
                mapScaleX -= 1;
                StdDraw.pause(60);
            }
            if(StdDraw.isKeyPressed(17)){  
                mapScaleY += 1;
                mapScaleX += 1;
                StdDraw.pause(60);
            }
            if(StdDraw.isKeyPressed(40)){  
                mapScaleY -= 1;
                mapScaleY2 -= 1;
                StdDraw.pause(60);
            }
            if(StdDraw.isKeyPressed(38)){  
                mapScaleY += 1;
                mapScaleY2 += 1;
                StdDraw.pause(60);
            }
            if(StdDraw.isKeyPressed(39)){  
                mapScaleX += 1;
                mapScaleX2 += 1;
                StdDraw.pause(60);
            }
            if(StdDraw.isKeyPressed(37)){  
                mapScaleX -= 1;
                mapScaleX2 -= 1;
                StdDraw.pause(60);
            }
            board.cleanBoard();
            board.drawBoard();
            StdDraw.show();
            StdDraw.clear(StdDraw.BOOK_LIGHT_BLUE);
            if(board.checkRemainingSquares() == 0){
                //game = false;
                win = true;
                board.win();
                StdDraw.pause(1000);
            }
            if(StdDraw.isKeyPressed(82)){  
                board = new Board(size);
                board.genBoard();
                board.checkBombs();
                fakeBombCount = board.bombCount;
                actualBombCount = board.bombCount;
                firstClick = true;
                game = true;
                win = false;
            }
        }
    }
}
