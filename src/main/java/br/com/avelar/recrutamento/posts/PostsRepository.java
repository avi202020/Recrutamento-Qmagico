package br.com.avelar.recrutamento.posts;

import br.com.avelar.recrutamento.topicos.Topico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
  public List<Post> findByTopico(Topico topico);
}