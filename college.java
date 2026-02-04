import java.util.Scanner;

// College class (Main class)
public class College {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter Theory Marks:");
        int theory = sc.nextInt();

        System.out.println("Enter Lab Marks:");
        int lab = sc.nextInt();

        System.out.println("Choose Program:");
        System.out.println("1. BTech");
        System.out.println("2. MCA");
        System.out.println("3. PhD");
        int programChoice = sc.nextInt();

        System.out.println("Choose Moderation Rule:");
        System.out.println("1. Attendance Moderation");
        System.out.println("2. Difficulty Moderation");
        System.out.println("3. Manual Moderation");
        int moderationChoice = sc.nextInt();

        ModerationRule rule;

        if (moderationChoice == 1)
            rule = new AttendanceModeration();
        else if (moderationChoice == 2)
            rule = new DifficultyModeration();
        else
            rule = new ManualModeration();

        EvaluationEngine engine;

        if (programChoice == 1)
            engine = new BTechEvaluation(rule, theory, lab);
        else if (programChoice == 2)
            engine = new MCAEvaluation(rule, theory, lab);
        else
            engine = new PhDEvaluation(rule, theory, lab);

        engine.evaluateStudent();

        sc.close();
    }
}

// Moderation rule interface
interface ModerationRule {
    int applyModeration(int marks);
}

// Different moderation implementations
class AttendanceModeration implements ModerationRule {
    public int applyModeration(int marks) {
        return marks + 5;
    }
}

class DifficultyModeration implements ModerationRule {
    public int applyModeration(int marks) {
        return marks + 10;
    }
}

class ManualModeration implements ModerationRule {
    public int applyModeration(int marks) {
        return marks + 2;
    }
}

// Abstract Evaluation Engine (controls fixed flow)
abstract class EvaluationEngine {

    protected ModerationRule moderationRule;
    protected int theoryMarks;
    protected int labMarks;

    public EvaluationEngine(ModerationRule rule, int theory, int lab) {
        this.moderationRule = rule;
        this.theoryMarks = theory;
        this.labMarks = lab;
    }

    // Fixed evaluation process
    public final void evaluateStudent() {
        int total = calculateTotal(theoryMarks, labMarks);
        total = moderationRule.applyModeration(total);
        String grade = generateGrade(total);

        System.out.println("Final Marks: " + total);
        System.out.println("Grade: " + grade);
        System.out.println("-----------------------");
    }

    protected abstract int calculateTotal(int theory, int lab);
    protected abstract String generateGrade(int total);
}

// Program-specific implementations
class BTechEvaluation extends EvaluationEngine {

    public BTechEvaluation(ModerationRule rule, int theory, int lab) {
        super(rule, theory, lab);
    }

    protected int calculateTotal(int theory, int lab) {
        return (int)(theory * 0.7 + lab * 0.3);
    }

    protected String generateGrade(int total) {
        if (total >= 80) return "A";
        else if (total >= 60) return "B";
        else return "C";
    }
}

class MCAEvaluation extends EvaluationEngine {

    public MCAEvaluation(ModerationRule rule, int theory, int lab) {
        super(rule, theory, lab);
    }

    protected int calculateTotal(int theory, int lab) {
        return theory + lab;
    }

    protected String generateGrade(int total) {
        if (total >= 85) return "Distinction";
        else if (total >= 50) return "Pass";
        else return "Fail";
    }
}

class PhDEvaluation extends EvaluationEngine {

    public PhDEvaluation(ModerationRule rule, int theory, int lab) {
        super(rule, theory, lab);
    }

    protected int calculateTotal(int theory, int lab) {
        return theory * 2 + lab;
    }

    protected String generateGrade(int total) {
        return (total >= 90) ? "Qualified" : "Not Qualified";
    }
}

