package com.pitang.projectPitang.utils;

public enum Gender {

  NOT_DEFINED(0,"Not Defined"),
  FEMALE(1,"Female"),
  MALE(2,"Male");

  private Integer codigo;
  private String descricao;

  Gender(Integer codigo, String descricao) {
    this.codigo = codigo;
    this.descricao = descricao;
  }


  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }


}
