//Daniela Costa da Silva 14613625

public class A { // class A with two protected attributes m and n
    protected int m;
    protected int n;
    protected int a1;
    protected int a2;

    public A(int mIn, int nIn) { // object constructor of class A. Initialize the parametres
        m = mIn;
        n = nIn;
        a1 = mIn;
        a2 = nIn;
    }

    public void m1() { // first method of class A.
        m = m + n; // action of method m1.
    }

    // second method of class A. It returns the value of m as a string.
    public String toString() {
        return "A =(" + m + "," + n + ")";
    }

     @Override
    public boolean equals(Object a) {
        // testing if it's the same object
        if (this == a) {
            return true;
        }
        
        // testing if object is null
        if (a == null) {
            return false;
        }
        
        // testing if classes are different
        if (getClass() != a.getClass()) {
            return false;
        }
        
        // cast to A to compare the values
        A other = (A) a;
        
        // compare the values of m and n
        return this.m == other.m && this.n == other.n;
    }

}
