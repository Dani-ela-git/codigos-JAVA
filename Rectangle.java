//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

public class Rectangle extends Shape {
    private double Length;
    private double Width;

    // constructor
    public Rectangle(String c, double l, double w) {
        super(c);
        this.Length = l;
        this.Width = w;
    }

    // get method of rectangle class
    public double getLength() {
        return Length;
    }

    public double getWidth() {
        return Width;
    }

    public void displayInfo() {
        System.out.println("Color: " + getColor() + ", Length: " + Length + ", Width: " + Width);
    }

    // verificando se dois retangulos são iguais
    public boolean equals(Rectangle r){
        if (this == r) {
            return true;
        }
        if(r == null || getLength() != r.getLength() && getWidth() != r.getWidth()){
            return false;
        }
        return true;
    }
}
