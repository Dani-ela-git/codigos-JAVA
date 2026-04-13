public class animal {
    protected boolean isAlive = true;
    protected boolean canMove = true;


    //constructor
    public animal(boolean a, boolean m) {
        isAlive = a;
        canMove = m;
    }

    //get method
    public boolean getisAlive(){
        return isAlive;
    }

    public boolean getcanMove(){
        return canMove;
    }
}


