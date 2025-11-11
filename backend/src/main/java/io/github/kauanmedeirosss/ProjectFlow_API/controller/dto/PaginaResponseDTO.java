package io.github.kauanmedeirosss.ProjectFlow_API.controller.dto;

import java.util.List;

public record PaginaResponseDTO<T>(
        List<T> conteudo,
        int paginaAtual,
        int tamanhoPagina,
        long totalElementos,
        int totalPaginas,
        boolean primeiraPagina,
        boolean ultimaPagina
) {
    public static <T> PaginaResponseDTO<T> of(List<T> conteudo, int pagina, int tamanho, long total, int totalPaginas) {
        return new PaginaResponseDTO<>(
                conteudo,
                pagina,
                tamanho,
                total,
                totalPaginas,
                pagina == 0,
                pagina >= totalPaginas - 1
        );
    }
}
