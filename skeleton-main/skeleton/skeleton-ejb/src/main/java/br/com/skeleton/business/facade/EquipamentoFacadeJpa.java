package br.com.skeleton.business.facade;

import br.com.skeleton.business.entity.Equipamento;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class EquipamentoFacadeJpa implements EquipamentoFacade {

    @PersistenceContext(unitName = "MasterOne")
    private EntityManager em;

    private static final Logger LOGGER = Logger.getLogger(EquipamentoFacadeJpa.class.getName());


    @param equipamento O equipamento a ser salvo. Não pode ser nulo.
            * @throws IllegalArgumentException Se o equipamento for nulo.

    @Override
    public void create(Equipamento equipamento) {
        if (equipamento == null) {
            throw new IllegalArgumentException("Equipamento não pode ser nulo.");
        }
        em.persist(equipamento);
        LOGGER.info("Equipamento criado com sucesso: " + equipamento.getNome());
    }


    @param equipamento O equipamento com as alterações aplicadas.

    @Override
    public void update(Equipamento equipamento) {
        if (equipamento == null) {
            throw new IllegalArgumentException("Equipamento não pode ser nulo.");
        }
        em.merge(equipamento);
        LOGGER.info("Equipamento atualizado com sucesso: " + equipamento.getNome());
    }

    @param id O ID do equipamento a ser removido.

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }
        Equipamento equipamento = em.find(Equipamento.class, id);
        if (equipamento != null) {
            em.remove(equipamento);
            LOGGER.info("Equipamento removido com sucesso. ID: " + id);
        } else {
            LOGGER.warning("Tentativa de remover equipamento inexistente. ID: " + id);
        }
    }

    @param id O identificador do equipamento.
     @return O equipamento encontrado ou null se não encontrado.


    @Override
    public Equipamento findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID não pode ser nulo.");
        }
        Equipamento equipamento = em.find(Equipamento.class, id);
        if (equipamento == null) {
            LOGGER.info("Equipamento não encontrado para ID: " + id);
        }
        return equipamento;
    }

    @return Lista de equipamentos.

    @Override
    public List<Equipamento> findAll() {
        TypedQuery<Equipamento> query = em.createQuery("SELECT e FROM Equipamento e", Equipamento.class);
        List<Equipamento> equipamentos = query.getResultList();
        LOGGER.info("Total de equipamentos encontrados: " + equipamentos.size());
        return equipamentos;
    }

}