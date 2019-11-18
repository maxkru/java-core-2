package Lesson_1.Marathon;

public class Team {
    private String name;
    private Competitor[] competitors;

    public Team(String name, Competitor... competitors) {
        this.name = name;
        this.competitors = competitors;
    }

    public void showResults() {
        System.out.println("Команда \"" + name + "\":");
        for (Competitor c : competitors) {
            if(c != null)
                c.info();
        }
    }

    public Competitor[] getCompetitors() {
        return competitors;
    }
}
