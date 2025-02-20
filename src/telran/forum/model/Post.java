package telran.forum.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Post implements  Comparable<Post>{
    private final int postId;
    private final String title;
    private final String author;
    private String content;
    private LocalDateTime date;
    private int likes;

    public Post(String content, String author, int postId, String title) {
        this.date = LocalDateTime.now();
        this.content = content;
        this.author = author;
        this.postId = postId;
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public int getLikes() {
        return likes;
    }

    public int getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int addLike() {
         return ++likes;
    }

    @Override
    public String toString() {
        return "Post{" +
                "author='" + author + '\'' +
                ", postId=" + postId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", likes=" + likes +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Post post)) return false;

        return postId == post.postId;
    }

    @Override
    public int hashCode() {
        return postId;
    }

    @Override
    public int compareTo(Post o) {
        return Integer.compare(postId,o.postId);
    }
}
