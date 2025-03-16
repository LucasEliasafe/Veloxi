package br.com.skeleton.web.bean;

import br.com.skeleton.business.entity.Equipamento;
import br.com.skeleton.business.facade.EquipamentoFacade;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@ViewScoped
public class EquipamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(EquipamentoBean.class.getName());

    @EJB
    private EquipamentoFacade equipamentoFacade;

    private Equipamento equipamento;
    private Long equipamentoId;
    private Equipamento equipamentoParaAtualizar;
    private List<Equipamento> equipamentos;

    @PostConstruct
    public void init() {
        equipamentos = new ArrayList<>();
        equipamento = new Equipamento();
        equipamentoParaAtualizar = new Equipamento();

        try {
            if (equipamentoFacade == null) {
                LOGGER.log(Level.SEVERE, "Erro: equipamentoFacade não foi injetado corretamente!");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na inicialização", "equipamentoFacade não foi injetado."));
            } else {
                LOGGER.info("Injeção bem-sucedida!");
                equipamentos = equipamentoFacade.findAll();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro durante a inicialização do bean", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro na inicialização", e.getMessage()));
        }
    }

    public void create() {
        try {
            validateDates(equipamento.getDatadechegada(), equipamento.getValidade());
            equipamentoFacade.create(equipamento);
            equipamentos.add(equipamento);
            clearForm();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Equipamento salvo com sucesso!"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o equipamento", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao salvar o equipamento: " + e.getMessage()));
        }
    }

    public void update() {
        try {
            if (equipamentoParaAtualizar == null || equipamentoParaAtualizar.getId() == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Selecione um equipamento para atualizar."));
                return;
            }
            validateDates(equipamentoParaAtualizar.getDatadechegada(), equipamentoParaAtualizar.getValidade());
            equipamentoFacade.update(equipamentoParaAtualizar);
            equipamentos.replaceAll(e -> e.getId().equals(equipamentoParaAtualizar.getId()) ? equipamentoParaAtualizar : e);
            equipamentoParaAtualizar = new Equipamento();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Equipamento atualizado com sucesso!"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar o equipamento", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao atualizar o equipamento: " + e.getMessage()));
        }
    }

    public void delete(Long id) {
        try {
            if (id == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "ID do equipamento inválido!"));
                return;
            }
            equipamentoFacade.delete(id);
            equipamentos.removeIf(e -> e.getId().equals(id));
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Equipamento deletado com sucesso!"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao deletar o equipamento", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao deletar o equipamento: " + e.getMessage()));
        }
    }

    public void buscarEquipamento() {
        try {
            if (equipamentoId != null) {
                equipamentoParaAtualizar = equipamentoFacade.findById(equipamentoId);

                if (equipamentoParaAtualizar == null) {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Equipamento não encontrado."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Equipamento encontrado."));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Por favor, informe o ID do equipamento."));
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar equipamento", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Erro ao buscar o equipamento: " + e.getMessage()));
        }
    }

    private void clearForm() {
        equipamento = new Equipamento();
    }

    private void validateDates(LocalDate datadechegada, LocalDate validade) {
        if (datadechegada == null || validade == null) {
            throw new IllegalArgumentException("As datas de chegada e validade não podem ser nulas.");
        }
        if (datadechegada.isAfter(validade)) {
            throw new IllegalArgumentException("A data de chegada não pode ser posterior à validade.");
        }
    }

    public String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date != null ? date.format(formatter) : "";
    }


    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamentoId(Long equipamentoId) {
        this.equipamentoId = equipamentoId;
    }

    public Equipamento getEquipamentoParaAtualizar() {
        return equipamentoParaAtualizar;
    }

    public void setEquipamentoParaAtualizar(Equipamento equipamentoParaAtualizar) {
        this.equipamentoParaAtualizar = equipamentoParaAtualizar;
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }
}

}