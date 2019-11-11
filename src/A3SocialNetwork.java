import java.util.List;

public interface A3SocialNetwork<T> {

    public int numberOfPeopleAtFriendshipDistance(T vertex, int distance);

    public int furthestDistanceInFriendshipRelationships(T vertex);

    public List<T> possibleFriends(T vertex);

    default public List<T> probableFriends(T vertex) {
	System.out.println("probableFriends NOT implemented");
	return null;
    }

}
