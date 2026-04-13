//Bruna Romero 11913896
//Daniela Costa da Silva 14613625

public abstract class Mammal implements Animal {
    protected String name;
    protected String specie;
    
    public Mammal(String name, String specie) { //constructor
        this.name = name;
        this.specie = specie;
    }

    //implements the Animal interface
    @Override
    public String getName(){
        return name;
    }

    @Override
    public String getSpecies(){
        return specie;
    }

    @Override
    public void eat(){
        System.out.println("Eat like a mammal");
    }

    //abstract methods
    public abstract void sound();

    public abstract void Habitat();

    // created a factory method
    public static Animal get(String name, String specie) {

        if (specie == null || name == null)
            return null;

        switch (specie.toLowerCase()) {
            case "elephant":
                return new Elephant(name, specie);

            case "lion":
                return new Lion(name, specie);
            default:
                System.out.println("Unknown species: " + specie);
        }
        return null;
    }
}

class Elephant extends Mammal {

        public Elephant(String name, String specie){
            super(name, specie);
        }

        @Override
        public void sound(){
            System.out.println("Trumpets");
        }

        @Override
        public void Habitat(){
            System.out.println("Savanna");
        }
    }

    class Lion extends Mammal {

        public Lion(String name, String specie){
            super(name, specie);
        }

        @Override
        public void sound(){
            System.out.println("Roars");
        }

        @Override
        public void Habitat(){
            System.out.println("Savanna");
        }
    }
