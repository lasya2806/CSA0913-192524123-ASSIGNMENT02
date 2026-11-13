
import java.util.*;

class Team {
    String name;
    int points;
    int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

// ORIGINAL DESIGN: sorts every time ranking() is called
class OriginalLeaderboard {
    private List<Team> teams = new ArrayList<>();

    private Comparator<Team> order =
        Comparator.comparingInt((Team t) -> t.points).reversed()
        .thenComparing(
            Comparator.comparingInt((Team t) -> t.goalDiff).reversed()
        )
        .thenComparing(t -> t.name);

    void add(Team team) {
        teams.add(team);
    }

    List<Team> ranking() {
        List<Team> result = new ArrayList<>(teams);
        result.sort(order);
        return result;
    }
}

// REDESIGNED: sorts once and reuses the saved ranking
class CachedLeaderboard {
    private List<Team> teams = new ArrayList<>();
    private List<Team> cachedRanking = null;

    private Comparator<Team> order =
        Comparator.comparingInt((Team t) -> t.points).reversed()
        .thenComparing(
            Comparator.comparingInt((Team t) -> t.goalDiff).reversed()
        )
        .thenComparing(t -> t.name);

    void add(Team team) {
        teams.add(team);

        // New team added, so the old ranking is outdated
        cachedRanking = null;
    }

    List<Team> ranking() {
        if (cachedRanking == null) {
            cachedRanking = new ArrayList<>(teams);
            cachedRanking.sort(order);
        }

        return Collections.unmodifiableList(cachedRanking);
    }
}

public class LeaderboardRedesign {

    public static void main(String[] args) {

        int[] sizes = {100, 1000, 5000};
        int repeatedRequests = 200;

        System.out.println("LEADERBOARD REDESIGN EXPERIMENT");
        System.out.println("Repeated ranking requests: " + repeatedRequests);

        for (int size : sizes) {

            OriginalLeaderboard original = new OriginalLeaderboard();
            CachedLeaderboard redesigned = new CachedLeaderboard();

            // Create the same data for both designs
            for (int i = 1; i <= size; i++) {
                Team team = new Team(
                    "Team" + i,
                    (i * 7) % 100,
                    (i * 13) % 50
                );

                original.add(team);
                redesigned.add(team);
            }

            // Warm-up calls
            original.ranking();
            redesigned.ranking();

            long startOriginal = System.nanoTime();

            int originalCount = 0;
            for (int i = 0; i < repeatedRequests; i++) {
                originalCount += original.ranking().size();
            }

            long originalTime =
                System.nanoTime() - startOriginal;

            long startRedesigned = System.nanoTime();

            int redesignedCount = 0;
            for (int i = 0; i < repeatedRequests; i++) {
                redesignedCount += redesigned.ranking().size();
            }

            long redesignedTime =
                System.nanoTime() - startRedesigned;

            System.out.println("\nNumber of teams: " + size);
            System.out.println(
                "Original time (ns): " + originalTime
            );
            System.out.println(
                "Redesigned time (ns): " + redesignedTime
            );
            System.out.println(
                "Original result count: " + originalCount
            );
            System.out.println(
                "Redesigned result count: " + redesignedCount
            );

            // Validate that both designs produce the same ranking
            boolean sameResult =
                original.ranking().toString()
                .equals(redesigned.ranking().toString());

            System.out.println("Rankings match: " + sameResult);

            // Check that adding a team refreshes the cached ranking
            redesigned.add(new Team("NewTeam", 100, 100));

            System.out.println(
                "New team appears after update: "
                + redesigned.ranking().get(0).name.equals("NewTeam")
            );
        }

        System.out.println("\nExperiment completed.");
    }
}

