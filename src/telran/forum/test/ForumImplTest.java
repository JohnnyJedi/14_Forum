package telran.forum.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import telran.forum.dao.ForumImpl;
import telran.forum.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ForumImplTest {
    private Post[] posts;
    private ForumImpl forum;


    @BeforeEach
    void setUp() {
        posts = new Post[14];
        posts[0] = new Post("Java is amazing!", "Hank", 2, "Why I Love Java");
        posts[1] = new Post("Understanding Binary Search", "Ivy", 1, "Binary Search Explained");
        posts[2] = new Post("React vs Angular", "Jack", 8, "Which One to Choose?");
        posts[3] = new Post("Data Structures and Algorithms", "David", 4, "DSA Basics");
        posts[4] = new Post("Concurrency in Java", "Eve", 7, "Multithreading Made Easy");
        posts[5] = new Post("Spring Boot REST API", "Frank", 6, "Building APIs with Spring Boot");
        posts[6] = new Post("The Power of Functional Programming", "Grace", 5, "Why FP Matters");
        posts[7] = new Post("Best Practices in OOP", "Alice", 3, "Clean Code and Design Patterns");
        posts[8] = new Post("How to Master Git", "Bob", 10, "Git for Beginners");
        posts[9] = new Post("Unit Testing in Java", "Charlie", 9, "JUnit and Mockito Guide");
        posts[10] = new Post("Java Script is amazing too!", "Hank", 11, "Why I Love Java Script");
        posts[11] = new Post("Understanding Comparator", "Jack", 14, "Comparator Explained");
        posts[12] = new Post("Spring is the best framework", "Jack", 13, "Don't need to choose");
        posts[13] = new Post("Create  Read Update Delete ", "David", 12, "Java Concept");
        forum = new ForumImpl();
        for (int i = 0; i < posts.length; i++) {
            forum.addPost(posts[i]);
        }
    }


    @Test
    void testAddPost() {
        Post post = new Post("Ha-ha-ha", "John", 15, "Bit it");
        assertTrue(forum.addPost(post));
        assertFalse(forum.addPost(post));
        Post post1 = new Post("Hey-hey", "Ivan", 20, "Greetings");
        Post post2 = new Post("Ho-ho-ho", "Boris", 19, "LOL");
        forum.addPost(post2);
        forum.addPost(post1);
        assertEquals(17, forum.size());
        forum.printPosts();
    }

    @Test
    void testRemovePost() {
        assertTrue(forum.removePost(2));
        assertEquals(13, forum.size());
        assertFalse(forum.removePost(222));
    }

    @Test
    void testUpdatePost() {
        assertTrue(forum.updatePost(2, "I love JAVA so much"));
        assertFalse(forum.updatePost(100, "Stop the war"));
        assertEquals("I love JAVA so much", forum.getPostById(2).getContent());
    }

    @Test
    void testGetPostById() {
        assertEquals(posts[6], forum.getPostById(5));
        assertNull(forum.getPostById(25));
        assertNotEquals(posts[9], forum.getPostById(1));
    }

    @Test
    void testGetPostsByAuthor() {
        Post[] actual = forum.getPostsByAuthor("Hank");
        Post[] expected = new Post[]{posts[0], posts[10]};
        Arrays.sort(actual);
        Arrays.sort(expected);
        assertArrayEquals(expected, actual);

    }

    @Test
    void testGetPostsByAuthor2() {
        posts[2].setDate(LocalDateTime.of(2024, 2, 8, 13, 30));
        Post[] actual = forum.getPostsByAuthor("Jack", LocalDate.of(2025, 2, 10), LocalDate.now());
        Post[] expected = new Post[]{posts[11], posts[12]};
        Arrays.sort(actual);
        Arrays.sort(expected);
        System.out.println("Actual posts:");
        for (int i = 0; i < actual.length; i++) {
            System.out.println(actual[i]);
        }

        assertArrayEquals(expected, actual);
    }


    @Test
    void testSize() {
        assertEquals(14, forum.size());
    }
}