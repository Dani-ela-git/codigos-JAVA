public class RiddleUser {
    public static void main(String[] args) {
        Riddle r1 = new Riddle(
            "What is black and white and red all over?",
            "An embarrassed zebra");
        
        Riddle r2 = new Riddle(
            "What has a head and a tail but no body?",
            "A coin");

        Riddle r3 = new Riddle(
            "What has a neck but no head?",
            "A bottle");

        System.out.println("Here are some riddles and their answers:");
        System.out.println(r1.getQuestion());
        System.out.println("Answer: " + r1.getAnswer());    
        System.out.println(r2.getQuestion());
        System.out.println("Answer: " + r2.getAnswer());
        System.out.println(r3.getQuestion());
        System.out.println("Answer: " + r3.getAnswer());
    }
}