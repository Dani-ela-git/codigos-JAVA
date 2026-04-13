//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

public class Shape{
    private String Color;

    //constructor
    public Shape (String c){
        Color = c;
    }

    public String getColor() { //get method
        return Color;
    }

    //sobreescrevendo o metodo toString para printar a cor adicionada
    public String toString(){
        return Color;
    }

    public void displayInfo(Shape c){
         System.out.println("Color: " + Color);
    }
}