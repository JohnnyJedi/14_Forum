package telran.forum.dao;

import telran.forum.model.Post;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class ForumImpl implements Forum {
    private Post[] posts;
    private int size;
    private static Comparator<Post> comparator = (post1, post2) -> {
        int res = post1.getAuthor().compareTo(post2.getAuthor());
       res = res != 0 ? res : post1.getDate().compareTo(post2.getDate());
       return res!=0?res:Integer.compare(post1.getPostId(),post2.getPostId());
    };

    public ForumImpl() {
        posts = new Post[10];
        size = 0;
    }

    @Override
    public boolean addPost(Post post) {
        if (post == null || getPostById(post.getPostId()) != null) {
            return false;
        }
        if (size == posts.length) {
            posts = Arrays.copyOf(posts, posts.length + 10);
        }
        int index = Arrays.binarySearch(posts, 0, size, post, comparator);
        index = index >= 0 ? index : -index - 1;
        System.arraycopy(posts, index, posts, index + 1, size - index);
        posts[index] = post;
        size++;
        return true;
    }

    public void printPosts() {
        for (int i = 0; i < size; i++) {
            System.out.println(posts[i]);
        }
    }

    @Override
    public boolean removePost(int postId) {
        for (int i = 0; i < size; i++) {
            if (posts[i] == getPostById(postId)) {
//                System.out.println(posts[i]);
                System.arraycopy(posts, i + 1, posts, i, size - i);
                posts[size - 1] = null;
                size--;
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean updatePost(int postId, String content) {
        Post postForUpdate = getPostById(postId);
        if (postForUpdate != null) {
            postForUpdate.setContent(content);
            return true;
        }
        return false;
    }


    @Override
    public Post getPostById(int postId) {
        for (int i = 0; i < size; i++) {
            if (posts[i].getPostId() == postId) return posts[i];
        }
        return null;
    }

    @Override
    public Post[] getPostsByAuthor(String author) {
        Post pattern = new Post("", author, 0, "");
        int index = Arrays.binarySearch(posts, 0, size, pattern, comparator);
        index = index >= 0 ? index : -index - 1;
        int indexStart = index;
        while (indexStart > 0 && posts[indexStart-1].getAuthor().equals(author)) {
            indexStart--;
        }
        int indexEnd = index;
        while (indexEnd > size-1 && posts[indexEnd+1].getAuthor().equals(author)) {
            indexEnd++;
        }
        return Arrays.copyOfRange(posts, indexStart, indexEnd);
//        return getByPredicate((post -> post.getAuthor().compareTo(author) == 0));
    }

    @Override
    public Post[] getPostsByAuthor(String author, LocalDate dateFrom, LocalDate dateTo) {  // Jack 10-19

        Post pattern = new Post("", author,Integer.MIN_VALUE, "");
        pattern.setDate(dateFrom.atStartOfDay());
        int indexStart = -Arrays.binarySearch(posts, 0, size, pattern, comparator)-1;

        Post pattern2 = new Post("", author,Integer.MAX_VALUE, "");
        pattern2.setDate(LocalDateTime.of(dateTo, LocalTime.MAX));
//        pattern2.setDate(dateTo.atTime(23, 59, 59));
        int indexEnd = -Arrays.binarySearch(posts, 0, size, pattern2, comparator)-1;

        return Arrays.copyOfRange(posts, indexStart, indexEnd);

//        return getByPredicate((post -> post.getAuthor().compareTo(author) == 0 && !post.getDate().toLocalDate().isBefore(dateFrom) && !post.getDate().toLocalDate().isAfter(dateTo)));
    }

    @Override
    public int size() {
        return size;
    }

    public Post[] getByPredicate(Predicate<Post> predicate) {
        Post[] postsTemp = new Post[size];
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (predicate.test(posts[i])) {
                postsTemp[count++] = posts[i];
            }
        }
        return Arrays.copyOfRange(postsTemp, 0, count);
    }
}
