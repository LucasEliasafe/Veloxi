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

  public Config(String mensagem) {
    this.mensagem = mensagem;
  }

  /**
   * Retorna a mensagem de configuração.
   * @return a mensagem de configuração
   */
  public String getMensagem() {
    return mensagem;
  }

  /**
   * Define uma nova mensagem de configuração.
   * @param mensagem a nova mensagem de configuração
   */
  public void setMensagem(String mensagem) {
    this.mensagem = mensagem;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(mensagem);
  }

  /**
   * Compara este objeto com outro para verificar se são iguais.
   * @param obj o objeto com o qual será comparado
   * @return true se os objetos forem iguais, caso contrário, false
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Config other = (Config) obj;
    return Objects.equals(mensagem, other.mensagem);
  }

  /**
   * Retorna uma string contendo os valores do id e mensagem.
   * @return uma string representando o objeto Config
   */
  @Override
  public String toString() {
    return "Config{" +
            "id=" + id +
            ", mensagem='" + mensagem + '\'' +
            '}';
  }
}