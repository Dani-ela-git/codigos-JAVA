//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

public class Main {
    
    public static void main(String[] args) {
        Animal elephant = Mammal.get("Dumbo", "elephant");
        Animal lion = Mammal.get("Simba", "lion");

        System.out.println("Name:c" + elephant.getName());
        System.out.println("Specie: " + elephant.getSpecies());
        elephant.eat();
        elephant.sound();
        elephant.Habitat();
    
        System.out.println("\n---\n");

        System.out.println("Name: " + lion.getName());
        System.out.println("Specie: " +lion.getSpecies());
        lion.eat();
        lion.sound();
        lion.Habitat();
    }
}
