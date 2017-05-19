package br.com.avelar.recrutamento.posts;

import br.com.avelar.recrutamento.topicos.Topico;
import br.com.avelar.recrutamento.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Entity
@Table(name = "POSTS")
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Post {
  
  @Id
  @GeneratedValue
  @Column(name = "ID")
  private Long id;
  
  @Column(name = "CONTEUDO")
  @NotNull
  @NotBlank
  @Size(max = 1000)
  private String conteudo;
  
  @Column(name = "DATA")
  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmZ")
  private Date data;
  
  @ManyToOne
  @JoinColumn(name = "TOPICO_ID", referencedColumnName = "ID")
  private Topico topico;
  
  @ManyToOne
  @JoinColumn(name = "AUTOR")
  private User autor;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getConteudo() {
    return conteudo;
  }

  public void setConteudo(String conteudo) {
    this.conteudo = conteudo;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public Topico getTopico() {
    return topico;
  }

  public void setTopico(Topico topico) {
    this.topico = topico;
  }

  public User getAutor() {
    return autor;
  }

  public void setAutor(User autor) {
    this.autor = autor;
  }
  
}
