public class Student {
    protected String Student;
    protected int Id;

    //constructed
    public Student (String Name, int i){
        Student = Name;
        Id = i;
    }

    //get method
    public String getName() {
        return Student;
    }

    public int getId(){
        return Id;
    }

    //toString() modified
    

    public void polyMethod(Object obj) {
        System.out.println(obj.toString()); // polymorphic
    }

    // main
    public static void main(String[] args) {
        Student Student = new Student("Daniela", 14613625);
        Student.polyMethod(Student); // polymorphic
    }
}
