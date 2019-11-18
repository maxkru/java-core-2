package Lesson_1.Marathon;

public class Course {
    private Obstacle[] obstacles;

    Course(Obstacle... obstacles) {
        this.obstacles = obstacles;
    }

    public void doIt(Team team) {
        for (Competitor c : team.getCompetitors()) {
            if (c != null) {
                for (Obstacle o : obstacles) {
                    if (o != null) {
                        o.doIt(c);
                        if (!c.isOnDistance())
                            break;
                    }
                }
            }
        }
    }
}

