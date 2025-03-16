package seven;

import java.util.*;
import java.util.stream.Collectors;

public class TwitterService {

  private final List<Post> posts = new ArrayList<>();

  // Добавляет стартовые посты
  public void initializePosts() {
    posts.add(new Post(new User("Alice"), "Привет, мир!"));
    posts.add(new Post(new User("Bob"), "Сегодня отличный день!"));
    posts.add(new Post(new User("Charlie"), "Люблю программировать на Java."));
    System.out.println("Добавлены стартовые посты.");
  }

  // Создание нового поста
  public void createPost(User user, String content) {
    posts.add(new Post(user, content));
    System.out.println("Пост создан!");
  }

  // Лайк поста по ID
  public void likePost(int id) {
    findPost(id).ifPresentOrElse(
            Post::like,
            () -> System.out.println("Пост не найден.")
    );
  }

  // Репост поста по ID
  public void repostPost(int id) {
    findPost(id).ifPresentOrElse(
            Post::repost,
            () -> System.out.println("Пост не найден.")
    );
  }

  // Показать все посты (от новых к старым)
  public void showAllPosts() {
    posts.stream()
            .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
            .forEach(System.out::println);
  }

  // Показать топ-N популярных постов по лайкам
  public void showPopularPosts(int count) {
    posts.stream()
            .sorted(Comparator.comparing(Post::getLikes).reversed())
            .limit(count)
            .forEach(System.out::println);
  }

  // Показать посты конкретного пользователя
  public void showUserPosts(User user) {
    posts.stream()
            .filter(post -> post.getAuthor().getName().equals(user.getName()))
            .forEach(System.out::println);
  }

  // Поиск поста по ID
  private Optional<Post> findPost(int id) {
    return posts.stream()
            .filter(p -> p.getId() == id)
            .findFirst();
  }
}
