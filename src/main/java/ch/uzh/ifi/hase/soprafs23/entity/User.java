package ch.uzh.ifi.hase.soprafs23.entity;

import ch.uzh.ifi.hase.soprafs23.constant.UserStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how
 * the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column
 * annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes
 * the primary key.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private UserStatus status;

  @Column(nullable = false)
  private String bio;

  @Column(nullable = false, unique = true)
  private String stats;

  @Column(nullable = false)
  private Date creationDate;

  @Column
  private boolean host;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String name) {
    this.password = name;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

  public UserStatus getStatus() {
    return status;
  }

  public void setStatus(UserStatus status) {
    this.status = status;
  }

  public String getBio(){ return bio;}

  public void setBio(String bio) {
      this.bio = bio;
  }
  public String stats(){
      return stats;
  }

  public void setStats(String stats){
      this.stats = stats;
  }
  public boolean getHost(){return host;}

  public void setHost(boolean host){
      this.host = host;
  }

    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

    public Date getCreationDate() { return creationDate; }
}
