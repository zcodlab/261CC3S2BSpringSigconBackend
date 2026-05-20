package dsw.sigconbackend.service;

import dsw.sigconbackend.model.Sexo;
import dsw.sigconbackend.model.TipoDocumento;
import dsw.sigconbackend.repository.SexoRepository;
import dsw.sigconbackend.repository.TipoDocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SexoService {
    @Autowired
    SexoRepository sexoRepository;

    public List<Sexo> getSexo(){
        return sexoRepository.findAll();
    }
}
