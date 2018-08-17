import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
class A {

    public int finalx;
    public int finaly;
    //public long startTime;
    public long endTime;
    //Set<Integer> set = new HashSet<>();

    public static char[][] readFileByLines(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 4;
            int n = Integer.parseInt(reader.readLine());  //first 1-26
            //System.out.println(n);
            int p_fruit = Integer.parseInt(reader.readLine());  //second 0-9
            //System.out.println(p);
            float time = Float.parseFloat(reader.readLine()); //third time
            //System.out.println(p);

            char[][] board = new char[n][n];

            while ((tempString = reader.readLine()) != null) {
                for(int i = 0; i < n ;i++){
                    board[line-4][i] = tempString.charAt(i);
                }
                line++;
            }
            reader.close();
            return board;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return new char[0][0];
    }


    public void output(char[][] board,char ch, int x) throws Exception{
        File file = new File("output.txt");
        FileWriter fw = new FileWriter(file);
        PrintWriter bw = new PrintWriter(fw);
        bw.write(ch);
        bw.print((int)x);
        bw.println();
        for(char[] v : board){
            for(char d: v){
                bw.write(d);
            }
            bw.println();
        }
        bw.close();
        fw.close();
//       String filepath = "D:/output.txt";
//       OutputStream out = new FileOutputStream(filepath);
//       ObjectOutputStream oout = new ObjectOutputStream(out);
//       oout.writeObject(board);
    }


    public void minmax(char board[][]) throws Exception{

        endTime = System.currentTimeMillis();
        if (stop(board)) {
            return;
        }

        int Maxscore = Integer.MIN_VALUE;
        int arf = Integer.MIN_VALUE;
        int bta = Integer.MAX_VALUE;
        int height = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '*') {
                    char ch = board[i][j];
                    char[][] boardnew = new char[board.length][board[0].length];
                    for (int x = 0; x < board.length; x++) {
                        for (int y = 0; y < board[0].length; y++) {
                            boardnew[x][y] = board[x][y];
                        }
                    }
                    count = 0;
                    int sco = replace(boardnew, i, j, ch);
                    int score = sco* sco;
                    //System.out.println(i);
                    //System.out.println(j);
                    //System.out.println(score);

                    gravity(boardnew);
                    int min = MIN_VAL(boardnew, score, 0, arf, bta,height+1);//VAL
                  //  System.out.println(min);
//                    System.out.println();
                    //MAX = Math,max(MIN,max)
                    //MaxValue = Math.max(MaxValue, min);
                    if (min > Maxscore) {
                        finalx = i;
                        finaly = j;
                    }
                    Maxscore = Math.max(Maxscore, min);
                    if(Maxscore >= bta)
                        return;
                    arf = Math.max(arf,Maxscore);
                    //      MAX_VAL(boardnew, i, j);
                    /*replace(board1,i,j,ch);
                    gravity(board1);
                    dfs(board1,i,j);*/
                }
            }
        }
        //System.out.println("X");
        //System.out.println(finalx);
        //System.out.println("Y");
        //System.out.println(finaly);
        char ch = board[finalx][finaly];

        char c1=(char) (finaly+65);
        System.out.print(c1);
        System.out.println(finalx+1);
        //System.out.println(ch);
//        for(int i = 0; i < board.length; i++){
//            for(int j = 0 ; j < board[0].length; j++){
//                System.out.print(board[i][j]);
//            }
//            System.out.println();
//        }
        replace(board,finalx,finaly,ch);

//        for(int i = 0; i < board.length; i++){
//            for(int j = 0 ; j < board[0].length; j++){
//                System.out.print(board[i][j]);
//            }
//            System.out.println();
//        }
        gravity(board);

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                System.out.print(board[i][j]);
            }
            System.out.println();
        }
        output(board,c1,finalx+1);

    }


    public int MAX_VAL(char[][] board, int maxscore, int minscore,int arf, int bta,int height) {
        //stopif(*)(-)
        //return
        endTime = System.currentTimeMillis();
        if (stop(board) || height > 2) {
            return maxscore - minscore;
        }

        int MaxValue = Integer.MIN_VALUE;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                //new [][] =
                if (board[i][j] != '*') {
                    char ch = board[i][j];
                    char[][] boardnew = new char[board.length][board[0].length];

                    for (int x = 0; x < board.length; x++) {
                        for (int y = 0; y < board[0].length; y++) {
                            boardnew[x][y] = board[x][y];
                        }
                    }


                    count = 0;
                    int sco = replace(boardnew, i, j, ch);
                    int score = sco* sco;
                    gravity(boardnew);
                    int min = MIN_VAL(boardnew, maxscore + score, minscore,arf,bta,height+1);//VAL
                    //MAX = Math,max(MIN,max)
                    MaxValue = Math.max(MaxValue, min);
                    if(MaxValue >= bta)
                        return MaxValue;
                    arf = Math.max(arf,MaxValue);

                }
            }
        }
        return MaxValue;
    }

    public int MIN_VAL(char[][] boardnew, int maxscore, int minscore, int arf, int bta,int height) {
        endTime = System.currentTimeMillis();
        if (stop(boardnew) ||height > 2)  {
            return maxscore-minscore;
        }

        int MinValue = Integer.MAX_VALUE;
        for (int i = 0; i < boardnew.length; i++) {
            for (int j = 0; j < boardnew[0].length; j++) {
                if (boardnew[i][j] != '*') {
                    char ch = boardnew[i][j];
                    char[][] board = new char[boardnew.length][boardnew[0].length];
                    for (int x = 0; x < board.length; x++) {
                        for (int y = 0; y < board[0].length; y++) {
                            board[x][y] = boardnew[x][y];
                        }
                    }
                    count = 0;
                    int sco = replace(board, i, j, ch);
                    int score = sco* sco;
                    gravity(board);
                    int max = MAX_VAL(board, maxscore, minscore + score, arf, bta,height+1);
                    MinValue = Math.min(MinValue, max);
                    if(MinValue <= arf)
                        return MinValue;
                    bta = Math.min(bta, MinValue);
                }
            }
        }
        return MinValue;

    }


    int count = 0;
    public int replace(char[][] board, int i, int j, char ch) {
        if (i < 0 || j < 0 || i >= board.length || j >= board[0].length)
            return 0;
        if (board[i][j] != ch )
            return 0;
        board[i][j] = '*';

        count++;

         replace(board, i + 1, j, ch);
         replace(board, i - 1, j, ch);
         replace(board, i, j - 1, ch);
         replace(board, i, j + 1, ch);
        //return score;
        return count;
    }

    public void gravity(char[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            int n = j;
            int m = board.length - 1;

            for(;m >= 0; m--){
                if(board[m][n]=='*')
                    break;
            }
            if(m==-1 || m==0)
                continue;
            for (int i = m-1; i >= 0; i--) {
                if (board[i][j] != '*') {
                    char tmp = board[m][n];
                    board[m][n] = board[i][j];
                    board[i][j] = tmp;
                    while(m>=0){
                        if(board[m][n]=='*')
                            break;
                        m--;
                    }
                }
            }
        }
    }

    public boolean stop(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] != '*')
                    return false;
            }
        }
        return true;

    }
}

    public class homework {

        public static void main(String[] args) throws Exception{
           // char[][] board = new char[][]{{'4','4','4'},{'4','4','4'},{'4','4','4'}} ;

            //System.out.println("kkkmmmmmm" + board[1][1]);
//            char[][] board = new char[][]{{'3', '1','0','2','3','2','2','3','1','0'},
//                    {'0','1','2','1','2','3','2','0','1','3'},
//                    {'3','0','2','1','1','1','1','1','1','3'},
//                    {'0','2','2','1','0','3','1','1','3','2'},
//                    {'0','2','3','0','0','1','1','0','1','2'},
//                    {'0','3','2','3','3','2','1','0','1','0'},
//                    {'2','0','0','3','0','2','2','0','1','2'},
//                    {'2','2','0','2','2','0','0','0','2','1'},
//                    {'0','1','3','0','0','0','0','0','2','0'},
//                    {'2','2','0','0','0','2','2','2','3','1'}
//            };
            //startTime = System.currentTimeMillis();
            A a=new A();
            //a.minmax(board);
            a.minmax(a.readFileByLines("input.txt"));

        }




    }

