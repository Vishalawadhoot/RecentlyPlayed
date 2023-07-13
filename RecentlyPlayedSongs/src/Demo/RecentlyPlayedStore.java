/*Create an in-memory store for recently played songs that can accommodate N songs per user, 
with a fixed initial capacity. This store must have the capability to store a list of song-user pairs, 
with each song linked to a user. It should also be able to fetch recently played songs based 
on the user and eliminate the least recently played songs when the store becomes full.*/

package Demo;

import java.util.HashMap;

class Song 
{
    String name;
    public Song(String name) 
    {
        this.name = name;
    }
}

class User {
    String name;
    public User(String name) 
    {
        this.name = name;
    }
}

class Node 
{
    Song song;
    User user;
    Node prev;
    Node next;
    public Node(Song song, User user) 
    {
        this.song = song;
        this.user = user;
    }
}

public class RecentlyPlayedStore
{
    private HashMap<User, Node> map;
    private Node head;
    private Node tail;
    private int capacity;
    private int size;

    public RecentlyPlayedStore(int capacity) 
    {
        this.map = new HashMap<>();
        this.head = null;
        this.tail = null;
        this.capacity = capacity;
        this.size = 0;
    }

    public void addRecentlyPlayedSong(Song song, User user) 
    {
        if (map.containsKey(user))
        {
            Node node = map.get(user);
            removeNode(node);
        }
        else if (size == capacity) 
        {
            removeLeastRecentlyPlayedSong();
        }

        Node newNode = new Node(song, user);
        map.put(user, newNode);
        addToFront(newNode);
    }

    private void removeLeastRecentlyPlayedSong()
    {
        Node lastNode = tail;
        removeNode(lastNode);
        map.remove(lastNode.user);
    }

    private void removeNode(Node node)
    {
        if (node == head)
        {
            head = head.next;
        } 
        else if (node == tail) 
        {
            tail = tail.prev;
        } 
        else
        {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        size--;
    }

    private void addToFront(Node node)
    {
        if (head == null) 
        {
            head = node;
            tail = node;
        }
        else
        {
            node.next = head;
            head.prev = node;
            head = node;
        }

        size++;
    }

    public void getRecentlyPlayedSongs(User user) 
    {
        Node node = map.get(user);
        if (node != null) 
        {
            System.out.println("Recently played songs for user " + user.name + ":");
            Node current = node;
            while (current != null)
            {
                System.out.println(current.song.name);
                current = current.next;
            }
        } 
        else
        {
            System.out.println("No recently played songs found for user " + user.name);
        }
    }

    public static void main(String[] args)
    {
        RecentlyPlayedStore store = new RecentlyPlayedStore(5);

        User user1 = new User("John");
        User user2 = new User("Jane");

        Song song1 = new Song("Song 1");
        Song song2 = new Song("Song 2");
        Song song3 = new Song("Song 3");
        Song song4 = new Song("Song 4");
        Song song5 = new Song("Song 5");
        Song song6 = new Song("Song 6");

        store.addRecentlyPlayedSong(song1, user1);
        store.addRecentlyPlayedSong(song2, user1);
        store.addRecentlyPlayedSong(song3, user2);
        store.addRecentlyPlayedSong(song4, user1);
        store.addRecentlyPlayedSong(song5, user2);
        store.addRecentlyPlayedSong(song6, user1);
        store.getRecentlyPlayedSongs(user1);
        store.getRecentlyPlayedSongs(user2);
    }
}
