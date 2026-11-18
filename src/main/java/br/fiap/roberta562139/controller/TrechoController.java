package br.fiap.roberta562139.controller;

import br.fiap.roberta562139.dto.TrechoDTO;
import br.fiap.roberta562139.service.BairroService;
import br.fiap.roberta562139.service.NaoEncontradoException;
import br.fiap.roberta562139.service.TrechoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trechos")
@RequiredArgsConstructor
public class TrechoController {

    private static final List<String> TIPOS_CALCADA =
            List.of("Regular", "Irregular", "Escadaria", "Sem iluminação");

    private final TrechoService trechoService;
    private final BairroService bairroService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("trechos", trechoService.listar());
        return "trechos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("trechoDTO", new TrechoDTO());
        carregarListas(model);
        return "trechos/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("trechoDTO", trechoService.buscarPorId(id));
            carregarListas(model);
            return "trechos/form";
        } catch (NaoEncontradoException e) {
            return listarComErro(model, e.getMessage());
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid TrechoDTO trechoDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            carregarListas(model);
            return "trechos/form";
        }
        try {
            trechoService.salvar(trechoDTO);
            return "redirect:/trechos";
        } catch (NaoEncontradoException e) {
            model.addAttribute("erro", e.getMessage());
            carregarListas(model);
            return "trechos/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, Model model) {
        try {
            trechoService.excluir(id);
            return "redirect:/trechos";
        } catch (NaoEncontradoException e) {
            return listarComErro(model, e.getMessage());
        }
    }

    private void carregarListas(Model model) {
        model.addAttribute("bairros", bairroService.listar());
        model.addAttribute("tiposCalcada", TIPOS_CALCADA);
    }

    private String listarComErro(Model model, String erro) {
        model.addAttribute("erro", erro);
        model.addAttribute("trechos", trechoService.listar());
        return "trechos/lista";
    }
}