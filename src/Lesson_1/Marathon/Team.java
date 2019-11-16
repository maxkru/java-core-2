package Lesson_1.Marathon;

public class Team {
    private String name;
    Competitor[] competitors = new Competitor[4];

    public Team(String name, Competitor... competitors) {
        this.name = name;
        for (int i = 0; i < competitors.length; i++) {
            this.competitors[i] = competitors[i];
        }
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
