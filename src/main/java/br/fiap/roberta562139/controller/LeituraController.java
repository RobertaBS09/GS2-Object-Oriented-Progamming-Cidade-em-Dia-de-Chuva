package br.fiap.roberta562139.controller;

import br.fiap.roberta562139.dto.LeituraDTO;
import br.fiap.roberta562139.service.LeituraService;
import br.fiap.roberta562139.service.NaoEncontradoException;
import br.fiap.roberta562139.service.TrechoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/leituras")
@RequiredArgsConstructor
public class LeituraController {

    private final LeituraService leituraService;
    private final TrechoService trechoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("leituras", leituraService.listar());
        return "leituras/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("leituraDTO", new LeituraDTO());
        carregarListas(model);
        return "leituras/form";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("leituraDTO", leituraService.buscarPorId(id));
            carregarListas(model);
            return "leituras/form";
        } catch (NaoEncontradoException e) {
            return listarComErro(model, e.getMessage());
        }
    }

    @PostMapping("/salvar")
    public String salvar(@Valid LeituraDTO leituraDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            carregarListas(model);
            return "leituras/form";
        }
        try {
            leituraService.salvar(leituraDTO);
            return "redirect:/leituras";
        } catch (NaoEncontradoException e) {
            model.addAttribute("erro", e.getMessage());
            carregarListas(model);
            return "leituras/form";
        }
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, Model model) {
        try {
            leituraService.excluir(id);
            return "redirect:/leituras";
        } catch (NaoEncontradoException e) {
            return listarComErro(model, e.getMessage());
        }
    }

    private void carregarListas(Model model) {
        model.addAttribute("trechos", trechoService.listar());
    }

    private String listarComErro(Model model, String erro) {
        model.addAttribute("erro", erro);
        model.addAttribute("leituras", leituraService.listar());
        return "leituras/lista";
    }
}