package br.com.skeleton.business.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table(name = "configs")
public class Config implements Serializable {

  private static final long serialVersionUID = 6615511541532380908L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "mensagem", nullable = false)
  private String mensagem;

  public Config() {}



  public String getMensagem() {
    return mensagem;
  }


  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }


  @Override
  public int hashCode() {
    return Objects.hash(mensagem);
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Config other = (Config) obj;
    return Objects.equals(mensagem, other.mensagem);
  }

}
