//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

public class main1 {
    public static void main(String[] args) {
        
        //criando os elementos
        Shape shape = new Shape("yellow");

        Circle circle1 = new Circle("yellow", 3.14);
        Circle circle2 = new Circle("red", 3.14);

        Rectangle rec1 = new Rectangle("yellow", 40, 20);
        Rectangle rec2 = new Rectangle("red", 40, 20);

        shape.displayInfo(shape);
        circle1.displayInfo();
        circle2.displayInfo();
        rec1.displayInfo();
        rec2.displayInfo();

        System.out.println("Circle 1 is equal to Circle 2: " + (circle1.equals(circle2)));
        System.out.println("Rectangle 1 is equal to Rectangle 2: " + rec1.equals(rec2));
    }
}
