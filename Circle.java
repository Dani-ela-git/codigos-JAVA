//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

public class Circle extends Shape {
    private double Radius;

    // extends constructor of shape class
    public Circle(String c, double r) {
        super(c); // herdado da classe mãe
        this.Radius = r; // caracteristica da classe circulo
    }

    // get method of radius
    public double getRadius() {
        return Radius;
    }

    // override do to string para essa classe
    public void displayInfo() {
        System.out.println("Color: " + getColor() + ", Radius: " + Radius);
    }

    // verificando se dois circulos são iguais
    public boolean equals(Circle c){
        if(this == c){
            return true;
        } 
        if (c == null || getRadius() != c.getRadius()){
            return false;
        }
        return true;
    }
}
