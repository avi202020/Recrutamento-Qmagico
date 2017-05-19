package br.com.avelar.recrutamento.posts;

import br.com.avelar.recrutamento.topicos.Topico;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostsService {

  @Autowired
  private PostsRepository dao;
  
  public void save(Post post) {
    dao.save(post);
  }
  
  public Post findOne(Long id) {
    return dao.findOne(id);
  }
  
  public List<Post> findAll() {
    return dao.findAll();
  }
  
  public List<Post> findByTopico(Topico topico) {
    return dao.findByTopico(topico);
  }
  
  public boolean exists(Long id) {
    return dao.exists(id);
  }
  
  public void delete(Post post) {
    dao.delete(post);
  }
  
  public void delete(Long id) {
    dao.delete(id);
  }
  
}
