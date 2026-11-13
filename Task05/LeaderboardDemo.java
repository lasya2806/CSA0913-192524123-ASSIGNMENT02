
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Team {
    final String name;
    final int points;
    final int goalDiff;

    Team(String name, int points, int goalDiff) {
        this.name = name;
        this.points = points;
        this.goalDiff = goalDiff;
    }

    @Override
    public String toString() {
        return name + "(" + points + "," + goalDiff + ")";
    }
}

class Leaderboard<T> {

    private final List<T> items = new ArrayList<>();
    private final Comparator<? super T> order;

    Leaderboard(Comparator<? super T> order) {
        this.order = order;
    }

    void add(T item) {
        items.add(item);
    }

    // Accepts a list of T or a list of a subtype of T
    void addAll(List<? extends T> newItems) {
        items.addAll(newItems);
    }

    List<T> ranking() {

        // Create a separate copy so original items are not changed
        List<T> sorted = new ArrayList<>(items);

        // Sort the copied list using the supplied comparator
        sorted.sort(order);

        return sorted;
    }
}

public class LeaderboardDemo {

    public static void main(String[] args) {

        // Higher points, then higher goal difference,
        // then alphabetical name
        Comparator<Team> byRank =
            Comparator.comparingInt((Team t) -> t.points)
                .reversed()
                .thenComparing(
                    Comparator.comparingInt((Team t) -> t.goalDiff)
                        .reversed()
                )
                .thenComparing(t -> t.name);

        Leaderboard<Team> board = new Leaderboard<>(byRank);

        // First test dataset: several ties
        board.add(new Team("Panthers", 9, 4));
        board.add(new Team("Eagles", 9, 6));
        board.add(new Team("Cobras", 6, 1));
        board.add(new Team("Bisons", 9, 4));

        System.out.println("FIRST DATASET - CORRECT RANKING");
        System.out.println(board.ranking());

        // Check that ranking() does not change original insertion order
        System.out.println("\nOriginal insertion order:");
        System.out.println("Panthers, Eagles, Cobras, Bisons");

        // Second dataset: designed to expose a faulty comparator
        // that compares only points and ignores goal difference
        Leaderboard<Team> secondBoard =
            new Leaderboard<>(byRank);

        secondBoard.add(new Team("Tigers", 10, 2));
        secondBoard.add(new Team("Lions", 10, 8));
        secondBoard.add(new Team("Wolves", 10, 5));

        System.out.println("\nSECOND DATASET - TIE BREAKER TEST");
        System.out.println(secondBoard.ranking());

        // Demonstrate the faulty comparator
        Comparator<Team> faultyByPointsOnly =
            Comparator.comparingInt((Team t) -> t.points).reversed();

        List<Team> faultyList = new ArrayList<>();

        faultyList.add(new Team("Tigers", 10, 2));
        faultyList.add(new Team("Lions", 10, 8));
        faultyList.add(new Team("Wolves", 10, 5));

        faultyList.sort(faultyByPointsOnly);

        System.out.println("\nFAULTY COMPARATOR RESULT");
        System.out.println(faultyList);

        System.out.println("\nProgram completed successfully.");
    }
}

