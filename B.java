//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


// now, we create class B that extends class A.
public class B extends A {

    // constructor of class B. It initializes the parameters and calls the
    // constructor of class A
    public B(int mIn, int nIn) {
        super(mIn, nIn); // call the constructor of class A and pass the parameters for m and n
        m = m - n;
    }

    public String toString() {
        return " B = (" + m + "," + n + ")";
    }

    @Override
    public boolean equals(Object a) {
        // testing id if n == n and m == m
        if (this == a) {
            return true;
        }
        //testing if classe are the same
        if (a != null && getClass() == a.getClass()) {
            return true;
        }
        // cast to B to access B's specific fields
        B other = (B) a;
        
        // compare the values of m and n
        return this.m == other.m && this.n == other.n;
    }

    public static void main(String[] args) { // main method of class B.
        A a = new A(1, 2); // create an object in class B with constructor of class A
        A b = new B(1, 2); // create an object in class B with constructor of class B
        A a1 = new A(3, 2);
        A a2 = new A(3, 2);
        A b1 = new B(3, 2);
        A b2 = new B(3, 2);
        A c1 = new B(3, 4);

        // print the values before call the methods
        System.out.println(a + "" + b);

        // call the methods for a and b
        a.m1();
        b.m1();

        // print the values of m for a and b
        System.out.println(a + "" + b);

        System.out.println("a1.equals(a2): " + a1.equals(a2)); // true
        System.out.println("a1.equals(b1): " + a1.equals(b1)); // false
        System.out.println("b1.equals(b2): " + b1.equals(b2)); // true
        System.out.println("b1.equals(c1): " + b1.equals(c1)); // false
        System.out.println("a1.equals(null): " + a1.equals(null)); // false

    }

}