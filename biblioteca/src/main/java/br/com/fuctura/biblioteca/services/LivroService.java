package br.com.fuctura.biblioteca.services;

import br.com.fuctura.biblioteca.dtos.LivroDto;
import br.com.fuctura.biblioteca.exeptions.ObjectNotFoundExepiton;
import br.com.fuctura.biblioteca.models.Categoria;
import br.com.fuctura.biblioteca.models.Livro;
import br.com.fuctura.biblioteca.repositores.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private CategoriaService categoriaService;

    public Livro findById(Integer id) {
        Optional<Livro> livro = livroRepository.findById(id);
        if (livro.isPresent()) {
            return livro.get();
        }
        throw new ObjectNotFoundExepiton("Livro nao encontrado ou não existente");
    }

    public void delete(Integer id) {
        findById(id);
        livroRepository.deleteById(id);
    }

    public List<Livro> findAll(Integer id_cat) {
        categoriaService.findById(id_cat);
        return livroRepository.findAllLivrosByCategoriaId(id_cat);
    }

    public Livro save(Integer id, LivroDto livroDto) {
        livroDto.setId(null);
        Categoria cat = categoriaService.findById(id);
        livroDto.setCategoria(cat);
       return livroRepository.save(new Livro(livroDto));
    }
}
