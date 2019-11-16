package Lesson_1.Marathon;

public class Course {
    private Obstacle[] obstacles = new Obstacle[5];

    Course(Obstacle... obstacles) {
        for (int i = 0; i < obstacles.length; i++) {
            this.obstacles[i] = obstacles[i];
        }
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

