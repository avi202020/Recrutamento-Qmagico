package br.com.avelar.recrutamento.topicos;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicosService {
  
  @Autowired
  private TopicosRepository dao;
  
  public void save(Topico topico) {
    dao.save(topico);
  }
  
  public Topico findOne(Long id) {
    return dao.findOne(id);
  }
  
  public List<Topico> findAll() {
    return dao.findAll();
  }
  
  public boolean exists(Long id) {
    return dao.exists(id);
  }
  
  public void delete(Topico topico) {
    dao.delete(topico);
  }
  
  public void delete(Long id) {
    dao.delete(id);
  }
  
}