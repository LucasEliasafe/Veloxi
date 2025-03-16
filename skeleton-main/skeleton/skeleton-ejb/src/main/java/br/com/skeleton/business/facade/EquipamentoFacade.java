package br.com.skeleton.business.facade;

import br.com.skeleton.business.entity.Equipamento;
import java.util.List;
import jakarta.ejb.Local;

@Local
public interface EquipamentoFacade {


        @param equipamento O equipamento a ser salvo. Não pode ser nulo.
        @throws IllegalArgumentException Se o equipamento for nulo ou inválido.

  void create(Equipamento equipamento);


        @param equipamento O equipamento com as alterações aplicadas. Não pode ser nulo.
        @throws IllegalArgumentException Se o equipamento for nulo ou inválido.
        @throws EntityNotFoundException Se o equipamento com o ID fornecido não for encontrado no banco.

  void update(Equipamento equipamento);


        @param id O identificador único do equipamento a ser removido. Não pode ser nulo.
        @throws EntityNotFoundException Se o equipamento com o ID fornecido não for encontrado no banco.

  void delete(Long id);



        @param id O identificador único do equipamento a ser buscado. Não pode ser nulo.
        @return O equipamento correspondente ao ID fornecido, ou null se não encontrado.
        @throws IllegalArgumentException Se o ID fornecido for nulo.

    Equipamento findById(Long id);


        @return Uma lista contendo todos os equipamentos cadastrados.

    List<Equipamento> findAll();
}