package br.fiap.roberta562139.controller;

import br.fiap.roberta562139.dto.BairroDTO;
import br.fiap.roberta562139.dto.PainelItemDTO;
import br.fiap.roberta562139.service.BairroService;
import br.fiap.roberta562139.service.NaoEncontradoException;
import br.fiap.roberta562139.service.PainelService;
import br.fiap.roberta562139.service.RegraNegocioException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bairros")
@RequiredArgsConstructor
public class BairroController {

    private static final List<String> ZONAS =
            List.of("Centro", "Norte", "Sul", "Leste", "Oeste");

    private final BairroService bairroService;
    private final PainelService painelService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("bairros", bairroService.listar());
        return "bairros/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("bairroDTO", new BairroDTO());
        model.addAttribute("zonas", ZONAS);
        return "bairros/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("bairroDTO", bairroService.buscarPorId(id));
            model.addAttribute("zonas", ZONAS);
            return "bairros/form";
        } catch (NaoEncontradoException e) {
            return listarComErro(model, e.getMessage());
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid BairroDTO bairroDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("zonas", ZONAS);
            return "bairros/form";
        }
        try {
            bairroService.salvar(bairroDTO);
            return "redirect:/bairros";
        } catch (NaoEncontradoException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("zonas", ZONAS);
            return "bairros/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, Model model) {
        try {
            bairroService.excluir(id);
            return "redirect:/bairros";
        } catch (NaoEncontradoException | RegraNegocioException e) {
            return listarComErro(model, e.getMessage());
        }
    }

    @GetMapping("/{id}/painel")
    public String painel(@PathVariable Long id, Model model) {
        try {
            List<PainelItemDTO> itens = painelService.montarPainel(id);
            model.addAttribute("bairro", bairroService.buscarPorId(id));
            model.addAttribute("itens", itens);
            model.addAttribute("qtdNormal", painelService.contarPorStatus(itens, "NORMAL"));
            model.addAttribute("qtdAtencao", painelService.contarPorStatus(itens, "ATENCAO"));
            model.addAttribute("qtdAlerta", painelService.contarPorStatus(itens, "ALERTA"));
            return "bairros/painel";
        } catch (NaoEncontradoException e) {
            return listarComErro(model, e.getMessage());
        }
    }

    private String listarComErro(Model model, String erro) {
        model.addAttribute("erro", erro);
        model.addAttribute("bairros", bairroService.listar());
        return "bairros/lista";
    }
}