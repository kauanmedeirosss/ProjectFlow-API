package io.github.kauanmedeirosss.ProjectFlow_API.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "anexos")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Anexo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo")
    private String nomeArquivo;

    @Column(name = "url_arquivo")
    private String URLarquivo;

    @CreatedDate
    @Column(name = "upload_em")
    private LocalDateTime uploadEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tarefa_id", nullable = false)
    private Tarefa tarefa;
}
