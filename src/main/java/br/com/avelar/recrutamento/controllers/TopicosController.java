package br.com.avelar.recrutamento.controllers;

import br.com.avelar.recrutamento.acl.ACLPermissions;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {
  
  private TopicosService service;
  
  @Autowired
  private UserService userService;
  
  @Autowired
  private ACLPermissions acl;
  
  @Autowired
  public TopicosController(TopicosService service) {
    this.service = service;
  }
  
  @CrossOrigin
  @PostMapping
  public @ResponseBody ResponseEntity<Void> save(@Valid @RequestBody Topico topico,
                                                                     Errors errors,
                                                                    Authentication authentication) 
                                                                         throws URISyntaxException {
    
    if(errors.hasErrors()) {
      return ResponseEntity.badRequest().build();
    }
    
    if(topico.getId() != null && service.exists(topico.getId())) {
      return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .header("Location", "/topicos/" + topico.getId())
                .build();
    }
    
    topico.setData(new Date());
    topico.setAutor(userService.find(authentication.getName())); //Pega o usuário autenticado
    service.save(topico);
    acl.add(authentication, topico, BasePermission.WRITE, BasePermission.DELETE);
    
    return ResponseEntity.created(new URI("/topicos/" + topico.getId())).build();
    
  }
  
  @CrossOrigin
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<Topico> findOne(@PathVariable Long id) {
    Topico topico = service.findOne(id);
    
    if(topico == null) {
      return new ResponseEntity<Topico>(HttpStatus.NOT_FOUND);
    }
    
    return ResponseEntity.ok(topico);
    
  }
  
  @CrossOrigin
  @RequestMapping(value = "/todos", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<List<Topico>> findAll() {
    List<Topico> topicos = service.findAll();
    
    if(topicos.isEmpty()) {
      return new ResponseEntity<List<Topico>>(HttpStatus.BAD_REQUEST);
    }
    
    return ResponseEntity.ok(topicos);
    
  }
  
  @CrossOrigin
  @PutMapping
  @PreAuthorize("hasPermission(#topico.id, 'br.com.avelar.recrutamento.topicos.Topico', 'write')")
  public ResponseEntity<Void> edit(@Valid @RequestBody Topico topico,
                                                       Errors errors,
                                                       Authentication authentication) 
                                                                        throws URISyntaxException {
    
    if(errors.hasErrors() || topico.getId() == null || !service.exists(topico.getId())) {
      return ResponseEntity.badRequest().build();
    }
    
    service.save(topico);
    
    return ResponseEntity.noContent().build();
    
  }
  
  @CrossOrigin
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @PreAuthorize("hasPermission(#id, 'br.com.avelar.recrutamento.topicos.Topico', 'delete')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if(!service.exists(id)) {
      return ResponseEntity.notFound().build();
    }
    
    service.delete(id);
    return ResponseEntity.noContent().build();
    
  }
  
}
