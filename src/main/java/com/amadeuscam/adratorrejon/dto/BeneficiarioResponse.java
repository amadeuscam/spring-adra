package com.amadeuscam.adratorrejon.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class BeneficiarioResponse {

    private List<BeneficiarioDTO> contenido;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;

    public BeneficiarioResponse() {
    }

    public List<BeneficiarioDTO> getContenido() {
        return contenido;
    }

    public void setContenido(List<BeneficiarioDTO> contenido) {
        this.contenido = contenido;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }
}
