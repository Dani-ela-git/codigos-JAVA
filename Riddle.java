public class Riddle {
    private String question;
    private String answer; //instance variable

    public Riddle(String q, String a) { //constructor of object
        question = q;
        answer = a; //initialize instance variables
    }

    public String getQuestion() {
        return question;  //instance method
    }

    public String getAnswer() {
        return answer;  //instance method
    }
} 

