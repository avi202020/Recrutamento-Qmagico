package br.com.avelar.recrutamento.controllers;

import br.com.avelar.recrutamento.acl.ACLPermissions;
import br.com.avelar.recrutamento.topicos.Topico;
import br.com.avelar.recrutamento.topicos.TopicosService;
import br.com.avelar.recrutamento.user.UserService;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public @ResponseBody ResponseEntity<String> save(@Valid @RequestBody Topico topico,
                                                                     Errors errors,
                                                                    Authentication authentication) 
                                                                         throws URISyntaxException {
    
    if(errors.hasErrors()) {
      return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    }
        
    topico.setData(new Date());
    topico.setAutor(userService.find(authentication.getName())); //Pega o usuário autenticado
    service.save(topico);
    acl.add(authentication, topico, BasePermission.WRITE, BasePermission.DELETE);
    
    return ResponseEntity.ok(topico.getId().toString());
    
  }
  
  @CrossOrigin
  @GetMapping("/{id}")
  public @ResponseBody ResponseEntity<Topico> findOne(@PathVariable Long id) {
    Topico topico = service.findOne(id);
    
    if(topico == null) {
      return new ResponseEntity<Topico>(HttpStatus.NOT_FOUND);
    }
    
    return ResponseEntity.ok(topico);
    
  }
  
  @CrossOrigin
  @GetMapping("/todos")
  public @ResponseBody List<Topico>findAll() {
    return service.findAll(); 
  }
  
  @CrossOrigin
  @PutMapping
  @PreAuthorize("hasPermission(#topico.id, 'br.com.avelar.recrutamento.topicos.Topico', 'write')  or hasRole('ADMIN')")
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
  @DeleteMapping("/{id}")
  @PreAuthorize("hasPermission(#id, 'br.com.avelar.recrutamento.topicos.Topico', 'delete')  or hasRole('ADMIN')")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    if(!service.exists(id)) {
      return ResponseEntity.notFound().build();
    }
    
    service.delete(id);
    return ResponseEntity.noContent().build();
    
  }
  
}
