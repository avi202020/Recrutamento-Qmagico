package br.com.avelar.recrutamento.controllers;

import br.com.avelar.recrutamento.acl.ACLPermissions;
import br.com.avelar.recrutamento.posts.Post;
import br.com.avelar.recrutamento.posts.PostsService;
import br.com.avelar.recrutamento.topicos.Topico;
import br.com.avelar.recrutamento.topicos.TopicosService;
import br.com.avelar.recrutamento.user.UserService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/posts")
public class PostsController {
  
  private PostsService service;
  
  @Autowired
  private TopicosService topicosService;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private ACLPermissions acl;
  
  @Autowired
  public PostsController(PostsService service) {
    this.service = service;
  }
  
  @CrossOrigin
  @PostMapping
  public @ResponseBody ResponseEntity<Void> save(@Valid @RequestBody Post post,
                                                                     Errors errors,
                                                                     Authentication authentication) 
                                                                         throws URISyntaxException {
    
    if(errors.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    
    if(post.getId() != null && service.exists(post.getId())) {
      return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header("Location", "/posts/" + post.getId())
                .build();
    }
    
    post.setData(new Date());
    post.setAutor(userService.find(authentication.getName()));
    service.save(post);
    acl.add(authentication, post, BasePermission.WRITE, BasePermission.DELETE);
    
    return ResponseEntity.created(new URI("/posts/" + post.getId())).build();
    
  }
  
  @CrossOrigin
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<Post> findOne(@PathVariable Long id) {
    Post post = service.findOne(id);
    
    if(post == null) {
      return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
    }
    
    return ResponseEntity.ok(post);
    
  }
  
  @CrossOrigin
  @RequestMapping(value = "/topico/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<Post>> findAll(@PathVariable Long id) {
    Topico topico = topicosService.findOne(id);
    
    if(topico == null) {
      return new ResponseEntity<List<Post>>(HttpStatus.BAD_REQUEST);
    }
    
    List<Post> posts = service.findByTopico(topico);
    return ResponseEntity.ok(posts);
    
  }
  
  @CrossOrigin
  @PutMapping
  @PreAuthorize("hasPermission(#post.id, 'br.com.avelar.recrutamento.posts.Post', 'write')")
  public ResponseEntity<Void> edit(@Valid @RequestBody Post post,
                                                       Errors errors,
                                                       Authentication authentication) 
                                                                        throws URISyntaxException {
    
    if(errors.hasErrors() || post.getId() == null || !service.exists(post.getId())) {
      return ResponseEntity.badRequest().build();
    }
    
    service.save(post);
    
    return ResponseEntity.noContent().build();
    
  }
  
  @CrossOrigin
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @PreAuthorize("hasPermission(#id, 'br.com.avelar.recrutamento.posts.Post', 'delete')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if(!service.exists(id)) {
      return ResponseEntity.notFound().build();
    }
    
    service.delete(id);
    return ResponseEntity.noContent().build();
    
  }
  
}
