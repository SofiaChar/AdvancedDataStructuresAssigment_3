import java.util.List;

public interface A3SocialNetwork {

    public int numberOfPeopleAtFriendshipDistance(int vertexIndex, int distance);

    public int furthestDistanceInFriendshipRelationships(int vertexIndex);

    public List<Integer> possibleFriends(int vertexIndex);

    default public List<Integer> probableFriends(int vertexIndex) {
        System.out.println("Not implemented");
        return null;
    }

}
