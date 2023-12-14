package config;

import geometry.IntCoordinates;

import static config.Cell.Content.DOT;
import static config.Cell.*;
import static config.Cell.Content.NOTHING;
import static config.Cell.Content.ENERGIZER;
import java.io.File;  // permet d'utiliser des fichiers
import java.io.FileNotFoundException;  // permet de gérer les erreurs de fichiers non existants
import java.util.Scanner; // permet de lire des fichiers
import java.io.FileWriter; // permet d'écrire dans des fichiers.txt
import java.io.IOException; //permet de gérer les erreurs d'exception IO (avec les fichiers)
import java.util.ArrayList;
import java.util.Random;
import static config.Cell.Content.FRUIT;

public class MazeConfig {
    public MazeConfig(Cell[][] grid, boolean[][] gomme,boolean[][] fruit, IntCoordinates pacManPos, IntCoordinates blinkyPos,
            IntCoordinates pinkyPos,
            IntCoordinates inkyPos, IntCoordinates clydePos) {
        this.grid = new Cell[grid.length][grid[0].length];
        for (int i = 0; i < getHeight(); i++) {
            if (getWidth() >= 0)
                System.arraycopy(grid[i], 0, this.grid[i], 0, getWidth());
        }
        
        this.pacManPos = pacManPos;
        this.blinkyPos = blinkyPos;
        this.inkyPos = inkyPos;
        this.pinkyPos = pinkyPos;
        this.clydePos = clydePos;
        pacGomme = gomme;
        Fruits = fruit;
        KeepPacgomme = new int[pacGomme.length][pacGomme[0].length];
        keep();
    }
    private int[][] KeepPacgomme;
    private final Cell[][] grid;
    private boolean[][] pacGomme;
    private boolean[][] Fruits;
    private final IntCoordinates pacManPos, blinkyPos, pinkyPos, inkyPos, clydePos;
   

    public IntCoordinates getPacManPos() {
        return pacManPos;
    }

    public IntCoordinates getBlinkyPos() {
        return blinkyPos;
    }

    public IntCoordinates getPinkyPos() {
        return pinkyPos;
    }

    public IntCoordinates getInkyPos() {
        return inkyPos;
    }

    public IntCoordinates getClydePos() {
        return clydePos;
    }

    public int getWidth() {
        return grid[0].length;
    }

    public int getHeight() {
        return grid.length;
    }

    public boolean getPacGomme(int x, int y) {
        return pacGomme[y][x];
    }

    public void setPacGomme(int x, int y, boolean b) {
        pacGomme[y][x] = b;
    }

    public Cell getCell(IntCoordinates pos) {
        return grid[Math.floorMod(pos.y(), getHeight())][Math.floorMod(pos.x(), getWidth())];
    }

    public boolean[][] getFruit(){
        return Fruits;
    }

    public int[][] getKeepPacgomme() {
        return KeepPacgomme;
    }

    public void keep(){
        for(int i = 0 ; i < pacGomme.length ; i++ ){
            for(int y = 0 ; y < pacGomme[0].length ; y++){
                if(pacGomme[i][y] == true){
                    KeepPacgomme[i][y] = 1;
                } else {
                    KeepPacgomme[i][y] = 0;
                }
            }
        }
    }

   
    public Cell[][] getGrid(){
        return this.grid;
    }

    public static MazeConfig mapLoader(String x){
        /*
         * Format de la map :
         * # = un mur
         * . = un point
         * F = un Fruit
         * * = un pac-gomme
         * = rien
         */
        //a = largeur de la map 
        //b = longueur de la map
        //c = largeur du format de la map
        //d = longueur du format de la map
        //o et p = position approximative du spawn des fantômes
        int a=0;
        int b=0;
        int c=0;
        int d=0;
        int o=0;
        int p=0;
        if(x.equals("map1.map")){
        a=26;
        b=35;
        c=a+2;
        d=b+2;
        o=15;
        p=11;
        }
        if(x.equals("map2.map")){
        a=8;
        b=9;
        c=a+2;  
        d=b+2;
        o=2;
        p=4;
        }
        if(x.equals("map3.map")){
        a=13;
        b=21;
        c=a+2;
        d=b+2;
        o=8;
        p=5;
        }

        Cell[][]tab = new Cell[a][b];
        boolean[][]gomme = new boolean[a][b];
        boolean[][] fruit = new boolean[a][b];
        String[][]t = new String[c][d];
        int f = 0;
        int g = 0;
        try {//partie du code qui récupère les informations dans le fichier.map qui contient le format de map
            File myObj = new File("./src/main/resources/"+x);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
            myReader.useDelimiter("");
            for(int i =0;i<c;i++){
                String dat = myReader.nextLine();
                for(int j = 0 ;j<d;j++){
              char data=dat.charAt(j);
             t[i][j]=""+data;
          }
         }
        }
        myReader.close();
    }
          catch (FileNotFoundException e) {
            System.out.println("Une erreur est survenue.");
            e.printStackTrace();
        }
        for (int k = 1; k < c - 1; k++) {// on regarde les informations récupérées du fichier.map et en fonction des
                                         // caractères, on créer des cellules différentes(ex: mur à gauche si il y a un
                                         // symbole "#" à gauche.).
            for (int l = 1; l < d - 1; l++) {
                if (t[k][l].equals(".")) {
                    boolean n = false;
                    boolean w = false;
                    boolean s = false;
                    boolean e = false;
                    int i = 0;
                    if (t[k - 1][l].equals("#")) {
                        n = true;
                    }
                    if (t[k][l - 1].equals("#")) {
                        w = true;
                    }
                    if (t[k + 1][l].equals("#")) {
                        s = true;
                    }
                    if (t[k][l + 1].equals("#")) {
                        e = true;
                    }
                    tab[f][g] = new Cell(n, e, s, w, DOT, i);
                    gomme[f][g] = false;
                    fruit[f][g] = false;
                    g++;
                    if (g % b == 0 && g != 0) {
                        f++;
                        g = 0;
                    }
                } else if (t[k][l].equals("*")) {
                    boolean n = false;
                    boolean w = false;
                    boolean s = false;
                    boolean e = false;
                    int i = 0;
                    if (t[k - 1][l].equals("#")) {
                        n = true;
                    }
                    if (t[k][l - 1].equals("#")) {
                        w = true;
                    }
                    if (t[k + 1][l].equals("#")) {
                        s = true;
                    }
                    if (t[k][l + 1].equals("#")) {
                        e = true;
                    }
                    tab[f][g] = new Cell(n, e, s, w, ENERGIZER, i);
                    gomme[f][g] = true;
                    fruit[f][g] = false;
                    g++;
                    if (g % b == 0 && g != 0) {
                        f++;
                        g = 0;
                    }
                } else if (t[k][l].equals("_")) {
                    boolean n = false;
                    boolean w = false;
                    boolean s = true;
                    boolean e = false;
                    int i = 1;
                    if (t[k - 1][l].equals("#")) {
                        n = true;
                    }
                    if (t[k][l - 1].equals("#")) {
                        w = true;
                    }
                    if (t[k][l + 1].equals("#")) {
                        e = true;
                    }
                    tab[f][g] = new Cell(n, e, s, w, DOT, i);
                    gomme[f][g] = false;
                    fruit[f][g] = false;
                    g++;
                    if (g % b == 0 && g != 0) {
                        f++;
                        g = 0;
                    }
                } else if (t[k][l].equals(" ")) {
                    boolean n = false;
                    boolean w = false;
                    boolean s = false;
                    boolean e = false;
                    int i = 0;
                    if (t[k - 1][l].equals("#")) {
                        n = true;
                    }
                    if (t[k][l - 1].equals("#")) {
                        w = true;
                    }
                    if (t[k + 1][l].equals("#") | t[k + 1][l].equals("F") ) {
                        s = true;
                    }
                    if (t[k][l + 1].equals("#")) {
                        e = true;
                    }
                    tab[f][g] = new Cell(n, e, s, w, NOTHING, i);
                    gomme[f][g] = false;
                    fruit[f][g] = false;
                    g++;
                    if (g % b == 0 && g != 0) {
                        f++;
                        g = 0;
                    }
                    
                } else if (t[k][l].equals("#")) {
                    boolean n = false;
                    boolean w = false;
                    boolean s = false;
                    boolean e = false;
                    int i = 0;
                    if (t[k - 1][l].equals(".") | t[k - 1][l].equals(" ") ) {
                        n = true;
                    }
                    if (t[k][l - 1].equals(".")| t[k][l - 1].equals(" ")) {
                        w = true;
                    }
                    if (t[k + 1][l].equals(".") | t[k + 1][l].equals("F")) {
                        s = true;
                    }
                    if (t[k][l + 1].equals(".") | t[k][l+1].equals(" ")) {
                        e = true;
                    }
                    if (t[k - 1][l].equals("*")) {
                        n = true;
                    }
                    if (t[k][l - 1].equals("*")) {
                        w = true;
                    }
                    if (t[k + 1][l].equals("*")) {
                        s = true;
                    }
                    if (t[k + 1][l].equals("_")) {
                        s = true;
                    }
                    if (t[k][l + 1].equals("*")) {
                        e = true;
                    }
                    tab[f][g] = new Cell(n, e, s, w, NOTHING, i);
                    gomme[f][g] = false;
                    fruit[f][g] = false;
                    g++;
                    if (g % b == 0 && g != 0) {
                        f++;
                        g = 0;
                    }
                }else if(t[k][l].equals("F")){
                    boolean n = false;
                    boolean w = false;
                    boolean s = false;
                    boolean e = false;
                    int i = 0;
                    if (t[k - 1][l].equals("#")) {
                        n = true;
                    }
                    if (t[k][l - 1].equals("#")) {
                        w = true;
                    }
                    if (t[k + 1][l].equals("#")) {
                        s = true;
                    }
                    if (t[k][l + 1].equals("#")) {
                        e = true;
                    }


                    tab[f][g] = new Cell(n, e, s, w,FRUIT,i);
                    gomme[f][g] = false;
                    fruit[f][g] = true;
                    g++;
                    if (g % b == 0 && g != 0) {
                        f++;
                        g = 0;
                    }
                }
            }
        }
        MazeConfig e = new MazeConfig(tab,gomme,fruit, new IntCoordinates(0, 0),new IntCoordinates(o+2, p), new IntCoordinates(o+2, p-1), new IntCoordinates(o+1, p), new IntCoordinates(o+3, p));
        return e;
    }

    public static void spawn(String[][] a){
        a[13][16]="#";
        a[13][17]="#";
        a[13][18]=" ";
        a[13][19]="#";
        a[13][20]="#";
        a[14][16]="#";
        a[14][17]=" ";
        a[14][18]=" ";
        a[14][19]=" ";
        a[14][20]="#";
        a[15][16]="#";
        a[15][17]="#";
        a[15][18]="#";
        a[15][19]="#";
        a[15][20]="#";
    }

    public static boolean estSpawn(String[][] a, int x, int y){
        if((x+2)<a.length && x>=0 && y>=0 && (y+4)<a[0].length){
        if(a[x][y].equals("#") &&
        a[x][y+1].equals("#") &&
        a[x][y+2].equals(" ") &&
        a[x][y+3].equals("#") &&
        a[x][y+4].equals("#") &&
        a[x+1][y].equals("#") &&
        a[x+1][y+1].equals(" ") &&
        a[x+1][y+2].equals(" ") &&
        a[x+1][y+3].equals(" ") &&
        a[x+1][y+4].equals("#") &&
        a[x+2][y].equals("#") &&
        a[x+2][y+1].equals("#") &&
        a[x+2][y+2].equals("#") &&
        a[x+2][y+3].equals("#") &&
        a[x+2][y+4].equals("#")){
            System.out.println("!!!!!!!!!!!!!!!");
        return true;
        }
    }
    System.out.println("oui");
        return false;
    }

    public static int l(int x,int y,String[][]a){
        Random r = new Random();
        ArrayList<Integer> c = new ArrayList<>();
        if(x==0 || y==0 || x==a.length || y==a[0].length || x==1 || x==a.length-1){
            return 0;
        }
        if(a[x][y]==null){

            if(a[x][y+1]==null && a[x-1][y]!="#" && a[x+1][y]!="#" && a[x][y-1]!="#" && a[x][y+2]==null && a[x-1][y+1]!="#" && a[x+1][y+1]!="#" && a[x-1][y+2]!="#" && a[x][y+3]!="#" && a[x+1][y+2]==null && a[x+2][y+2]!="#" && a[x+1][y+3]!="#"){
                c.add(1);
            }
            if(a[x+1][y]!="#" && a[x][y-1]!="#" && a[x-1][y]!="#" && a[x+1][y+1]!="#" && a[x-1][y+1]!="#" && a[x+1][y+2]!="#" && a[x][y+3]!="#" && a[x-1][y+3]!="#" && a[x-2][y+2]!="#" && a[x][y+1]==null && a[x][y+2]==null && a[x-1][y+2]==null){
                c.add(2);
            }
            if(a[x-2][y]!="#" && a[x+1][y]!="#" && a[x][y-1]!="#" && a[x+1][y+1]!="#" && a[x+1][y+2]!="#" && a[x-1][y+1]!="#" && a[x-1][y+2]!="#" && a[x][y+3]!="#" && a[x+1][y]==null && a[x][y+1]==null && a[x][y+2]==null){
                c.add(3);
            }
            if(a[x+1][y-1]!="#" && a[x+1][y+1]!="#" && a[x+2][y]!="#" && a[x][y-1]!="#" && a[x+1][y]!="#" && a[x-1][y+1]!="#" && a[x+1][y+2]!="#" && a[x][y+3]!="#" && a[x-1][y+2]!="#" && a[x-1][y]==null && a[x][y+1]==null && a[x][y+2]==null){
                c.add(4);
            }
            if(c.size()==0){
                return 0;
            }
            return c.get(r.nextInt((c.size())));
        }
        return 0;
    }

    public static int t(int x,int y,String[][]a){
        Random r = new Random();
        ArrayList<Integer> c = new ArrayList<>();
        if(x==0 || y==0 || x==a.length || y==a[0].length){
            return 0;
        }
        if(a[x][y]==null){
            if(a[x][y+1]==" " && a[x][y+2]==" " && a[x+1][y+2]==" "){
                c.add(1);
            }
            if(a[x][y+1]==" " && a[x][y+2]==" " && a[x-1][y+2]==" "){
                c.add(2);
            }
            if(a[x+1][y]==" " && a[x][y+1]==" " && a[x][y+2]==" "){
                c.add(3);
            }
            if(a[x-1][y]==" " && a[x][y+1]==" " && a[x][y+2]==" "){
                c.add(4);
            }
            if(c.size()==0){
                return 0;
            }
            return c.get(r.nextInt((c.size()-1)+1)+1);
        }
        return 0;
    }

    public static int c(int x,int y,String[][]a){
        if(x==0 || y==0 || x==a.length || y==a[0].length){
            return 0;
        }
        if(a[x][y]==null && x+2!=a.length-1 && y+3!=a[0].length-1){
            if(a[x][y+1]==null && a[x+1][y+1]==null && a[x+1][y]==null && a[x-1][y]!="#" && a[x-1][y-1]!="#" && a[x][y-1]!="#" && a[x+1][y-1]!="#" && a[x+2][y-1]!="#" && a[x+1][y]!="#" && a[x+2][y+1]!="#" && a[x+2][y+2]!="#" && a[x+2][y+3]!="#" && a[x+1][y+3]!="#" && a[x][y+3]!="#" && a[x-1][y+3]!="#" && a[x-1][y+2]!="#" && a[x-1][y+1]!="#"){
                return 1;
            }
            else{
                return 0;
            }
        }
        return 0;
    }

    public static void Generator(){
        int x = 28;
        int y = 36;
        String[][]tab=new String[x][y];
        String[][]tab2=new String[x-2][y-2];
        int b = y/2;
        try{
            File file = new File("test.txt");
            FileWriter a = new FileWriter(file);
            
            for(int i=0;i<y;i++){
                tab[0][i]="#";
                tab[x-1][i]="#";
            }
            for(int i=0;i<x;i++){
                tab[i][0]="#";
                tab[i][y-1]="#";
            }
            spawn(tab2);
            for(int i=0;i<x-2;i++){
                for(int j=0;j<y-2;j++){
                    int c = c(i,j,tab2);
                    if(tab2[i+1][j]!=null && tab2[i][j+1]!=null && tab2[i+1][j+1]!=null){
                    if(c==1){
                        tab2[i][j]="#";
                        tab2[i+1][j]="#";
                        tab2[i][j+1]="#";
                        tab2[i+1][j+1]="#";
                    }
                }
                    /*d++;
                System.out.println(d);
                    int m = l(i,j,tab2);
                    if(m==1){
                        tab2[i][j]="#";
                        tab2[i][j+1]="#";
                        tab2[i][j+2]="#";
                        tab2[i-1][j+2]="#";
                    }
                    if(m==2){
                        tab2[i][j]="#";
                        tab2[i][j+1]="#";
                        tab2[i][j+2]="#";
                        tab2[i+1][j+2]="#";
                    }
                    if(m==3){
                        tab2[i][j]="#";
                        tab2[i][j+1]="#";
                        tab2[i][j+2]="#";
                        tab2[i-1][j]="#";
                    }
                    if(m==4){
                        tab2[i][j]="#";
                        tab2[i+1][j]="#";
                        tab2[i][j+1]="#";
                        tab2[i][j+2]="#";
                    }*/
                }
            }
            for(int i=0;i<tab2.length;i++){
                for(int j=0;j<tab2[0].length;j++){
                    tab[i+1][j+1]=tab2[i][j];
                }
            }
            for(int i=1;i<x-1;i++){
                for(int j=1;j<y-1;j++){
                    if(tab[i][j]==null){
                        tab[i][j]=".";
                    }
                }
            }
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++){
                a.write(tab[i][j]);
                }
                a.write("\n");
            }
            a.close();
        }
        catch(IOException e){
            System.out.println("Une erreur est survenue.");
            e.printStackTrace();
        }

    }

    public String[][] traduction(Cell[][] tab){
        String[][]a=new String[tab.length][tab[0].length];
        for(int i=0;i<tab.length;i++){
            for(int j=0;j<tab[0].length;j++){
                if(tab[i][j].estN()==true){
                    a[i][j]+="n";
                }
                if(tab[i][j].estS()==true){
                    a[i][j]+="s";
                }
                if(tab[i][j].estW()==true){
                    a[i][j]+="w";
                }
                if(tab[i][j].estE()==true){
                    a[i][j]+="e";
                }
            }
        }
        return a;
    }

    public static void main(String[]args){
        Generator();
        //mapLoader(26, 35, 28, 37);
    }
    
}
