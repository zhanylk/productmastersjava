package seven;

import java.time.LocalDateTime;
import seven.User;
public class Post {
    private static int idCounter = 1;

    private final int id;
    private final User author;
    private final String content;
    private final LocalDateTime createdAt;
    private int likes;
    private int reposts;

    public Post(User author, String content) {
        this.id = idCounter++;
        this.author = author;
        this.content = content.length() > 280 ? content.substring(0, 280) : content;
        this.createdAt = LocalDateTime.now();
        this.likes = 0;
        this.reposts = 0;
    }

    public int getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getLikes() {
        return likes;
    }

    public int getReposts() {
        return reposts;
    }

    public void like() {
        likes++;
    }

    public void repost() {
        reposts++;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Автор: %s | Лайки: %d | Репосты: %d\n%s\n%s",
                id, author.getName(), likes, reposts, content, createdAt);
    }
}
