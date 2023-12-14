package config;

public record Cell(boolean northWall, boolean eastWall, boolean southWall, boolean westWall, Cell.Content initialContent, int estInv) {
    public enum Content { NOTHING, DOT, ENERGIZER,FRUIT}

    public boolean estN(){
        if(this.northWall){
            return true;
        }
        return false;
    }
    public boolean estE(){
        if(this.eastWall){
            return true;
        }
        return false;
    }
    public boolean estS(){
        if(this.southWall){
            return true;
        }
        return false;
    }
    public boolean estW(){
        if(this.westWall){
            return true;
        }
        return false;
    }
    
}
