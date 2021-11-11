package com.example.contato;

import com.example.contato.models.Contato;
import com.example.contato.repositories.ContatoRepositories;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestContato {
    @Autowired
    private ContatoRepositories repositories;

    @Test
    public void saveCantatotest(){
        Contato contato = Contato.builder()
                .nome("Pedro")
                .email("www@hotamil")
                .telefone("55049923")
                .build();
        repositories.save(contato);
        Assertions.assertThat(contato.getId()).isGreaterThan(0);


    }

    @Test
    @Order(2)
    public void listContato(){
        Contato contato = repositories.findById(1L).get();
        Assertions.assertThat(contato.getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    public void getContatoTest(){
        Contato contato = repositories.findById(1L).get();

        Assertions.assertThat(contato.getId()).isEqualTo(1L);
    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateContatoTest(){
        Contato contato = repositories.findById(1l).get();
        contato.setEmail("teste@gmail.com");
        Contato contatoUpdadte = repositories.save(contato);

        Assertions.assertThat(contatoUpdadte.getEmail()).isEqualTo("teste@gmail.com");
    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteContatoTest(){
        Contato contato =repositories.findById(1L).get();

        repositories.delete(contato);

        Contato contato1 = null;

        Optional<Contato> optionalContato = repositories.findByEmail("teste@gmail.com");

        if(optionalContato.isPresent()){
            contato1 = optionalContato.get();
        }
        Assertions.assertThat(contato1).isNull();
    }
}
